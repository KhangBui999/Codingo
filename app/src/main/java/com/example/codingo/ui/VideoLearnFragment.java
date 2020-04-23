package com.example.codingo.ui;

/**
 * Please note that the YouTubePlayerSupportFragmentX used in VideoLearnFragment is adapted from:
 * https://gist.github.com/medyo/f226b967213c3b8ec6f6bebb5338a492
 * More details @ google.android.youtube.player.YouTubePlayerSupportFragmentX
 */

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codingo.BaseActivity;
import com.example.codingo.Model.Content;
import com.example.codingo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass that handles the video and content learning tool.
 * Class is responsible for retrieving video and learning data from Firestore and handling
 * user interaction with the learning tool.
 */
public class VideoLearnFragment extends Fragment implements YouTubePlayer.OnInitializedListener {

    private final String TAG = "com.example.codingo.ui.VideoLearnFragment";
    private TextView mTopic, mContent;
    private ProgressBar mProgress;
    private Content mCon; //ontent to be displayed
    private static final String API_KEY = "AIzaSyB-X0GINXGVnrcwiFAMx9bZliAvMSEcEu0"; //API key

    public VideoLearnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_video_learn, container, false);

        // Link XML elements
        mTopic =  root.findViewById(R.id.tv_topic);
        mContent = root.findViewById(R.id.tv_content);
        mProgress = root.findViewById(R.id.pb_progress);

        //Retrieve the content data based on previously selected topic
        Intent intent = getActivity().getIntent();
        int position = intent.getIntExtra("POSITION", 0);
        getContentData(position);
        return root;
    }

    /**
     * This method is responsible for the loading of videos in the selected fragment screen.
     */
    protected void loadVideo() {
        try {
            //YouTubeSupportFragmentX is a custom class. Refer to top for academic reference.
            YouTubePlayerSupportFragmentX youtubePlayerFragment = new YouTubePlayerSupportFragmentX();

            //Managing the fragment transactions for the youtube fragment
            youtubePlayerFragment.initialize(API_KEY, this);
            FragmentManager manager = getChildFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame_fragment, youtubePlayerFragment);
            transaction.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves video and content data from the Firestore server.
     * @param position is the topic_id of the previously selected topic.
     */
    protected void getContentData(int position) {
        //Progress UI rendered
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);

        //Retrieval of data
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("content")
                .whereEqualTo("position", position)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //Loop is used as a precaution
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Converts document from Firestore into a Content object
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> contentMap = document.getData();
                                String topic = contentMap.get("topic").toString();
                                String content = contentMap.get("content_body").toString().replace("_n", "\n");
                                String videoId = contentMap.get("video_id").toString();
                                mCon = new Content(topic, content, videoId);
                            }
                            //Successful UI rendered
                            loadVideo();
                            mProgress.setVisibility(View.INVISIBLE);
                            mTopic.setText(mCon.getTopic());
                            mContent.setText(mCon.getContent());
                        }
                        else {
                            //Network failure UI rendered
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            mProgress.setVisibility(View.INVISIBLE);
                            mContent.setText("Connection issue. Content failed to load.");
                        }
                    }
                });
    }

    //Method from the YouTube API that handles the successful loading of the YouTube Video
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.setShowFullscreenButton(false); //prevents user from encountering YouTube API issue
            youTubePlayer.cueVideo(mCon.getVideo());
        }
    }

    //Method from the YouTube API that handles failed loading of the YouTube video
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getActivity(), "Connection issue - video failed to load!", Toast.LENGTH_SHORT).show();
    }

}

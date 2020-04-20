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
 * A simple {@link Fragment} subclass.
 */
public class VideoLearnFragment extends Fragment implements YouTubePlayer.OnInitializedListener {

    private final String TAG = "com.example.codingo.ui.VideoLearnFragment";
    private TextView mTopic;
    private TextView mContent;
    private ProgressBar mProgress;
    private Content mCon;
    private static final String API_KEY = "AIzaSyB-X0GINXGVnrcwiFAMx9bZliAvMSEcEu0";

    public VideoLearnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_video_learn, container, false);
        Intent intent = getActivity().getIntent();
        int position = intent.getIntExtra("POSITION", 0);
        mTopic =  root.findViewById(R.id.tv_topic);
        mContent = root.findViewById(R.id.tv_content);
        mProgress = root.findViewById(R.id.pb_progress);
        getContentData(position);
        return root;
    }

    protected void loadVideo() {
        try {
            YouTubePlayerSupportFragmentX youtubePlayerFragment = new YouTubePlayerSupportFragmentX();
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

    protected void getContentData(int position) {
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("content")
                .whereEqualTo("position", position)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> contentMap = document.getData();
                                String topic = contentMap.get("topic").toString();
                                String content = contentMap.get("content_body").toString();
                                String videoId = contentMap.get("video_id").toString();
                                mCon = new Content(topic, content, videoId);
                                loadVideo();
                                mProgress.setVisibility(View.INVISIBLE);
                                mTopic.setText(mCon.getTopic());
                                mContent.setText(mCon.getContent());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            mProgress.setVisibility(View.INVISIBLE);
                            mContent.setText("Connection issue. Content failed to load.");
                        }
                    }
                });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.setShowFullscreenButton(false); //prevents user from encountering YouTube API issue
            youTubePlayer.cueVideo(mCon.getVideo());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getActivity(), "Connection issue - video failed to load!", Toast.LENGTH_SHORT).show();
    }

}

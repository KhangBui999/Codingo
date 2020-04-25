package com.example.codingo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.codingo.Entities.Topic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass that handles topic selection for the previously selected
 * learning style. Uses a RecyclerView to display the topic options and handles the launch
 * of the learning activity.
 */
public class LearnTopicFragment extends Fragment {


    private final String TAG = "LearnTopicFragment";
    private RecyclerView mRecyclerView;
    private TopicAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mStatus, mLoading;
    private ProgressBar mProgress;


    public LearnTopicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflating the layout
        View root = inflater.inflate(R.layout.fragment_learn_topic, container, false);

        //Links the XML elements
        mStatus = root.findViewById(R.id.tv_status);
        mLoading = root.findViewById(R.id.tv_progress);
        mRecyclerView = root.findViewById(R.id.rvList);
        mProgress = root.findViewById(R.id.pb_topic);

        //Setting the adapter
        mStatus.setVisibility(View.INVISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        TopicAdapter.RecyclerViewClickListener listener = new TopicAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                launchLearningContent(position);
            }
        };
        mAdapter = new TopicAdapter(new ArrayList<>(), listener);
        mRecyclerView.setAdapter(mAdapter);
        getTopics(); //retrieves the list of topics from the Firebase db
        return root;
    }


    /**
     * This handles the retrieval of available topics from the Firestore db.
     */
    private void getTopics() {
        //Progress UI
        mProgress.setIndeterminate(true);
        mLoading.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.VISIBLE);

        List<Topic> topics = new ArrayList<>(); //the topic list

        //FirebaseFirestore transaction
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Get data from collection "content" where its order by the "position" field in asc order
        db.collection("content")
                .orderBy("position", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //Retrieves all available topics from the database
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                //Converts document into a Map<String, Object>
                                Map<String, Object> topicMap = document.getData();
                                String id = document.getId();
                                String topic = topicMap.get("topic").toString();
                                String content = topicMap.get("content_body").toString();
                                String video = topicMap.get("video_id").toString();

                                //adds new topic to the list
                                topics.add(new Topic(id, topic, true));
                            }
                            //Sets a new adapter list based on the Firestore results
                            mAdapter.setTopicList(topics);
                        }
                        else {
                            //Error handling
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            mStatus.setVisibility(View.VISIBLE);
                        }
                        //Progress UI finishing
                        mLoading.setVisibility(View.INVISIBLE);
                        mProgress.setVisibility(View.INVISIBLE);
                    }
                });
    }


    /**
     * Handles the navigation of the selected learning tool.
     * @param position of the row selected
     */
    private void launchLearningContent(int position) {
        String type = getActivity().getIntent().getStringExtra("LEARN_TYPE"); //user choice from prev. frag.
        getActivity().getIntent().putExtra("POSITION", position); //position == topic id
        if(getActivity() instanceof BaseActivity) {
            if(type.equals("Flashcards")) {
                //Launch flashcards if user selected flashcard in prev. fragment
                ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_flashcard);
            }
            else if(type.equals("Videos")) {
                //Launch video content if user selected videos in prev. fragment
                ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_video_learn);
            }
        }
    }

}

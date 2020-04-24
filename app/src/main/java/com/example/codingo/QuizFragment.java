package com.example.codingo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class QuizFragment extends Fragment {

    private final String TAG = "QuizFragment";
    private RecyclerView mRecyclerView;
    private TopicAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgress;
    private TextView mLoading;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_quiz, container, false);

        mRecyclerView = root.findViewById(R.id.rvList);
        mProgress = root.findViewById(R.id.progressBar);
        mLoading = root.findViewById(R.id.tv_loading);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        TopicAdapter.RecyclerViewClickListener listener = new TopicAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                launchQuizStart(position);
            }
        };
        mAdapter = new TopicAdapter(new ArrayList<>(), listener);
        mRecyclerView.setAdapter(mAdapter);
        getTopics();
        return root;
    }

    protected void getTopics() {
        // UI rendering to show progress
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.VISIBLE);

        List<Topic> topics = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                                Map<String, Object> topicMap = document.getData();
                                String id = document.getId();
                                String topic = topicMap.get("topic").toString();
                                String content = topicMap.get("content_body").toString();
                                String video = topicMap.get("video_id").toString();
                                topics.add(new Topic(id, topic, true));
                                mLoading.setVisibility(View.INVISIBLE);
                            }
                            //Sets a new adapter list based on the Firestore results
                            mAdapter.setTopicList(topics);
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            mLoading.setText("No topics retrieved due to network error!");
                        }
                        //Progress UI finishing
                        mProgress.setVisibility(View.INVISIBLE);
                    }
                });
    }

    public void launchQuizStart(int position) {
        Intent intent = new Intent(getActivity(), QuizActivity.class);
        intent.putExtra("TOPIC_ID", position);
        startActivity(intent);
    }
}

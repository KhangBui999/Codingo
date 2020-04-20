package com.example.codingo.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.codingo.BaseActivity;
import com.example.codingo.Model.Content;
import com.example.codingo.Model.Topic;
import com.example.codingo.R;
import com.example.codingo.TopicAdapter;
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
 * A simple {@link Fragment} subclass.
 */
public class LearnTopicFragment extends Fragment {

    private final String TAG = "LearnTopicFragment";
    private RecyclerView mRecyclerView;
    private TopicAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mStatus, mLoading;
    private ProgressBar mProgress;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public LearnTopicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_learn_topic, container, false);

        mStatus = root.findViewById(R.id.tv_status);
        mLoading = root.findViewById(R.id.tv_progress);
        mRecyclerView = root.findViewById(R.id.rvList);
        mProgress = root.findViewById(R.id.pb_topic);

        mStatus.setVisibility(View.INVISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        TopicAdapter.RecyclerViewClickListener listener = new TopicAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                launchLearningContent(position);
            }
        };
        mAdapter = new TopicAdapter(new ArrayList<>(), listener);
        mRecyclerView.setAdapter(mAdapter);
        getTopics();
        return root;
    }

    private void getTopics() {
        mProgress.setIndeterminate(true);
        mLoading.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.VISIBLE);
        List<Topic> topics = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("content")
                .orderBy("position", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> topicMap = document.getData();
                                String id = document.getId();
                                String topic = topicMap.get("topic").toString();
                                String content = topicMap.get("content_body").toString();
                                String video = topicMap.get("video_id").toString();
                                topics.add(new Topic(id, topic, true));
                                mAdapter.setTopicList(topics);
                                mLoading.setVisibility(View.INVISIBLE);
                                mProgress.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            mLoading.setVisibility(View.INVISIBLE);
                            mProgress.setVisibility(View.INVISIBLE);
                            mStatus.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void launchLearningContent(int position) {
        String type = getActivity().getIntent().getStringExtra("LEARN_TYPE");
        getActivity().getIntent().putExtra("POSITION", position);
        if(getActivity() instanceof BaseActivity) {
            if(type.equals("Flashcards")) {
                ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_flashcard);
            }
            else if(type.equals("Videos")) {
                ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_video_learn);
            }
        }
    }

}

package com.example.codingo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codingo.BaseActivity;
import com.example.codingo.Model.Topic;
import com.example.codingo.R;
import com.example.codingo.TopicAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LearnTopicFragment extends Fragment {

    private final String TAG = "LearnTopicFragment";
    private RecyclerView mRecyclerView;
    private TopicAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public LearnTopicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_learn_topic, container, false);

        mRecyclerView = root.findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        TopicAdapter.RecyclerViewClickListener listener = new TopicAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                launchLearningContent();
            }
        };
        mAdapter = new TopicAdapter(new ArrayList<>(), listener);
        mRecyclerView.setAdapter(mAdapter);
        if(getActivity() instanceof BaseActivity) {
            mAdapter.setTopicList(((BaseActivity) getActivity()).getContentTopic());
        }
        return root;
    }

    private void launchLearningContent() {
        String type = getActivity().getIntent().getStringExtra("LEARN_TYPE");
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

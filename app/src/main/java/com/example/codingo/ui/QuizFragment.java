package com.example.codingo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codingo.BaseActivity;
import com.example.codingo.Model.Topic;
import com.example.codingo.QuizActivity;
import com.example.codingo.R;
import com.example.codingo.Utilities.TopicAdapter;

import java.util.ArrayList;

public class QuizFragment extends Fragment {

    private final String TAG = "QuizFragment";
    private RecyclerView mRecyclerView;
    private TopicAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_quiz, container, false);
        ArrayList<Topic> dummyData = new ArrayList<>();
        dummyData.add(new Topic("1", "Intro to Java", false));
        dummyData.add(new Topic("2", "Data types", false));
        dummyData.add(new Topic("3", "Strings", false));

        mRecyclerView = root.findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        TopicAdapter.RecyclerViewClickListener listener = new TopicAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                launchQuizStart();
            }
        };
        mAdapter = new TopicAdapter(dummyData, listener);
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }

    public void launchQuizQuestions() {
        Intent intent = new Intent(getContext(), QuizActivity.class);
        startActivity(intent);
    }

    public void launchQuizStart() {
//        FragmentManager manager = getParentFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        Fragment fragment = new QuizStartFragment();
//        transaction.replace(R.id.nav_host_fragment, fragment);
//        transaction.commit();
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_quiz_start);
        }
    }
}

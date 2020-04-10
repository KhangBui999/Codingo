package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.codingo.Model.Topics;
import com.example.codingo.Utilities.BottomNavHelper;
import com.example.codingo.Utilities.TopicAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class QuizTopicActivity extends AppCompatActivity {

    private final String TAG = "codingo.system.message";
    private BottomNavigationView mNavigation;
    private RecyclerView mRecyclerView;
    private TopicAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called from QuizTopicActivity");
        setContentView(R.layout.activity_quiz_topic);

        Log.d(TAG, "BottomNavigationBar rendering");
        BottomNavHelper bnh = new BottomNavHelper();
        mNavigation = bnh.getNavMenu(this, R.id.navigation);
        Log.d(TAG, "BottomNavigationBar rendered");

        ArrayList<Topics> dummyData = new ArrayList<>();
        dummyData.add(new Topics("1", "Intro to Java", false));
        dummyData.add(new Topics("2", "Data types", false));
        dummyData.add(new Topics("3", "Strings", false));

        mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        TopicAdapter.RecyclerViewClickListener listener = new TopicAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                launchQuizActivity();
            }
        };
        mAdapter = new TopicAdapter(dummyData, listener);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void launchQuizActivity() {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }
}

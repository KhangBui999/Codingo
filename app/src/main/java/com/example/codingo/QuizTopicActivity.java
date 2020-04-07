package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.codingo.Utilities.BottomNavHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuizTopicActivity extends AppCompatActivity {

    private final String TAG = "codingo.system.message";
    private BottomNavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called from QuizTopicActivity");
        setContentView(R.layout.activity_quiz_topic);

        Log.d(TAG, "BottomNavigationBar rendering");
        BottomNavHelper bnh = new BottomNavHelper();
        mNavigation = bnh.getNavMenu(this, R.id.navigation);
        Log.d(TAG, "BottomNavigationBar rendered");
    }
}

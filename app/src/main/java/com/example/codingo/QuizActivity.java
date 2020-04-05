package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.codingo.Utilities.BottomNavHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuizActivity extends AppCompatActivity {

    private String TAG = "codingo.system.message";
    private BottomNavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Log.i(TAG, "BottomNavigationBar rendering");
        BottomNavHelper bnh = new BottomNavHelper();
        mNavigation = bnh.getNavMenu(this, R.id.navigation);
        Log.i(TAG, "BottomNavigationBar rendered");
    }
}

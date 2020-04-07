package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.codingo.Utilities.BottomNavHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ResultActivity extends AppCompatActivity {

    private BottomNavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        BottomNavHelper bnh = new BottomNavHelper();
        mNavigation = bnh.getNavMenu(this, R.id.navigation);
    }
}

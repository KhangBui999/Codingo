package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.codingo.Utilities.BottomNavHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavHelper bnh = new BottomNavHelper();
        mNavigation = bnh.getNavMenu(this, R.id.navigation);
    }
}

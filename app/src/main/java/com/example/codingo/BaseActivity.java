package com.example.codingo;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class BaseActivity extends AppCompatActivity {

    protected NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(ContextCompat.getColorStateList(this, R.color.nav_menu_selector));
        navView.setItemTextColor(ContextCompat.getColorStateList(this, R.color.nav_menu_selector));

        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_quiz, R.id.navigation_learn, R.id.navigation_quiz_start,
                R.id.navigation_flashcard, R.id.navigation_learn_topic, R.id.navigation_video_learn)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public NavController getNavController() {
        return navController;
    }
}

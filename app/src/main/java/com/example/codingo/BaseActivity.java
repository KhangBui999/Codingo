/*
 * INFS3634 Group Assignment 2020 T1 - Team 31
 *
 * This is an Android mobile application that showcases the use of functional Android building blocks
 * and the implementation of other features such as Google Firebase and API calls. Submitted as part of
 * a group assignment for the course, INFS3634.
 *
 * Authors:
 * Shara Bakal, Khang Bui, Laurence Truong & Brian Vu
 *
 */

package com.example.codingo;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/**
 * BaseActivity hosts the BottomNavigationView for the majority of fragments in the Codingo app.
 * This class handles most of the navigation for the mobile app and allows us to have a consistent
 * UI throughout each screen/fragment.
 */
public class BaseActivity extends AppCompatActivity {

    private final String TAG = "com.example.codingo.BaseActivity";
    protected NavController navController;
    private FirebaseAuth mAuth;

    //Default BottomNavigationView method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //Firebase data retrieval code
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        loadUserInformation(currentUser);

        //Rendering of the BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(ContextCompat.getColorStateList(this, R.color.nav_menu_selector));
        navView.setItemTextColor(ContextCompat.getColorStateList(this, R.color.nav_menu_selector));

        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_quiz, R.id.navigation_learn, R.id.navigation_flashcard,
                R.id.navigation_learn_topic, R.id.navigation_video_learn, R.id.navigation_profile_menu,
                R.id.navigation_user_profile, R.id.navigation_leaderboard, R.id.navigation_settings)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Debugging method to detect whether a user has logged in or not
     * @param user is the current Firebase user account logged in
     */
    protected void loadUserInformation(FirebaseUser user) {
        if(user != null) {
            Log.d(TAG, "A logged in user has been detected.");
        }
        else {
            Log.d(TAG, "No current user detected.");
        }
    }

    /**
     * Retrieves the NavController for fragment classes to use
     * @return the NavController used by this activity.
     */
    public NavController getNavController() {
        return navController;
    }
}

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
                R.id.navigation_home, R.id.navigation_quiz, R.id.navigation_learn, R.id.navigation_quiz_start,
                R.id.navigation_flashcard, R.id.navigation_learn_topic, R.id.navigation_video_learn)
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

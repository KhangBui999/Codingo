package com.example.codingo.Utilities;

import android.app.Activity;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.codingo.QuizActivity;
import com.example.codingo.QuizTopicActivity;
import com.example.codingo.R;
import com.example.codingo.ResultActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * This is a Helper class used to minimise code redundancy and improve code readability
 * of the application of the project. This Helper class in particular acts an auxiliary class
 * for the BottomNavigationView used in most of the Activity classes.
 */
public class BottomNavHelper {

    private Activity activity;

    /**
     * This returns a navigation menu that can be used by all activities.
     * Using this navigation method in all methods ensures consistency of every navigation.
     *
     * @param act is current activity invoking the method.
     * @param id is the element id of the activity's layout view.
     * @return the navigation menu
     */
    public BottomNavigationView getNavMenu(Activity act, int id) {
        activity = act;
        BottomNavigationView mNavMenu = activity.findViewById(id); //links xml element
        mNavMenu.getMenu().clear(); //clears default BottomNavigationView element
        mNavMenu.inflateMenu(R.menu.bottom_nav_items); //sets BottomNavigationView layout
        mNavMenu.setBackgroundResource(R.color.colorNavBackground); //changes color of navigation

        mNavMenu.setItemTextColor(ContextCompat.getColorStateList(activity,
                R.color.nav_menu_selector)); //sets a ColorStateList for selected icons

        mNavMenu.setItemIconTintList(ContextCompat.getColorStateList(activity,
                R.color.nav_menu_selector)); //sets a ColorStateList for selected icons

        //Sets the active icon depending on which ActivityGroup parameterised activity belongs to
        if (checkActivityGroup() == 0) {
            mNavMenu.setSelectedItemId(R.id.action_learn);
        }
        else if (checkActivityGroup() == 1) {
            mNavMenu.setSelectedItemId(R.id.action_quiz);
        }

        //Sets the listener for each icon/button
        mNavMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.action_learn:
                        launchLearnActivity();
                        item.setCheckable(true);
                        return true;
                    case R.id.action_quiz:
                        launchQuizActivity();
                        item.setCheckable(true);
                        return true;
                    case R.id.action_more:
                        launchMoreActivity();
                        item.setCheckable(true);
                        return true;
                }
                return false;
            }
        });
        return mNavMenu;
    }

    //Determines which of the three ActivityGroup the parameterised activity belongs to
    private int checkActivityGroup() {
        if(activity instanceof QuizTopicActivity || activity instanceof QuizActivity || activity instanceof ResultActivity) {
            return 1;
        }
        else {
            return 0;
        }
    }

    private void launchLearnActivity() {
        Log.d("com.example.codingo", "launchLearnActivity: Attempting to launch");
    }

    private void launchQuizActivity() {
        Log.d("com.example.codingo", "launchQuizActivity: Attempting to launch");
    }

    private void launchMoreActivity() {
        Log.d("com.example.codingo", "launchMoreActivity: Attempting to launch");
    }

}

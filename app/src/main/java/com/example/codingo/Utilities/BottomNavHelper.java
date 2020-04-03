package com.example.codingo.Utilities;

import android.app.Activity;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.codingo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavHelper {

    /**
     * This returns a navigation menu that can be used by all activities.
     * Using this navigation method in all methods ensures consistency of every navigation.
     *
     * @param activity is current activity invoking the method.
     * @param id is the element id of the activity's layout view.
     * @return the navigation menu
     */
    public BottomNavigationView getNavMenu(Activity activity, int id) {
        BottomNavigationView mNavMenu = activity.findViewById(id);
        mNavMenu.getMenu().clear();
        mNavMenu.inflateMenu(R.menu.bottom_nav_items);
        mNavMenu.setBackgroundResource(R.color.colorNavBackground);
        mNavMenu.setItemTextColor(ContextCompat.getColorStateList(activity, R.color.nav_menu_selector));
        mNavMenu.setItemIconTintList(ContextCompat.getColorStateList(activity, R.color.nav_menu_selector));

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

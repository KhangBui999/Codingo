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

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codingo.R;

/**
 * A simple {@link Fragment} subclass that contains options to access the current user's profile,
 * the leaderboard and the settings screen.
 */
public class ProfileMenuFragment extends Fragment {

    private CardView mProfile;
    private CardView mLeaderboard;
    private CardView mSettings;

    public ProfileMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile_menu, container, false);
        mProfile = root.findViewById(R.id.cv_userProfile);
        mLeaderboard = root.findViewById(R.id.cv_leaderboard);
        mSettings = root.findViewById(R.id.cv_settings);

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_user_profile);
            }
        });

        mLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_leaderboard);
            }
        });

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_settings);
            }
        });
        return root;
    }
}

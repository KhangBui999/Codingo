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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.codingo.BaseActivity;
import com.example.codingo.R;

/**
 * The LearnFragment acts as the navigation before choosing a learning topic
 * Allows the user to access learning via (Flashcards) or (Videos & Reading)
 */
public class LearnFragment extends Fragment {

    private CardView mFlashcards, mVideo;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflating the root layout
        View root = inflater.inflate(R.layout.fragment_learn, container, false);

        //Linking the XML elements
        mFlashcards = root.findViewById(R.id.cv_learn);
        mVideo = root.findViewById(R.id.cv_video);

        //onClickListeners for Flashcards and Videos
        mFlashcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLearnToolFragment("Flashcards");
            }
        });
        mVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLearnToolFragment("Videos");
            }
        });
        return root;
    }

    /**
     * Launches the LearnTopicFragment to enable user to select learning style
     * @param type is the learning option the user chooses
     */
    private void launchLearnToolFragment(String type) {
        if(getActivity() instanceof BaseActivity) {
            getActivity().getIntent().putExtra("LEARN_TYPE", type);
            ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_learn_topic);
        }
    }
}

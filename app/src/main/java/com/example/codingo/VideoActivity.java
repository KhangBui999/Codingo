package com.example.codingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.codingo.Model.Content;
import com.example.codingo.Utilities.BottomNavHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class VideoActivity extends YouTubeBaseActivity {
    private YouTubePlayerView mView;
    private YouTubePlayer.OnInitializedListener mListener;
    private TextView mTopic;
    private TextView mContent;
    private TextView mPrompt;
    private BottomNavigationView mNavigation;
    private Content mCon;
    private static final String API_KEY = "AIzaSyB-X0GINXGVnrcwiFAMx9bZliAvMSEcEu0";
    // initialised variables for the youtube player


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent = getIntent();
        int position = intent.getIntExtra("VIDEO_ID", 0);
        ArrayList<Content> contents = Content.getContents();

        BottomNavHelper bnh = new BottomNavHelper();
        mNavigation = bnh.getNavMenu(this, R.id.navigation);

        // Retrieving information from the contents class
        mCon = contents.get(position);
        mPrompt= findViewById(R.id.prompt);
        mView = findViewById(R.id.view);
        mTopic =  findViewById(R.id.topic);
        mContent = findViewById(R.id.content);

        // Changes text based on content
        mTopic.setText(mCon.getTopic());
        mContent.setText(mCon.getContent());

        mListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(mCon.getVideo());
            }
            //Interface definition for callbacks that are invoked when player initialization succeeds or fails
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                mPrompt.setText("This video is currently not available!");
            }
        };

        // When clicking the Youtube player, the youtube video will load
        mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                mView.initialize(API_KEY, mListener);

            }
        });

    }
}

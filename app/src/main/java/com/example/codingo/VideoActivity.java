package com.example.codingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


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
    private Content mCon;
    private static final String API_KEY = "AIzaSyB-X0GINXGVnrcwiFAMx9bZliAvMSEcEu0";
    // initialised variables for the youtube player


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent = getIntent();
        int position = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0);
        ArrayList<Content> contents = Content.getContents();
        // retrieving information from the contents class
        mCon=contents.get(position);
        mPrompt= (TextView) findViewById(R.id.prompt);
        mView = (YouTubePlayerView) findViewById(R.id.view);
        mTopic = (TextView) findViewById(R.id.topic);
        mContent = (TextView) findViewById(R.id.content);

        mTopic.setText(mCon.getTopic());
        mContent.setText(mCon.getContent());
        // changes text based on content

        mListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(mCon.getVideo());
            }
            //Interface definition for callbacks that are invoked when player initialization succeeds or fails
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                mPrompt.setText("This activity_video is not available!");
            }
        };
        // When clicking the Youtube player, the youtube player will load
        mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                mView.initialize(API_KEY, mListener);

            }
        });

    }
}

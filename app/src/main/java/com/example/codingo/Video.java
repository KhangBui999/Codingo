package com.example.codingo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Video extends YouTubeBaseActivity {
    YouTubePlayerView mView;
    YouTubePlayer.OnInitializedListener mListener;
    Button mButton;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        mView = (YouTubePlayerView) findViewById(R.id.view);
        mButton = (Button) findViewById(R.id.button);
        mListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("oHg5SJYRHA0");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                mView.initialize(API.getApiKey(), mListener);

            }
        });

    }
}

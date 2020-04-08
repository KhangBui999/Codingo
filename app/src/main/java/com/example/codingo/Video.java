package com.example.codingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class Video extends YouTubeBaseActivity {
    private YouTubePlayerView mView;
    private YouTubePlayer.OnInitializedListener mListener;
    private Button mButton;
    private TextView mTopic;
    private TextView mContent;
    private Content mCon;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        Intent intent = getIntent();
        int position = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0);
        ArrayList<Content> contents = Content.getContents();
        mCon=contents.get(position);

        mView = (YouTubePlayerView) findViewById(R.id.view);
        mButton = (Button) findViewById(R.id.button);
        mTopic = (TextView) findViewById(R.id.topic);
        mContent = (TextView) findViewById(R.id.content);

        mTopic.setText(mCon.getTopic());
        mContent.setText(mCon.getContent());


        mListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(mCon.getVideo());
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

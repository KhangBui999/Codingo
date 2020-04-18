package com.example.codingo.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.codingo.Model.Content;
import com.example.codingo.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoLearnFragment extends Fragment implements YouTubePlayer.OnInitializedListener {

    private TextView mTopic;
    private TextView mContent;
    private Content mCon;
    private static final String API_KEY = "AIzaSyB-X0GINXGVnrcwiFAMx9bZliAvMSEcEu0";

    public VideoLearnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_video_learn, container, false);
        Intent intent = getActivity().getIntent();
        int position = intent.getIntExtra("VIDEO_ID", 0);
        ArrayList<Content> contents = Content.getContents();

        mCon = contents.get(position);
//        mPrompt= findViewById(R.id.prompt);
//        mView = findViewById(R.id.view);
        mTopic =  root.findViewById(R.id.tv_topic);
        mContent = root.findViewById(R.id.tv_content);

        loadVideo();

        mTopic.setText(mCon.getTopic());
        mContent.setText(mCon.getContent());

        return root;
    }

    private void loadVideo() {
        YouTubePlayerSupportFragment youtubePlayerFragment = new YouTubePlayerSupportFragment();
        youtubePlayerFragment.initialize(API_KEY, this);
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_fragment, youtubePlayerFragment);
        transaction.commit();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.cueVideo(mCon.getVideo());
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        //Fill this later
    }

}

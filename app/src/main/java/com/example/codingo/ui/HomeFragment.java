package com.example.codingo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.codingo.BaseActivity;
import com.example.codingo.R;

public class HomeFragment extends Fragment {

    private ImageButton mQuizButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mQuizButton = root.findViewById(R.id.ib_quiz);
        mQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchQuizFragment();
            }
        });
        return root;
    }

    private void launchQuizFragment() {
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_quiz);
        }
    }
}

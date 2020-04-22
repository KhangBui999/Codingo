package com.example.codingo.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.codingo.BaseActivity;
import com.example.codingo.QuizActivity;
import com.example.codingo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizStartFragment extends Fragment {

    TextView mReselect;
    Button mStart;

    public QuizStartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_quiz_start, container, false);
        mReselect = root.findViewById(R.id.tv_alternate);
        mStart = root.findViewById(R.id.btn_start);
        mReselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToQuizTopic();
            }
        });
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchQuizQuestions();
            }
        });
        return root;
    }

    private void backToQuizTopic() {
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_quiz);
        }
    }

    protected void launchQuizQuestions() {
        Intent intent = new Intent(getActivity(), QuizActivity.class);
        intent.putExtra("TOPIC_ID", getActivity().getIntent().getIntExtra("TOPIC_ID", 0));
        startActivity(intent);
    }

}

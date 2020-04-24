package com.example.codingo;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.codingo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    private CardView mCardUser, mCardBadge, mCardStat;
    private TextView mDisplayName, mPoints, mAttempt, mCorrect, mAccuracy;
    private FirebaseAuth mAuth;


    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);
        mCardUser = root.findViewById(R.id.cardView);
        mCardBadge = root.findViewById(R.id.cardView2);
        mCardStat = root.findViewById(R.id.cardView3);

        mDisplayName = root.findViewById(R.id.tv_display);
        mPoints = root.findViewById(R.id.tv_points);

        mAttempt = root.findViewById(R.id.tv_attempts);
        mCorrect = root.findViewById(R.id.tv_correct);
        mAccuracy = root.findViewById(R.id.tv_accRate);

        getUserData();
        return root;
    }

    protected void getUserData() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
    }
}

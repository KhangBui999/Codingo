package com.example.codingo;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.codingo.Entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    private final String TAG = "com.example.codingo.UserProfileFragmnent";
    private CardView mCardUser, mCardBadge, mCardStat;
    private ImageView mImage;
    private TextView mDisplayName, mPoints, mAttempt, mCorrect, mAccuracy, mLoading;
    private ProgressBar mProgress;
    private FirebaseAuth mAuth;
    private User userAccount;


    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mLoading = root.findViewById(R.id.tv_loading);
        mProgress = root.findViewById(R.id.pb_loading);

        mCardUser = root.findViewById(R.id.cardView);
        mCardBadge = root.findViewById(R.id.cardView2);
        mCardStat = root.findViewById(R.id.cardView3);

        mImage = root.findViewById(R.id.imageView2);
        mDisplayName = root.findViewById(R.id.tv_display);
        mPoints = root.findViewById(R.id.tv_points);

        mAttempt = root.findViewById(R.id.tv_attempts);
        mCorrect = root.findViewById(R.id.tv_correct);
        mAccuracy = root.findViewById(R.id.tv_accRate);

        getUserData();
        return root;
    }

    protected void getUserData() {
        mCardUser.setVisibility(View.INVISIBLE);
        mCardBadge.setVisibility(View.INVISIBLE);
        mCardStat.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.VISIBLE);
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        String name = user.getDisplayName();
        String imageUrl = user.getPhotoUrl().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> userMap = document.getData();
                        ArrayList<String> badgeList = (ArrayList<String>) userMap.get("badges");
                        Long ucXp = (Long) userMap.get("xp");
                        Long ucPoints = (Long) userMap.get("points");
                        Long ucCorrect = (Long) userMap.get("correct");
                        Long ucAttempt = (Long) userMap.get("attempt");
                        int xp = ucXp.intValue();
                        int points = ucPoints.intValue();
                        int correct = ucCorrect.intValue();
                        int attempt = ucAttempt.intValue();
                        userAccount = new User(uid, name, imageUrl, xp, points, badgeList, correct, attempt);
                        loadUserProfile(db, badgeList);
                    }
                    else {
                        Log.d(TAG, "No such document");
                        mLoading.setText("ERROR: User not found!");
                    }
                }
                else {
                    Log.d(TAG, "get failed with ", task.getException());
                    mLoading.setText("NETWORK ERROR: No data loaded!");
                }
                mProgress.setVisibility(View.INVISIBLE);
            }
        });

    }

    protected void loadUserProfile(FirebaseFirestore db, ArrayList<String> list) {
        mLoading.setVisibility(View.INVISIBLE);
        Glide.with(getActivity())
                .load(userAccount.getProfilePicUrl())
                .placeholder(R.mipmap.ic_logo)
                .into(mImage);
        mDisplayName.setText(userAccount.getName());
        mPoints.setText(Integer.toString(userAccount.getPoints()));
        mAttempt.setText(Integer.toString(userAccount.getAttempts()));
        mCorrect.setText(Integer.toString(userAccount.getCorrect()));
        mAccuracy.setText(userAccount.getAccuracyRate());
        mCardUser.setVisibility(View.VISIBLE);
        mCardBadge.setVisibility(View.VISIBLE);
        mCardStat.setVisibility(View.VISIBLE);
    }

}

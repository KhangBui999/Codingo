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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.codingo.Entities.Badge;
import com.example.codingo.Entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass that displays the User Profile.
 * Used as a controller class to perform a database transaction to read user data.
 * Converts user data to display on the screen. Displays: display name, total points earned,
 * badges obtained and quiz statistics.
 */
public class UserProfileFragment extends Fragment {

    private final String TAG = "com.example.codingo.UserProfileFragmnent";
    private CardView mCardUser, mCardBadge, mCardStat;
    private ImageView mImage;
    private TextView mDisplayName, mPoints, mAttempt, mCorrect, mAccuracy, mLoading;
    private ProgressBar mProgress;
    private RecyclerView mRecyclerView;
    private BadgeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
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

        //Linking XMl elements
        mLoading = root.findViewById(R.id.tv_loading);
        mProgress = root.findViewById(R.id.pb_loading);

        mCardUser = root.findViewById(R.id.cardView);
        mCardBadge = root.findViewById(R.id.cardView2);
        mCardStat = root.findViewById(R.id.cardView3);

        mImage = root.findViewById(R.id.imageView2);
        mDisplayName = root.findViewById(R.id.tv_display);
        mPoints = root.findViewById(R.id.tv_points);

        mRecyclerView = root.findViewById(R.id.rvList);

        mAttempt = root.findViewById(R.id.tv_attempts);
        mCorrect = root.findViewById(R.id.tv_correct);
        mAccuracy = root.findViewById(R.id.tv_accRate);

        getUserData(); //retrieves User Data
        return root;
    }

    /**
     * This method performs database transactions to read data from the Firestore database
     */
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
                        loadProfileBadges(db, badgeList);
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

    /**
     * This method handles the badge display UI
     * @param db is the database instance
     * @param list is the data retrieved from the database.
     */
    protected void loadProfileBadges(FirebaseFirestore db, ArrayList<String> list) {
        List<Badge> badges = new ArrayList<>();
        db.collection("badges")
                .orderBy("position", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "QuerySnapshot successful");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                Map<String, Object> badgeMap = document.getData();
                                String name = badgeMap.get("name").toString();
                                String desc = badgeMap.get("desc").toString();
                                String refString = badgeMap.get("image_ref").toString();
                                int imageRef = getResources().getIdentifier(refString, "drawable", getActivity().getPackageName());
                                Long ucPosition = (Long) badgeMap.get("position");
                                int pos = ucPosition.intValue();
                                if(list.contains(document.getId())) {
                                    badges.add(new Badge(document.getId(), name, desc, imageRef, pos));
                                }
                                else {
                                    badges.add(new Badge(document.getId(), "Not Achieved",
                                            desc, R.drawable.bg_homeicon, pos));
                                }
                            }
                            loadEntireProfile(badges);
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            mLoading.setText("NETWORK ERROR: No data loaded!");
                        }
                    }
                });
    }

    /**
     * This method updates the entire profile's UI (except for the badge UI which is completed first)
     * @param badges is the new set of badges
     */
    private void loadEntireProfile(List<Badge> badges) {
        mLoading.setVisibility(View.INVISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayout);
        BadgeAdapter.RecyclerViewClickListener listener = new BadgeAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Do nothing for now
            }
        };
        mAdapter = new BadgeAdapter(badges, listener);
        mRecyclerView.setAdapter(mAdapter);
        mProgress.setVisibility(View.INVISIBLE);
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

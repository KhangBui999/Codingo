package com.example.codingo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.codingo.Entities.User;
import com.example.codingo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass that shows a leaderboard. Shows the top 10 players with
 * the most points. Shows their current position in the leaderboard, their display name, their
 * avatar and their total amount of points. The leadderboard is updated every time the user
 * accesses this page as a result of the Firebase implementation.
 */
public class LeaderboardFragment extends Fragment {

    private final String TAG = "com.example.codingo.LeaderboardFragment";
    private RecyclerView mRecyclerView;
    private UserAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ProgressBar mProgress;
    private TextView mStatus;

    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        //Link XML elements
        mRecyclerView = root.findViewById(R.id.rvList);
        mProgress = root.findViewById(R.id.pb_loading);
        mStatus = root.findViewById(R.id.tv_status);

        //Set the RecyclerView adapter
        mRecyclerView.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayout);
        UserAdapter.RecyclerViewClickListener listener = new UserAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Do nothing
            }
        };
        mAdapter = new UserAdapter(new ArrayList<>(), listener);
        mRecyclerView.setAdapter(mAdapter);

        updateUI(); //update the UI
        return root;
    }

    protected void updateUI() {
        //Server connection progress UI
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);
        mStatus.setVisibility(View.VISIBLE);

        //new ArrayList to store the top 10 players
        List<User> topUsers = new ArrayList<>();

        //FirebaseFirestore transaction
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //gets data from the collection "users" ordered by points in desc order. max 10 users.
        db.collection("users")
                .orderBy("points", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "QuerySnapshot successful");
                            //Converts the DocumentSnapshot into User objects that can be added to list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Retrieving user data from the document
                                String uid = document.getId();
                                Map<String, Object> userMap = document.getData(); //document -> map
                                String name = userMap.get("name").toString();
                                String image = userMap.get("image").toString();
                                ArrayList<String> badgeList = (ArrayList<String>) userMap.get("badges");

                                //these number fields are Long variables in the database
                                Long ucXp = (Long) userMap.get("xp");
                                Long ucPoints = (Long) userMap.get("points");
                                Long ucCorrect = (Long) userMap.get("correct");
                                Long ucAttempt = (Long) userMap.get("attempt");

                                //convert Long variables to int variables
                                int xp = ucXp.intValue();
                                int points = ucPoints.intValue();
                                int correct = ucCorrect.intValue();
                                int attempt = ucAttempt.intValue();

                                //add new user object to the list
                                topUsers.add(new User(uid, name, image, xp, points, badgeList, correct, attempt));
                            }
                            mProgress.setVisibility(View.INVISIBLE); //status update for UI
                            mStatus.setVisibility(View.INVISIBLE); //status update for UI
                            mAdapter.updateUserList(topUsers); //update list for adapter
                        }
                        else {
                            //Error handling
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            mStatus.setText("Could not load leader board. Try later!");
                        }
                    }
                });
    }
}

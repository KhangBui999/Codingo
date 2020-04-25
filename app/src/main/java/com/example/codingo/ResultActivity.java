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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * The ResultActivity is launched after the successful completion of the QuizActivity by a user.
 * This is a controller class that handles database transactions such as updating the points, xp,
 * question statistics and badges of a user. It also shows a congratulatory message for the user.
 */
public class ResultActivity extends AppCompatActivity {

    private TextView mPoints;
    private Button mBtn;
    private ProgressBar mProgress;
    private CardView mCardView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Link XML elements
        mPoints = findViewById(R.id.tv_points);
        mBtn = findViewById(R.id.btn_ok);
        mProgress = findViewById(R.id.pb_loading);
        mCardView = findViewById(R.id.cardView4);

        //Get intent values from the QuizActivity
        Intent intent = getIntent();
        int points = intent.getIntExtra("POINTS", 0);
        int totalQuest = intent.getIntExtra("NUM_OF_QUEST", 0);
        int topicId = intent.getIntExtra("TOPIC_ID", 0);

        //Begin db transaction
        updateUserData(topicId, points, totalQuest);
    }


    /**
     * This method is responsible for handling the database transaction to update user data with
     * the quiz data from the QuizActivity.
     *
     * @param topicId is the topic_id of the QuizActivity
     * @param points is the amount of points earnt from the quiz
     * @param totalQuest is the number of questions in the previous quiz
     */
    private void updateUserData(int topicId, int points, int totalQuest) {
        //Database Progress UI
        mCardView.setVisibility(View.INVISIBLE);
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);

        //Firebase Transaction
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Updates the current user's data in  the "users" collection in the Firestore server
        final DocumentReference userRef = db.collection("users").document(user.getUid());
        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                //Gets the current user's points, xp and quiz statistics
                DocumentSnapshot snapshot = transaction.get(userRef);

                //Adds points, xp and quiz data to statistics
                //NOTE: for xp, 200 is granted for each correct answer & 50 for each incorrect
                long newPoints = snapshot.getLong("points") + points;
                long newXp = snapshot.getLong("xp") + ((totalQuest*50)+points);
                long newAttempt = snapshot.getLong("attempt") + totalQuest;
                long newCorrect = snapshot.getLong("correct") + (points/200);

                //Gets the new badge list
                ArrayList<String> badges = (ArrayList<String>) snapshot.get("badges");
                ArrayList<String> newBadgeList = getNewBadgeList(topicId, points, totalQuest, badges, newPoints);

                //Updates data with new values
                transaction.update(userRef, "points", newPoints)
                        .update(userRef, "xp", newXp)
                        .update(userRef, "attempt", newAttempt)
                        .update(userRef, "correct", newCorrect)
                        .update(userRef, "badges", newBadgeList);
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Update UI to indicate transactionsuccess
                mProgress.setVisibility(View.INVISIBLE);
                mPoints.setText("You have earned "+points+" points!");
                mBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                mCardView.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Update UI to indicate transaction failure
                mProgress.setVisibility(View.INVISIBLE);
                mPoints.setText("Failed to load result!");
                mBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                mCardView.setVisibility(View.VISIBLE);
            }
        });
    }


    /**
     * This method updates the BadgeList by checking whether a user fulfill the criteria for
     * gaining a badge and if they already have received it (to prevent duplicates).
     *
     * @param topicId is the topic_id of the QuizActivity
     * @param points is the points earned from the quiz
     * @param totalQuest is the number of questions from the quiz
     * @param badges is the current badge list
     * @param newPoints is the updated number of points
     * @return the updated BadgeList
     */
    protected ArrayList<String> getNewBadgeList(int topicId, int points, int totalQuest,
                                                ArrayList<String> badges, long newPoints) {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(badges);

        //Topic badges
        if(points == (totalQuest*200)){
            String newTopicBadge = "0";
            //Badges are stored in hash codes on the Firebase server
            switch(topicId){
                case 0: newTopicBadge = "NjhSOydPScumEfkshn6V"; break;
                case 1: newTopicBadge = "JhQ7wEgIKZ2udrL4O3yK"; break;
                case 2: newTopicBadge = "eFITibZwha70GjSkgGtW"; break;
                case 3: newTopicBadge = "axb6mTmone4xRu2dWS18"; break;
                case 4: newTopicBadge = "7HUTiDf31TFoXfE0xYGe"; break;
                case 5: newTopicBadge = "rq34RI8igSXXh5WGulYl"; break;
            }
            if(!result.contains(newTopicBadge) && !newTopicBadge.equals("0")) {
                result.add(newTopicBadge);
            }
            if(result.size() == 6){
                //If user has completed 6 topics give the completionist badge
                result.add("HsnVObLUUHPmmNY1yFPE");
            }
        }
        if(newPoints > 1000000){
            //If user has more than 1 mill points, give this badge
            result.add("fOfh0PVSoOD5vrKwHUk4");
        }
        return result;
    }
}

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

        mPoints = findViewById(R.id.tv_points);
        mBtn = findViewById(R.id.btn_ok);
        mProgress = findViewById(R.id.pb_loading);
        mCardView = findViewById(R.id.cardView4);

        Intent intent = getIntent();
        int points = intent.getIntExtra("POINTS", 0);
        int totalQuest = intent.getIntExtra("NUM_OF_QUEST", 0);
        int topicId = intent.getIntExtra("TOPIC_ID", 0);

        updateUserData(topicId, points, totalQuest);
    }

    private void updateUserData(int topicId, int points, int totalQuest) {
        mCardView.setVisibility(View.INVISIBLE);
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);
        String newBadgeId = "0";
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference userRef = db.collection("users").document(user.getUid());
        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(userRef);
                long newPoints = snapshot.getLong("points") + points;
                long newXp = snapshot.getLong("xp") + ((totalQuest*50)+points);
                long newAttempt = snapshot.getLong("attempt") + totalQuest;
                long newCorrect = snapshot.getLong("correct") + (points/200);
                transaction.update(userRef, "points", newPoints)
                        .update(userRef, "xp", newXp)
                        .update(userRef, "attempt", newAttempt)
                        .update(userRef, "correct", newCorrect);
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
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
}

package com.example.codingo.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.codingo.Model.Content;
import com.example.codingo.Model.Flashcard;
import com.example.codingo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlashcardFragment extends Fragment {

    private final String TAG = "com.example.codingo.ui.FlashcardFragment";
    private Flashcard current;
    private ArrayList<Flashcard> list = new ArrayList<>();
    private int index = 0;

    private CardView mCard;
    private Button next_button;
    private Button prev_button;
    private TextView text;
    private TextView tvAnswer;
    private TextView tvInstructions;
    private TextView mStatus;
    private ImageView mLeft;
    private ImageView mRight;
    private ProgressBar mProgress;

    public FlashcardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flashcard, container, false);
        mCard = root.findViewById(R.id.cv_cardbutton);
        next_button = root.findViewById(R.id.next_button);
        prev_button = root.findViewById(R.id.prev_button);
        text = root.findViewById(R.id.text);
        tvAnswer = root.findViewById(R.id.tvAnswer);
        tvInstructions = root.findViewById(R.id.tvInstructions);
        mLeft = root.findViewById(R.id.iv_left);
        mRight = root.findViewById(R.id.iv_right);
        mProgress = root.findViewById(R.id.pb_flashcard);
        mStatus = root.findViewById(R.id.tv_loading);
        int position = getActivity().getIntent().getIntExtra("POSITION", 0);
        loadFlashcardSet(position);
        return root;
    }

    protected void loadFlashcardSet(int position) {
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);
        mStatus.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("flashcards")
                .whereEqualTo("topic_id", position)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> flashcardMap = document.getData();
                                String id = document.getId();
                                String question = flashcardMap.get("question").toString();
                                String answer = flashcardMap.get("answer").toString();
                                list.add(new Flashcard(id, question, answer));
                            }
                            current = list.get(index);
                            showFlashcard(); //Show the first flashcard
                            setCardFlip(); //Show the answer if the card is clicked
                            //Show the next flashcard
                            next_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    nextFlashcard();
                                }
                            });
                            //Show the previous flashcard
                            prev_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    prevFlashCard();
                                }
                            });
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        mProgress.setVisibility(View.INVISIBLE);
                        mStatus.setVisibility(View.INVISIBLE);
                    }
                });
    }

    protected void showFlashcard() {
        text.setText(current.getContent());
        text.setVisibility(View.VISIBLE);
        tvAnswer.setVisibility(View.GONE);
        tvInstructions.setVisibility(View.VISIBLE);
        setCardFlip();
        if (index <= 0) {
            tvInstructions.setText("Click to reveal the answer");
        }
    }

    protected void showAnswer() {
        tvAnswer.setText(current.getAnswer());
        text.setVisibility(View.GONE);
        tvAnswer.setVisibility(View.VISIBLE);
        tvInstructions.setVisibility(View.INVISIBLE);
        mCard.setOnClickListener(null);
    }

    protected void nextFlashcard() {
        if (index < list.size() - 1) {
            index++;
            current = list.get(index);
            showFlashcard();
        } else {
            tvInstructions.setText("You have reached the last flashcard! Good job!");
            index = -1;
            showFlashcard();
        }
    }

    protected void prevFlashCard() {
        if (index > 0) {
            index--;
            current = list.get(index);
            showFlashcard();
        } else {
            index = list.size();
            showFlashcard();
        }
    }

    protected void setCardFlip() {
        mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnswer();
            }

        });
    }
}

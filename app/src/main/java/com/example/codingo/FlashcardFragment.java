package com.example.codingo;

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

import com.example.codingo.Entities.Flashcard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass for controlling the Flashcard screen UI.
 * This class handles data retrieval from the Firebase server, flipping the flashcards and
 * navigation through the flashcard set.
 */
public class FlashcardFragment extends Fragment {

    private final String TAG = "com.example.codingo.FlashcardFragment";
    private Flashcard current; //current Flashcard
    private ArrayList<Flashcard> list = new ArrayList<>(); //flashcard set
    private int index = 0; //current position of the flashcard set

    private CardView mCard;
    private ImageView next_button, prev_button;
    private TextView text, tvAnswer, tvInstructions, mStatus;
    private ProgressBar mProgress;

    public FlashcardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflating the view of the Flashcard Fragment
        View root = inflater.inflate(R.layout.fragment_flashcard, container, false);

        //Linking the variables to XML elements
        mCard = root.findViewById(R.id.cv_cardbutton);
        text = root.findViewById(R.id.text);
        prev_button = root.findViewById(R.id.iv_left);
        next_button = root.findViewById(R.id.iv_right);
        tvAnswer = root.findViewById(R.id.tvAnswer);
        tvInstructions = root.findViewById(R.id.tvInstructions);
        mProgress = root.findViewById(R.id.pb_flashcard);
        mStatus = root.findViewById(R.id.tv_loading);

        //Get position from the topic selector via the intent of the fragment's activity
        int position = getActivity().getIntent().getIntExtra("POSITION", 0);

        //As RecyclerView was sorted based on topic_id, get the flashcards if topic_id matches position
        loadFlashcardSet(position); //invokes the method to retrieve data from Firestore
        return root;
    }

    /**
     * This method is responsible for retrieving the dataset from the Firebase server and
     * updating the UI accordingly.
     *
     * @param position of the RecyclerView that is retrieved via activity intent
     */
    protected void loadFlashcardSet(int position) {
        //Initial UI handling
        mProgress.setIndeterminate(true); //sets progress bar to be indeterminate
        mProgress.setVisibility(View.VISIBLE); //sets progress bar to be visible
        mStatus.setVisibility(View.VISIBLE); //sets loading text to be visible

        //Begin the FirebaseFirestore connection
        FirebaseFirestore db = FirebaseFirestore.getInstance(); //get application Firestore db
        db.collection("flashcards")
                .whereEqualTo("topic_id", position)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //Converts document collection for Firestore into a Flashcard set
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //Conversion of current document Map into Flashcard object
                                Map<String, Object> flashcardMap = document.getData();
                                String id = document.getId();
                                String question = flashcardMap.get("question").toString();
                                String answer = flashcardMap.get("answer").toString();
                                list.add(new Flashcard(id, question, answer));
                            }

                            //Successful UI update
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
                        //Remove progress UI
                        mProgress.setVisibility(View.INVISIBLE);
                        mStatus.setVisibility(View.INVISIBLE);
                    }
                });
    }

    /**
     * Responsible for rendering the UI when new flashcard is loaded
     */
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

    /**
     * Flips the flashcard to the back of the card (the answer).
     */
    protected void showAnswer() {
        tvAnswer.setText(current.getAnswer());
        text.setVisibility(View.GONE);
        tvAnswer.setVisibility(View.VISIBLE);
        tvInstructions.setVisibility(View.INVISIBLE);
        mCard.setOnClickListener(null);
    }

    /**
     * Loads the next flashcard in the set.
     */
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

    /**
     * Loads the previous flashcard in the set.
     */
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

    /**
     * Sets the onClickListener to detect when a user wants to flip a card
     */
    protected void setCardFlip() {
        mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnswer();
            }

        });
    }
}

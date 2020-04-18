package com.example.codingo.ui;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.codingo.Model.Flashcard;
import com.example.codingo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlashcardFragment extends Fragment {

    private Flashcard current;
    private ArrayList<Flashcard> list;
    private int index = 0;

    private CardView mCard;
    private Button next_button;
    private Button prev_button;
    private TextView text;
    private TextView tvAnswer;
    private TextView tvInstructions;

    public FlashcardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flashcard, container, false);

        //Creates ArrayList including the front and back of each card
        list = new ArrayList<>();
        list.add(new Flashcard("1", "Do or not do, there is no try.", "Yoda"));
        list.add(new Flashcard("2", "If I have seen further than others, it is by " +
                "standing upon the shoulders of giants", "Newton"));
        list.add(new Flashcard("3", "So I took the road less traveled by and that " +
                "has made all the difference.", "Robert Frost"));
        current = list.get(index);

        mCard = root.findViewById(R.id.cv_cardbutton);
        next_button = root.findViewById(R.id.next_button);
        prev_button = root.findViewById(R.id.prev_button);
        text = root.findViewById(R.id.text);
        tvAnswer = root.findViewById(R.id.tvAnswer);
        tvInstructions = root.findViewById(R.id.tvInstructions);

        //Show the first flashcard
        showFlashcard();

        //Show the answer if the card is clicked
        setCardFlip();

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
        return root;
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

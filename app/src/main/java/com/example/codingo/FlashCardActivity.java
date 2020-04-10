package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.codingo.Model.Flashcard;
import java.util.ArrayList;

public class FlashCardActivity extends AppCompatActivity {

    private Flashcard current;
    private ArrayList<Flashcard> list;
    private int index = 0;

    private CardView mCard;
    private Button next_button;
    private Button prev_button;
    private TextView text;
    private TextView tvAnswer;
    private TextView tvInstructions;

    public FlashCardActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        //Creates ArrayList including the front and back of each card
        list = new ArrayList<>();
        list.add(new Flashcard("1", "Do or not do, there is no try.", "Yoda"));
        list.add(new Flashcard("2", "If I have seen further than others, it is by " +
                "standing upon the shoulders of giants", "Newton"));
        list.add(new Flashcard("3", "So I took the road less traveled by and that " +
                "has made all the difference.", "Robert Frost"));
        current = list.get(index);

        mCard = findViewById(R.id.cv_cardbutton);
        next_button = findViewById(R.id.next_button);
        prev_button = findViewById(R.id.prev_button);
        text = findViewById(R.id.text);
        tvAnswer = findViewById(R.id.tvAnswer);
        tvInstructions = findViewById(R.id.tvInstructions);

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
        tvInstructions.setVisibility(View.GONE);
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

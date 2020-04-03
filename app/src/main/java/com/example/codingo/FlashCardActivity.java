package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;

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

    private Button card_button;
    private Button next_button;
    private TextView text;
    private TextView tvAnswer;
    private TextView tvInstructions;

    public FlashCardActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        list = new ArrayList<>();
        list.add(new Flashcard("1", "Do or not do, there is no try.", "Yoda"));
        list.add(new Flashcard("2", "If I have seen further than others, it is by " +
                "standing upon the shoulders of giants", "Newton"));
        current = list.get(index);

        card_button = findViewById(R.id.card_button);
        next_button = findViewById(R.id.next_button);
        text = findViewById(R.id.text);
        tvAnswer = findViewById(R.id.tvAnswer);
        tvInstructions = findViewById(R.id.tvInstructions);

        showFlashcard();

        card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnswer();
            }

        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextFlashcard();
            }

        });
    }

    protected void showFlashcard() {
        text.setText(current.getContent());
        text.setVisibility(View.VISIBLE);
        tvAnswer.setVisibility(View.GONE);
        tvInstructions.setVisibility(View.VISIBLE);
    }

    protected void showAnswer() {
        tvAnswer.setText(current.getAnswer());
        text.setVisibility(View.GONE);
        tvAnswer.setVisibility(View.VISIBLE);
        tvInstructions.setVisibility(View.GONE);
    }

    protected void nextFlashcard() {
        if(index < list.size() - 1){
            index++;
            current = list.get(index);
            showFlashcard();
        }
        else {
            tvInstructions.setText("You have reached the end! Good job!");
            text.setVisibility(View.GONE);
            tvAnswer.setVisibility(View.GONE);
            tvInstructions.setVisibility(View.VISIBLE);
            card_button.setOnClickListener(null);
            next_button.setOnClickListener(null);
            //Include a back button or something later on :)
        }
    }

}

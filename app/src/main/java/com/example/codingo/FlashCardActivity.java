package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FlashCardActivity extends AppCompatActivity {
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

        card_button = (Button) findViewById(R.id.card_button);
        next_button = (Button) findViewById(R.id.next_button);
        text = (TextView) findViewById(R.id.text);
        tvAnswer = (TextView) findViewById(R.id.tvAnswer);
        tvInstructions = (TextView) findViewById(R.id.tvInstructions);

        final ArrayList<String> questions=new ArrayList<String>();
        questions.add("Do or not do, there is no try");
        questions.add("If I have seen further than others, it is by standing upon the shoulders of giants");

        final ArrayList<String> answers=new ArrayList<String>();
        answers.add("Yoda");
        answers.add("Newton");

        text.setText(questions.get(0));

        card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setVisibility(View.INVISIBLE);
                tvInstructions.setVisibility(View.GONE);
                tvAnswer.setText(answers.get(0));
            }

        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAnswer.setVisibility(View.INVISIBLE);
                text.setVisibility(View.VISIBLE);
                text.setText(questions.get(1));
                tvInstructions.setVisibility(View.INVISIBLE);
            }

        });
    }
}

package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codingo.Model.Question;
import com.example.codingo.Utilities.BottomNavHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private String TAG = "codingo.system.message";
    private BottomNavigationView mNavigation;
    private TextView mQuestNo;
    private TextView mQuestion;
    private TextView mAnswerA;
    private TextView mAnswerB;
    private TextView mAnswerC;
    private TextView mAnswerD;
    private CardView mCardA;
    private CardView mCardB;
    private CardView mCardC;
    private CardView mCardD;
    private ImageView mResult;
    private Button mBtn;

    private ArrayList<Question> questions = new ArrayList<>();
    private Question current;
    private int qIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called from QuizActivity");
        setContentView(R.layout.activity_quiz);

        Log.d(TAG, "BottomNavigationBar rendering");
        BottomNavHelper bnh = new BottomNavHelper();
        mNavigation = bnh.getNavMenu(this, R.id.navigation);
        Log.d(TAG, "BottomNavigationBar rendered");

        //Linking up XML elements to Java variables
        mQuestNo = findViewById(R.id.tv_qno);
        mQuestion = findViewById(R.id.tv_question);
        mAnswerA = findViewById(R.id.tv_answer1);
        mAnswerB = findViewById(R.id.tv_answer2);
        mAnswerC = findViewById(R.id.tv_answer3);
        mAnswerD = findViewById(R.id.tv_answer4);
        mCardA = findViewById(R.id.cv_answer1);
        mCardB = findViewById(R.id.cv_answer2);
        mCardC = findViewById(R.id.cv_answer3);
        mCardD = findViewById(R.id.cv_answer4);
        mResult = findViewById(R.id.iv_result);
        mBtn = findViewById(R.id.btn);

        //Makes result icon and next button invisible to the ser
        mResult.setVisibility(View.GONE);
        mBtn.setVisibility(View.GONE);

        //Dummy data for testing
        ArrayList<String> answers = new ArrayList<>();
        answers.add("A");
        answers.add("B");
        answers.add("C");
        answers.add("D");
        questions.add(new Question("1", "What is love?", answers, 2));
        questions.add(new Question("2", "Spam", answers, 0));
        questions.add(new Question("3", "Eggs", answers, 1));
        questions.add(new Question("3", "Tomatoes", answers, 3));
        current = questions.get(qIndex);

        //rendering
        renderView();
    }

    protected void renderView() {
        mResult.setVisibility(View.GONE);
        mBtn.setVisibility(View.GONE);
        mCardA.setBackgroundResource(R.drawable.btn_rounded);
        mCardB.setBackgroundResource(R.drawable.btn_rounded);
        mCardC.setBackgroundResource(R.drawable.btn_rounded);
        mCardD.setBackgroundResource(R.drawable.btn_rounded);
        mQuestNo.setText("Question "+(qIndex+1)+" of "+questions.size());
        mQuestion.setText(current.getQuestion());
        mAnswerA.setText(current.getAnswers().get(0));
        mAnswerB.setText(current.getAnswers().get(1));
        mAnswerC.setText(current.getAnswers().get(2));
        mAnswerD.setText(current.getAnswers().get(3));
        setCardListener(mCardA);
        setCardListener(mCardB);
        setCardListener(mCardC);
        setCardListener(mCardD);
        setButtonListener();
    }

    protected void setCardListener(final CardView cardView) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correct;
                switch (cardView.getId()) {
                    case R.id.cv_answer1: correct = handleAnswer(0); break;
                    case R.id.cv_answer2: correct = handleAnswer(1); break;
                    case R.id.cv_answer3: correct = handleAnswer(2); break;
                    case R.id.cv_answer4: correct = handleAnswer(3); break;
                    default: correct = false; break;
                }

                if(correct) {
                    cardView.setBackgroundResource(R.drawable.card_correct);
                    mResult.setImageResource(R.drawable.correct);
                }
                else {
                    cardView.setBackgroundResource(R.drawable.card_incorrect);
                    switch(current.getCorrectPosition()) {
                        case 0: mCardA.setBackgroundResource(R.drawable.card_correct); break;
                        case 1: mCardB.setBackgroundResource(R.drawable.card_correct); break;
                        case 2: mCardC.setBackgroundResource(R.drawable.card_correct); break;
                        case 3: mCardD.setBackgroundResource(R.drawable.card_correct); break;
                    }
                    mResult.setImageResource(R.drawable.wrong);
                }

                mResult.setVisibility(View.VISIBLE);
                mBtn.setVisibility(View.VISIBLE);

                mCardA.setOnClickListener(null);
                mCardB.setOnClickListener(null);
                mCardC.setOnClickListener(null);
                mCardD.setOnClickListener(null);
            }
        });
    }

    protected boolean handleAnswer(int answer) {
        boolean result = false;
        if(answer == current.getCorrectPosition()) {
            return true;
        }
        return result;
    }

    protected void setButtonListener() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qIndex < questions.size()-1){
                    qIndex++;
                    current = questions.get(qIndex);
                    renderView();
                }
                else {
                    mBtn.setText("GO TO RESULTS");
                    //mBtn.setOnClickListener(null);
                    mBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            launchResultActivity();
                        }
                    });
                }
            }
        });
    }

    protected void launchResultActivity() {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
        finish();
    }

}

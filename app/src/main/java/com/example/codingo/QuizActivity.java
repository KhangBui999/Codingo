package com.example.codingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.codingo.Model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private String TAG = "com.example.codingo.QuizActivity";
    private TextView mQuestNo, mQuestion, mAnswerA, mAnswerB, mAnswerC, mAnswerD, mStatus;
    private CardView mCardA, mCardB, mCardC, mCardD;
    private ImageView mResult;
    private Button mBtn;
    private ProgressBar mProgress;

    private ArrayList<Question> questions = new ArrayList<>();
    private Question current;
    private int qIndex = 0;
    private int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called from QuizActivity");
        setContentView(R.layout.activity_quiz);

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
        mProgress = findViewById(R.id.progressBar2);
        mStatus = findViewById(R.id.tv_loading);

        //Makes result icon and next button invisible to the see
        mCardA.setVisibility(View.INVISIBLE);
        mCardB.setVisibility(View.INVISIBLE);
        mCardC.setVisibility(View.INVISIBLE);
        mCardD.setVisibility(View.INVISIBLE);
        mResult.setVisibility(View.INVISIBLE);
        mBtn.setVisibility(View.INVISIBLE);

        //Retrieves data from the Firebase Server
        retrieveQuestions(getIntent().getIntExtra("TOPIC_ID", 0));
    }

    protected void retrieveQuestions(int position) {
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);
        mStatus.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("questions")
                .whereEqualTo("topic_id", position)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> questionMap = document.getData();
                                String id = document.getId();
                                String question = questionMap.get("question").toString();
                                ArrayList<String> answers = (ArrayList<String>) questionMap.get("answers");
                                Long ucAnswerKey = (Long) questionMap.get("answer_key");
                                int answerKey = ucAnswerKey.intValue();
                                questions.add(new Question(id, question, answers, answerKey));
                            }
                            current = questions.get(qIndex);
                            mCardA.setVisibility(View.VISIBLE);
                            mCardB.setVisibility(View.VISIBLE);
                            mCardC.setVisibility(View.VISIBLE);
                            mCardD.setVisibility(View.VISIBLE);
                            mStatus.setVisibility(View.INVISIBLE);
                            renderView();
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            mStatus.setText("Connection failed - error getting questions.");
                        }
                        mProgress.setVisibility(View.INVISIBLE);
                    }
                });
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
                    points += 100;
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
        if(answer == current.getCorrectPosition()) {
            return true;
        } else {
            return false;
        }
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
        intent.putExtra("POINTS", points);
        startActivity(intent);
        finish();
    }

}

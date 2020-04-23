package com.example.codingo;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

/**
 * The QuizActivity class handles UI changes and logical calculation based on user interaction
 * with the Quiz module. Contains method to update UI to reflect correct and incorrect answers,
 * load the next Question and load the ResultActivity. Additional UI features include status
 * indicators for loading of content from the server and amount of points user has earned.
 */
public class QuizActivity extends AppCompatActivity {

    private String TAG = "com.example.codingo.QuizActivity";
    private TextView mQuestNo, mQuestion, mAnswerA, mAnswerB, mAnswerC, mAnswerD, mStatus, mScore;
    private CardView mCardA, mCardB, mCardC, mCardD;
    private ImageView mResult;
    private Button mBtn;
    private ProgressBar mProgress, mCompletion;

    private ArrayList<Question> questions = new ArrayList<>();
    private Question current;
    private int qIndex = 0;
    private int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called from QuizActivity");
        setContentView(R.layout.activity_quiz);

        /*Overrides the default back button action. Back button now launches a dialog that asks
        if user is sure they want to leave the activity. */
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                exitCheck();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        //Linking up XML elements to Java variables
        mQuestNo = findViewById(R.id.tv_qno);
        mQuestion = findViewById(R.id.tv_question);
        mAnswerA = findViewById(R.id.tv_answer1);
        mAnswerB = findViewById(R.id.tv_answer2);
        mAnswerC = findViewById(R.id.tv_answer3);
        mAnswerD = findViewById(R.id.tv_answer4);
        mScore = findViewById(R.id.tv_score);
        mCardA = findViewById(R.id.cv_answer1);
        mCardB = findViewById(R.id.cv_answer2);
        mCardC = findViewById(R.id.cv_answer3);
        mCardD = findViewById(R.id.cv_answer4);
        mResult = findViewById(R.id.iv_result);
        mBtn = findViewById(R.id.btn);
        mProgress = findViewById(R.id.progressBar2);
        mCompletion = findViewById(R.id.pb_question);
        mStatus = findViewById(R.id.tv_loading);

        //Makes result icon and next button invisible to the see
        mCompletion.setVisibility(View.INVISIBLE);
        mCardA.setVisibility(View.INVISIBLE);
        mCardB.setVisibility(View.INVISIBLE);
        mCardC.setVisibility(View.INVISIBLE);
        mCardD.setVisibility(View.INVISIBLE);
        mResult.setVisibility(View.INVISIBLE);
        mBtn.setVisibility(View.INVISIBLE);

        //Retrieves data from the Firebase Server
        retrieveQuestions(getIntent().getIntExtra("TOPIC_ID", 0));
    }

    /**
     * Launches a dialog that asks if a user is sure they want to leave the QuizActivity
     */
    private void exitCheck() {
        QuizExitDialogFragment dialog = new QuizExitDialogFragment();
        dialog.show(getSupportFragmentManager(), TAG);
    }

    /**
     * Retrieves question from the database
     * @param position is the topic_id from a previously selected topic fragment
     */
    protected void retrieveQuestions(int position) {
        //Progress UI - helps user understand content is loading
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);
        mStatus.setVisibility(View.VISIBLE);

        //Firebase connection code
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("questions")
                .whereEqualTo("topic_id", position)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            //Conversion of Firebase result into a question set
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
                            //UI changes after the successful loading of questions
                            current = questions.get(qIndex);
                            mCompletion.setMax(questions.size());
                            mCompletion.setVisibility(View.VISIBLE);
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

    /**
     * This method handles the rendering of the UI after each question is completed.
     */
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

    /**
     * Sets a CardView listener for the parameterised cardView element. This handles the UI change
     * of a CardView whether they are correct or incorrect. Also disables a user from gaming the
     * system and clicking the same CardView for duplicate points.
     *
     * @param cardView is one of the questions cardView
     */
    protected void setCardListener(final CardView cardView) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checks if current CardView contains the correct answer
                boolean correct;
                switch (cardView.getId()) {
                    case R.id.cv_answer1: correct = handleAnswer(0); break;
                    case R.id.cv_answer2: correct = handleAnswer(1); break;
                    case R.id.cv_answer3: correct = handleAnswer(2); break;
                    case R.id.cv_answer4: correct = handleAnswer(3); break;
                    default: correct = false; break;
                }

                if(correct) {
                    //If CardView contains correct answer update the UI to notify a user got the correct answer.
                    //sets green color for selected correct answer
                    cardView.setBackgroundResource(R.drawable.card_correct);
                    mResult.setImageResource(R.drawable.correct);
                    points += 200; //adds point
                    mScore.setText(Integer.toString(points)); //updates point UI
                }
                else {
                    cardView.setBackgroundResource(R.drawable.card_incorrect); //red for selected answer
                    //correct answer is shown in green to give user feedback
                    switch(current.getCorrectPosition()) {
                        case 0: mCardA.setBackgroundResource(R.drawable.card_correct); break;
                        case 1: mCardB.setBackgroundResource(R.drawable.card_correct); break;
                        case 2: mCardC.setBackgroundResource(R.drawable.card_correct); break;
                        case 3: mCardD.setBackgroundResource(R.drawable.card_correct); break;
                    }
                    mResult.setImageResource(R.drawable.wrong);
                }

                //Updates the question progression bar
                mCompletion.setProgress(qIndex+1, true);

                //Enables user to go to the next question after attempt
                mResult.setVisibility(View.VISIBLE);
                mBtn.setVisibility(View.VISIBLE);

                //Listeners are null to prevent point exploitation
                mCardA.setOnClickListener(null);
                mCardB.setOnClickListener(null);
                mCardC.setOnClickListener(null);
                mCardD.setOnClickListener(null);
            }
        });
    }

    /**
     * Checks if the CardView contains the correct answer
     * @param answer is the R id of the CardView containing the answer
     * @return true if answer is correct, false if answer is incorrect
     */
    protected boolean handleAnswer(int answer) {
        if(answer == current.getCorrectPosition()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Listener enables button to handle the loading of the next question and updating the UI.
     */
    protected void setButtonListener() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qIndex < questions.size()-1){
                    //If there is another question in the set, load the next question
                    qIndex++;
                    current = questions.get(qIndex);
                    renderView();
                }
                else {
                    //If there is no remaining questions, allow user to access ResultActivity
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

    /**
     * Launches the ResultActivity after user has completed the quiz
     */
    protected void launchResultActivity() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("POINTS", points);
        startActivity(intent);
        finish();
    }

}

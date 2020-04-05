package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "codingo.system.message";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate called from MainActivity");
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn_gs);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNextActivity();
            }
        });
        Log.i(TAG, "onCreate finished");
        Log.i(TAG, "MainActivity loaded");
    }

    private void launchNextActivity() {
        Log.i(TAG, "launchNextActivity called from MainActivity");
        Log.i(TAG, "Launching Activity");
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
        finish();
    }
}

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
        Log.d(TAG, "onCreate called from MainActivity");
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn_gs);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNextActivity();
            }
        });
        Log.d(TAG, "onCreate finished");
        Log.d(TAG, "MainActivity loaded");
    }

    private void launchNextActivity() {
        Log.d(TAG, "launchNextActivity called from MainActivity");
        Log.d(TAG, "Launching Activity");
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("VIDEO_ID", 0);
        startActivity(intent);
        finish();
        Log.d(TAG, "finish called from MainActivity");
    }
}

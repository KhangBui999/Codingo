package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String EXTRA_MESSAGE = "au.edu.unsw.infs3634.beers.MESSAGE";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate Called");
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btnLaunchActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDetailActivity("Hello from Codingo");
            }
        });
    }

    private void launchDetailActivity(String message) {
        Intent intent = new Intent(this, FlashCardActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }
}
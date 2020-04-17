package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.codingo.Utilities.BottomNavHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ResultActivity extends AppCompatActivity {

    private BottomNavigationView mNavigation;
    private TextView mPoints;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        int points = intent.getIntExtra("POINTS", 0);

        mPoints = findViewById(R.id.tv_points);
        mBtn = findViewById(R.id.btn_ok);

        mPoints.setText("You have earned "+points+" points!");

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

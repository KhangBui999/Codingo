/*
 * INFS3634 Group Assignment 2020 T1 - Team 31
 *
 * This is an Android mobile application that showcases the use of functional Android building blocks
 * and the implementation of other features such as Google Firebase and API calls. Submitted as part of
 * a group assignment for the course, INFS3634.
 *
 * Authors:
 * Shara Bakal, Khang Bui, Laurence Truong & Brian Vu
 *
 */

package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * The MainActivity is splash page that enables a user to either login or register.
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = "com.example.codingo.MainActivity";
    private Button mLogin;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called from MainActivity");
        setContentView(R.layout.activity_main);

        mLogin = findViewById(R.id.btn_gs);
        mRegister = findViewById(R.id.btn_register);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginActivity();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRegisterActivity();
            }
        });

        Log.d(TAG, "onCreate finished");
        Log.d(TAG, "MainActivity loaded");
    }

    private void launchLoginActivity() {
        Log.d(TAG, "launchLoginActivity called from MainActivity");
        Log.d(TAG, "Launching Login Activity");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void launchRegisterActivity() {
        Log.d(TAG, "launchRegisterActivity called from MainActivity");
        Log.d(TAG, "Launching Register Activity");
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}

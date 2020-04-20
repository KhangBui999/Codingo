package com.example.codingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private TextView mTitle;
    private EditText mDisplay;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirm;
    private Button mRegister;
    private TextView mExisting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTitle = findViewById(R.id.tv_login);
        mDisplay = findViewById(R.id.et_display);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mConfirm = findViewById(R.id.et_confirm);
        mRegister = findViewById(R.id.btn_register);
        mExisting = findViewById(R.id.tv_existing);

        mExisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginActivity();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displayName = mDisplay.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String confirmPass = mConfirm.getText().toString();
                boolean valid = true;

                //Check 1: Fields are not empty
                //The following if statements checks that every field has been filled
                if(displayName.isEmpty()) {
                    mDisplay.setError("Please enter a display name!");
                    valid = false;
                }
                if(email.isEmpty()) {
                    mEmail.setError("Please enter an e-mail!");
                    valid = false;
                }
                if(password.isEmpty()) {
                    mPassword.setError("Please enter a password!");
                    valid = false;
                }
                if(confirmPass.isEmpty()) {
                    mConfirm.setError("Please confirm your password!");
                    valid = false;
                }

                //Check 2: Check for not fulfilling length requirements
                //The next if statements check for additional requirements during registration
                if(valid) {
                    //Length check for display name (must be 16 or less characters)
                    if(displayName.length() < 4 || displayName.length() > 16) {
                        mDisplay.setError("Display name must be 4-16 characters!");
                        valid = false;
                    }
                    //Length check for password fields (must be 6 or more characters)
                    if(password.length() < 6) {
                        mPassword.setError("Passwords must be at least 6 characters!");
                        valid = false;
                    }
                    if(confirmPass.length() < 6) {
                        mConfirm.setError("Passwords must be at least 6 characters!");
                        valid = false;
                    }
                }

                //Check 3: Passwords match
                //The final check before an account creation process is invoked
                if(valid) {
                    if(!password.equals(confirmPass)) {
                        mPassword.setError("Passwords do not match!");
                        mConfirm.setError("Passwords do not match!");
                        valid = false;
                    }
                    //If the final check if passed it will activate the else statement
                    //The block of code will communicate with Firebase to create an account
                    else {

                    }
                }
            }
        });
    }

    protected void launchLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

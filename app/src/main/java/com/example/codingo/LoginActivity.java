package com.example.codingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "com.example.codingo.LoginActivity";
    private FirebaseAuth mAuth;
    private String userId;

    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private TextView mForgot;
    private TextView mRegister;
    private ProgressBar mProgress;
    private TextView mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mLogin = findViewById(R.id.btn_login);
        mForgot = findViewById(R.id.tv_forgot);
        mRegister = findViewById(R.id.tv_register);
        mProgress = findViewById(R.id.pb_login);
        mStatus = findViewById(R.id.tv_status);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameField = mUsername.getText().toString();
                String passwordField = mPassword.getText().toString();
                if(usernameField.isEmpty() && passwordField.isEmpty()) {
                    mUsername.setError("Please enter an e-mail!");
                    mPassword.setError("Please enter a password!");
                }
                else if(usernameField.isEmpty()) {
                    mUsername.setError("Please enter an e-mail!");
                    mUsername.requestFocus();
                }
                else if(passwordField.isEmpty()) {
                    mPassword.setError("Please enter a password!");
                    mPassword.requestFocus();
                }
                else{
                    attemptSignIn(usernameField, passwordField);
                }
            }
        });
    }

    protected void attemptSignIn(String email, String password) {
        try{
            mUsername.setVisibility(View.INVISIBLE);
            mPassword.setVisibility(View.INVISIBLE);
            mLogin.setVisibility(View.INVISIBLE);
            mForgot.setVisibility(View.INVISIBLE);
            mRegister.setVisibility(View.INVISIBLE);
            mStatus.setText("Authenticating User");
            mProgress.setIndeterminate(true);
            mStatus.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.VISIBLE);

            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                launchBaseActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed - incorrect details",
                                        Toast.LENGTH_SHORT).show();
                                mUsername.setVisibility(View.VISIBLE);
                                mPassword.setVisibility(View.VISIBLE);
                                mLogin.setVisibility(View.VISIBLE);
                                mForgot.setVisibility(View.VISIBLE);
                                mRegister.setVisibility(View.VISIBLE);
                                mProgress.setIndeterminate(false);
                                mProgress.setVisibility(View.INVISIBLE);
                                mStatus.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
        }
        catch(Exception e) {
            Log.d(TAG, "something went wrong");
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Authentication failed - connection issue",
                    Toast.LENGTH_SHORT).show();
            mUsername.setVisibility(View.VISIBLE);
            mPassword.setVisibility(View.VISIBLE);
            mLogin.setVisibility(View.VISIBLE);
            mForgot.setVisibility(View.VISIBLE);
            mRegister.setVisibility(View.VISIBLE);
            mProgress.setIndeterminate(false);
            mProgress.setVisibility(View.INVISIBLE);
            mStatus.setVisibility(View.INVISIBLE);
        }
    }

    protected void launchBaseActivity() {
        Intent intent = new Intent(this, BaseActivity.class);
        startActivity(intent);
        finish();
    }
}

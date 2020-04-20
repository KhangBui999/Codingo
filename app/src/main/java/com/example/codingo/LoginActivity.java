package com.example.codingo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.NetworkErrorException;
import android.content.Intent;
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
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "com.example.codingo.LoginActivity";
    private FirebaseAuth mAuth;

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
        mRegister = findViewById(R.id.tv_existing);
        mProgress = findViewById(R.id.pb_login);
        mStatus = findViewById(R.id.tv_status);

        mForgot.setVisibility(View.INVISIBLE);

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

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRegisterActivity();
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    protected void launchRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        bypassLogin(currentUser);
    }

    protected void attemptSignIn(String email, String password) {
        try {
            mUsername.setVisibility(View.INVISIBLE);
            mPassword.setVisibility(View.INVISIBLE);
            mLogin.setVisibility(View.INVISIBLE);
            //mForgot.setVisibility(View.INVISIBLE);
            mRegister.setVisibility(View.INVISIBLE);
            mStatus.setText("Authenticating User");
            mProgress.setIndeterminate(true);
            mStatus.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.VISIBLE);

            Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email, password)
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
                                reloadUI();
                            }
                        }
                    });
            Tasks.await(task, 10, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG, "InterruptedException thrown");
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Authentication failed - interrupted error",
                    Toast.LENGTH_SHORT).show();
            reloadUI();
        }
        catch (ExecutionException e) {
            Log.d(TAG, "ExecutionException thrown");
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Authentication failed - execution error",
                    Toast.LENGTH_SHORT).show();
            reloadUI();
        }
        catch (TimeoutException e) {
            Log.d(TAG, "TimeoutException thrown");
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Authentication failed - timeout error",
                    Toast.LENGTH_SHORT).show();
            reloadUI();
        }
        catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException thrown");
            e.printStackTrace();
        }
    }

    protected void launchBaseActivity() {
        Intent intent = new Intent(this, BaseActivity.class);
        startActivity(intent);
        finish();
    }

    protected void reloadUI() {
        mUsername.setVisibility(View.VISIBLE);
        mPassword.setVisibility(View.VISIBLE);
        mLogin.setVisibility(View.VISIBLE);
        //mForgot.setVisibility(View.VISIBLE);
        mRegister.setVisibility(View.VISIBLE);
        mProgress.setIndeterminate(false);
        mProgress.setVisibility(View.INVISIBLE);
        mStatus.setVisibility(View.INVISIBLE);
    }

    protected void bypassLogin(FirebaseUser user) {
        if(user != null) {
            Log.d(TAG, "A logged in user has been detected. Bypassing login.");
            launchBaseActivity();
        }
        else {
            Log.d(TAG, "No current user detected. Authentication form must be filled.");
        }
    }
}

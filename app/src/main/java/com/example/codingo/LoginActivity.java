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

/**
 * The LoginActivity is a controller class that handles user input in the login form.
 * It's main functions is to detect errors and run error handling code, execute db transactions with
 * Firebase Firestore and any subsequent side effects of these code e.g. UI changes or launching
 * BaseActivity (if login was successgul).
 */
public class LoginActivity extends AppCompatActivity {


    private final String TAG = "com.example.codingo.LoginActivity";
    private FirebaseAuth mAuth;

    private EditText mUsername, mPassword;
    private Button mLogin;
    private TextView mForgot, mRegister, mStatus;
    private ProgressBar mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Linking the XML elements
        mUsername = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mLogin = findViewById(R.id.btn_login);
        mForgot = findViewById(R.id.tv_forgot);
        mRegister = findViewById(R.id.tv_existing);
        mProgress = findViewById(R.id.pb_login);
        mStatus = findViewById(R.id.tv_status);

        //Disabling the access of a planned feature (forget password)
        mForgot.setVisibility(View.INVISIBLE);

        //Handles when a user clicks the Login Button
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameField = mUsername.getText().toString();
                String passwordField = mPassword.getText().toString();

                //If and else statements check if the user has correctly entered login details
                if(usernameField.isEmpty() && passwordField.isEmpty()) {
                    //Empty fields for both username and password
                    mUsername.setError("Please enter an e-mail!");
                    mPassword.setError("Please enter a password!");
                }
                else if(usernameField.isEmpty()) {
                    //Empty fields only for username
                    mUsername.setError("Please enter an e-mail!");
                    mUsername.requestFocus();
                }
                else if(passwordField.isEmpty()) {
                    //Empty fields only for password
                    mPassword.setError("Please enter a password!");
                    mPassword.requestFocus();
                }
                else{
                    //Start login authentication
                    attemptSignIn(usernameField, passwordField);
                }
            }
        });

        //Redirects user to the RegisterActivity screen
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRegisterActivity();
            }
        });

        //Initialising the Firebase Authentication connection
        mAuth = FirebaseAuth.getInstance();
    }


    /**
     * Launches the RegisterActivity when a user doesn't have an account.
     */
    protected void launchRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * Executed after the onCreate method
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        bypassLogin(currentUser);
    }


    /**
     * attemptSignIn method is used to authenticate the user and handle UI changes
     * @param email
     * @param password
     */
    protected void attemptSignIn(String email, String password) {
        try {
            //Authentication progress UI
            mUsername.setVisibility(View.INVISIBLE);
            mPassword.setVisibility(View.INVISIBLE);
            mLogin.setVisibility(View.INVISIBLE);
            //mForgot.setVisibility(View.INVISIBLE);
            mRegister.setVisibility(View.INVISIBLE);
            mStatus.setText("Authenticating User");
            mProgress.setIndeterminate(true);
            mStatus.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.VISIBLE);

            //Authenticates the user
            Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, redirect to BaseActivity
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
            //Ensures a timeout in the event of a network issue
            Tasks.await(task, 10, TimeUnit.SECONDS);
        }
        //The following catch methods handles network problems
        catch (InterruptedException e) {
            //Handles any network interruption between client and server
            Log.d(TAG, "InterruptedException thrown");
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Authentication failed - interrupted error",
                    Toast.LENGTH_SHORT).show();
            reloadUI();
        }
        catch (ExecutionException e) {
            //Execution error due to unknown network error
            Log.d(TAG, "ExecutionException thrown");
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Authentication failed - execution error",
                    Toast.LENGTH_SHORT).show();
            reloadUI();
        }
        catch (TimeoutException e) {
            //Handles any timeouts that occur
            Log.d(TAG, "TimeoutException thrown");
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Authentication failed - timeout error",
                    Toast.LENGTH_SHORT).show();
            reloadUI();
        }
        catch (IllegalStateException e) {
            //Handles any inappropriate input
            Log.d(TAG, "IllegalStateException thrown");
            e.printStackTrace();
        }
    }


    /**
     * Launches the BaseActivity in the event of a successful login
     */
    protected void launchBaseActivity() {
        Intent intent = new Intent(this, BaseActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * Renders the UI to enable user to input login details
     */
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


    /**
     * Allows user to log in instantly if they have are still logged in on their phones.
     * @param user is the current Firebase user account.
     */
    protected void bypassLogin(FirebaseUser user) {
        if(user != null) {
            Log.d(TAG, "Logged in user:"+user.getUid()+". Bypassing login.");
            launchBaseActivity();
        }
        else {
            Log.d(TAG, "No current user detected. Authentication form must be filled.");
        }
    }
}

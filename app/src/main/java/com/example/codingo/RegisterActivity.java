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
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * The RegisterActivity is a controller class that handles user input in the login form.
 * It's main functions is to detect errors and run error handling code, execute db transactions with
 * Firebase Firestore and any subsequent side effects of these code e.g. UI changes or launching
 * BaseActivity (if registration was successful).
 * Additional functions include making a new data document for the new user in Firebase Firestore.
 */
public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "com.example.codingo.LoginActivity";
    private FirebaseAuth mAuth;

    private EditText mDisplay, mEmail, mPassword, mConfirm;
    private Button mRegister;
    private TextView mExisting;
    private ProgressBar mProgress;
    private TextView mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDisplay = findViewById(R.id.et_display);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mConfirm = findViewById(R.id.et_confirm);
        mRegister = findViewById(R.id.btn_register);
        mExisting = findViewById(R.id.tv_existing);
        mProgress = findViewById(R.id.pb_register);
        mStatus = findViewById(R.id.tv_status);

        mExisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginActivity();
            }
        });

        //When registration button is clicked
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
                        registerNewUser(email, password, displayName);
                    }
                }
            }
        });

        //Establishing connection to Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null) {
            //signs current user out to prevent any account conflicts
            FirebaseAuth.getInstance().signOut();
        }
    }

    /**
     * Handles the registration of a new user
     * @param email is the inputted email
     * @param password is the inputted password
     * @param displayName is the inputted display name
     */
    protected void registerNewUser(String email, String password, String displayName) {
        try {
            //Progress UI rendered
            mDisplay.setVisibility(View.INVISIBLE);
            mEmail.setVisibility(View.INVISIBLE);
            mPassword.setVisibility(View.INVISIBLE);
            mConfirm.setVisibility(View.INVISIBLE);
            mRegister.setVisibility(View.INVISIBLE);
            mExisting.setVisibility(View.INVISIBLE);
            mProgress.setIndeterminate(true);
            mStatus.setText("Creating Account");
            mStatus.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.VISIBLE);

            //Initiates the registration process
            Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign up success, set Display Name and redirect to BaseActivity
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(displayName)
                                        .setPhotoUri(Uri.parse("https://robohash.org/"+mAuth.getUid()+".png"))
                                        .build();
                                task.getResult().getUser().updateProfile(profileUpdates);
                                setUpUserDatabaseFile(displayName, "https://robohash.org/"+mAuth.getUid()+".png");
                            } else {
                                // If sign up fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Account creation failed - client issue",
                                        Toast.LENGTH_SHORT).show();
                                reloadUI();
                            }
                        }
                    });
            Tasks.await(task, 10, TimeUnit.SECONDS); //ensures a time limit for network errors
        }
        //The following catch codes handle network or Firebase Authentication issues
        catch (InterruptedException e) {
            Log.d(TAG, "InterruptedException thrown");
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "Account creation failed - interrupted error",
                    Toast.LENGTH_SHORT).show();
            reloadUI();
        }
        catch (ExecutionException e) {
            Log.d(TAG, "ExecutionException thrown");
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "Account creation failed - execution error",
                    Toast.LENGTH_SHORT).show();
            reloadUI();
        }
        catch (TimeoutException e) {
            Log.d(TAG, "TimeoutException thrown");
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "Account creation failed - timeout error",
                    Toast.LENGTH_SHORT).show();
            reloadUI();
        }
        catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException thrown");
            e.printStackTrace();
        }
    }

    /**
     * Sets up database information on the newly registered for the Firestore db
     */
    private void setUpUserDatabaseFile(String name, String url) {
        String userUID = mAuth.getCurrentUser().getUid();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("badges", new ArrayList<>());
        userInfo.put("xp", Integer.valueOf(0));
        userInfo.put("points", Integer.valueOf(0));
        userInfo.put("attempt", Integer.valueOf(0));
        userInfo.put("correct", Integer.valueOf(0));
        userInfo.put("name", name);
        userInfo.put("image", url);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userUID)
                .set(userInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        launchBaseActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        Log.d(TAG, "Deleting user account from auth service");
                        mAuth.getCurrentUser().delete();
                        Toast.makeText(RegisterActivity.this, "Account creation failed - network error",
                                Toast.LENGTH_SHORT).show();
                        reloadUI();
                    }
                });

    }

    /**
     * Renders UI to enable user to input rego details
     */
    private void reloadUI() {
        mDisplay.setVisibility(View.VISIBLE);
        mEmail.setVisibility(View.VISIBLE);
        mPassword.setVisibility(View.VISIBLE);
        mConfirm.setVisibility(View.VISIBLE);
        mRegister.setVisibility(View.VISIBLE);
        mExisting.setVisibility(View.VISIBLE);
        mStatus.setVisibility(View.INVISIBLE);
        mProgress.setVisibility(View.INVISIBLE);
    }

    /**
     * Launches LoginActivity if user has an existing account
     */
    protected void launchLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Launches BaseActivity in the event of a successful registration event
     */
    private void launchBaseActivity() {
        Intent intent = new Intent(this, BaseActivity.class);
        startActivity(intent);
    }
}

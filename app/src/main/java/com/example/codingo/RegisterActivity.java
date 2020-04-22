package com.example.codingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

        //TODO: Add Progress Bar and Status Loading Message
        mDisplay = findViewById(R.id.et_display);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mConfirm = findViewById(R.id.et_confirm);
        mRegister = findViewById(R.id.btn_register);
        mExisting = findViewById(R.id.tv_existing);

        //TODO: Readability and documentation
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
                                        .setDisplayName(displayName).build();
                                setUpUserDatabaseFile();
                                launchBaseActivity();
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
    private void setUpUserDatabaseFile() {
        //TODO: complete this method
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

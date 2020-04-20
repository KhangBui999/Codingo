package com.example.codingo;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.codingo.Model.Content;
import com.example.codingo.Model.Topic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseActivity extends AppCompatActivity {

    private final String TAG = "com.example.codingo.BaseActivity";
    protected NavController navController;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Topic> contentTopic = new ArrayList<>();
    private List<Content> contentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        loadUserInformation(currentUser);
        getContentData();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(ContextCompat.getColorStateList(this, R.color.nav_menu_selector));
        navView.setItemTextColor(ContextCompat.getColorStateList(this, R.color.nav_menu_selector));

        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_quiz, R.id.navigation_learn, R.id.navigation_quiz_start,
                R.id.navigation_flashcard, R.id.navigation_learn_topic, R.id.navigation_video_learn)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        mAuth = FirebaseAuth.getInstance();
    }

    protected void loadUserInformation(FirebaseUser user) {
        if(user != null) {
            Log.d(TAG, "A logged in user has been detected.");
        }
        else {
            Log.d(TAG, "No current user detected.");
        }
    }

    public void getContentData() {
        db.collection("content")
                .orderBy("position", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> topicMap = document.getData();
                                String id = document.getId();
                                String topic = topicMap.get("topic").toString();
                                String content = topicMap.get("content_body").toString();
                                String video = topicMap.get("video_id").toString();
                                contentTopic.add(new Topic(id, topic, true));
                                contentList.add(new Content(topic, content, video));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public List<Topic> getContentTopic() {
        return contentTopic;
    }

    public List<Content> getContentList() {
        return contentList;
    }

    public NavController getNavController() {
        return navController;
    }
}

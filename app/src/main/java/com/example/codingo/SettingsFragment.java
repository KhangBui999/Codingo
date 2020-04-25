package com.example.codingo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.codingo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private final String TAG = "com.example.codingo.SettingsFragment";
    private int secretCount = 0;
    private Button mSecret;
    private Button mLogout;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        mSecret = root.findViewById(R.id.btn_secret);
        mSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSecretAction();
            }
        });
        mLogout = root.findViewById(R.id.btn_logout);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutCurrentUser();
            }
        });
        return root;
    }

    protected void launchSecretAction() {
        if (secretCount < 9) {
            secretCount++;
            Toast.makeText(getContext(), "Click "+(10-secretCount)+" more times!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            startActivity(intent);
            secretCount = 0;
        }
    }

    private void logoutCurrentUser() {
        LogoutFragmentDialog dialog = new LogoutFragmentDialog();
        dialog.show(getActivity().getSupportFragmentManager(), TAG);
    }
}

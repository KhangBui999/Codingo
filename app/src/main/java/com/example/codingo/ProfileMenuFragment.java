package com.example.codingo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codingo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileMenuFragment extends Fragment {

    public ProfileMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_menu, container, false);
    }
}

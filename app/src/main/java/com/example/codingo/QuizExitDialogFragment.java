package com.example.codingo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * Controller class for a dialog popup that occurs when user attempts to leave a quiz they
 * are completing. Ensures user did not make the mistake of leaving.
 */
public class QuizExitDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Building the dialog UI
        builder.setMessage("Your progress will not be saved.\nYou will lose all points earned so far." +
                "\n\nAre you sure you want to leave this quiz?")
                .setTitle("Quiz Progress Not Saved")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog -- no action to be taken
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

package ca.cmpt276.as3.activityUI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import ca.cmpt276.as3.model.R;

/**
 * Message activity class models an AlertDialog for a game win
 * It supports creating the AlertDialog tto finish activity.
 */
public class MessageWon extends AppCompatDialogFragment {

    // Used course tutorial at: https://www.youtube.com/watch?v=y6StJRn-Y-A&feature=youtu.be
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create the view
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.won_message, null);

        // Create a listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        };

        // Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Victory")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}

package com.example.bennettmitchellassignment5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class SettingsMenu extends DialogFragment {
    private Dice dice;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // create a builder for this activity
        // inflate to show the dialog
        LayoutInflater inflator = getActivity().getLayoutInflater();
        // pass the layout to display
        View dialogView = inflator.inflate(R.layout.settings, null);

        // put the items in here
        // not very different except it has to go through the dialogView
        CheckBox doubleBox = dialogView.findViewById(R.id.doubleBox);
        CheckBox tripleBox = dialogView.findViewById(R.id.tripleBox);
        // set the checkboxes to be correct
        doubleBox.setChecked(dice.getDoubleMult());
        tripleBox.setChecked(dice.getTripleMult());

        Button exitButton = dialogView.findViewById(R.id.cancelButton);
        Button saveButton = dialogView.findViewById(R.id.saveButton);

        // get the activity that rang
        MainActivity callActivity = (MainActivity) getActivity();

        exitButton.setOnClickListener((View v) -> dismiss());// dismiss makes it leave
        saveButton.setOnClickListener((View v) -> {
            // hi
            // apply the changes
            dice.setDoubleMult(doubleBox.isChecked());
            dice.setTripleMult(tripleBox.isChecked());
            if (callActivity != null){callActivity.updateEnhancers();}
            dismiss(); //bye
        });



        // set the view to dialogView
        builder.setView(dialogView);
        // change the title message
        builder.setMessage("Settings");
        // return the Dialog
        return builder.create();
    }

    // recieve dice from main activity
    public void sendDice(Dice dice){
        this.dice = dice;
    }


}

package ca.cmpt276.as3.activityUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import ca.cmpt276.as3.model.R;

/**
 * Options activity class models the information about a
 * preferences of a game. Data includes the board size
 * and imposters count selections. It supports saving the
 * preferences via SharedPreferences
 */
public class OptionsActivity extends AppCompatActivity {

    // Launch Intent
    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, OptionsActivity.class);        // Start activity
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        boardSpinnerSetup();        // Setup for spinner of the board size
        imposterSpinnerSetup();     // Setup for Spinner of the imposter count
        playedTimesSetup();         // Setup for the textView of games played
    }

    private void playedTimesSetup() {
        TextView timesPlayed = findViewById(R.id.countPlays);
        timesPlayed.setText(GamePlay.getPlayedTimes(this));
    }

    private void imposterSpinnerSetup() {
        Spinner impSpinner = findViewById(R.id.imspoterSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,                   // Context
                R.array.imposterSelection,      // get array from strings.xml
                R.layout.spinner_custom);       // custom layout for spinner
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout_custom);
        impSpinner.setAdapter(adapter);
        impSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getItemAtPosition(position).toString();
                // Save selected item
                savedImposterSelection(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] imposters = this.getResources().getStringArray(R.array.imposterSelection);

        for (int position = 0; position < imposters.length; position++) {
            if (imposters[position].equals(getImposterSelection(this))) {
                impSpinner.setSelection(position);
                return;
            }
        }
    }

    private void boardSpinnerSetup() {
        Spinner boardSpinner = findViewById(R.id.boardSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,               // Context
                R.array.boardSizes,         // The array found in the strings.xml
                R.layout.spinner_custom);   // Custom layout UI
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout_custom);
        boardSpinner.setAdapter(adapter);
        boardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getItemAtPosition(position).toString();
                // Save the selected item
                savedBoardSelection(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] boardSizes = this.getResources().getStringArray(R.array.boardSizes);

        for (int position = 0; position < boardSizes.length; position++) {
            if (boardSizes[position].equals(getBoardSelection(this))) {
                boardSpinner.setSelection(position);
                return;
            }
        }
    }

    // Used the course tutorial on: https://www.youtube.com/watch?v=m_ZiP0U_zRA&feature=youtu.be
    private void savedBoardSelection(String selection) {
        SharedPreferences preferences = this.getSharedPreferences("Board", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Size", selection);
        editor.apply();
    }

    static public String getBoardSelection(Context context) {
        // First run default setting
        String defaultString = context.getResources().getString(R.string.default_board_size);
        SharedPreferences preferences = context.getSharedPreferences("Board", MODE_PRIVATE);
        return preferences.getString("Size", defaultString);
    }

    // Used the course tutorial on: https://www.youtube.com/watch?v=m_ZiP0U_zRA&feature=youtu.be
    private void savedImposterSelection(String selection) {
        SharedPreferences preferences = this.getSharedPreferences("Imposter", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Count", selection);
        editor.apply();
    }

    static public String getImposterSelection(Context context) {
        // Initial default count of imposters
        String defaultString = context.getResources().getString(R.string.default_imposter_count);
        SharedPreferences preferences = context.getSharedPreferences("Imposter", MODE_PRIVATE);
        return preferences.getString("Count", defaultString);
    }

}
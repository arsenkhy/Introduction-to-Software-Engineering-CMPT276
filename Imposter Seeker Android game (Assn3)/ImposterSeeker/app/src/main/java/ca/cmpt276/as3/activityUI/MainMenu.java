package ca.cmpt276.as3.activityUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ca.cmpt276.as3.model.GameSettings;
import ca.cmpt276.as3.model.R;

public class MainMenu extends AppCompatActivity {

    GameSettings settings;

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, MainMenu.class);        // Start activity
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        settings = GameSettings.getInstance();

        setupStartGameButton();
        setupOptionsButton();
        setupHelpButton();

        setTheSettings();
    }

    private void setupHelpButton() {
        Button helpButton = findViewById(R.id.helpButton);

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = HelpActivity.makeLaunchIntent(MainMenu.this);
                startActivity(intent);
            }
        });
    }

    private void setupOptionsButton() {
        Button optionsButton = findViewById(R.id.optionsButton);

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OptionsActivity.makeLaunchIntent(MainMenu.this);
                startActivity(intent);
            }
        });
    }

    private void setupStartGameButton() {
        Button gameButton = findViewById(R.id.startGameButton);

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GamePlay.makeLaunchIntent(MainMenu.this);
                startActivity(intent);
            }
        });
    }

    private void setTheSettings() {
        String savedBoardCount = OptionsActivity.getBoardSelection(this);
        int rows = Integer.parseInt(String.valueOf(savedBoardCount.charAt(0)));
        int columns = Integer.parseInt(savedBoardCount.substring(
                savedBoardCount.length() - 2).trim());
        settings.setRows(rows);
        settings.setColumns(columns);


        String savedImposterCount = OptionsActivity.getImposterSelection(this);
        int imposters = Integer.parseInt(savedImposterCount);
        settings.setImpostersCount(imposters);

        String savedTimesPlayed = GamePlay.getPlayedTimes(this);
        int timesPlayed = Integer.parseInt(savedTimesPlayed);
        settings.setPlaysCounter(timesPlayed);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTheSettings();
    }

}
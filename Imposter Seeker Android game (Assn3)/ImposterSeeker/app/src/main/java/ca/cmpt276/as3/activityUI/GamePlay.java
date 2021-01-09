package ca.cmpt276.as3.activityUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import ca.cmpt276.as3.model.ButtonCell;
import ca.cmpt276.as3.model.GameLogic;
import ca.cmpt276.as3.model.GameSettings;
import ca.cmpt276.as3.model.R;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Color.WHITE;

/**
 * GamePlay activity class models the information about a
 * game running. Data includes the GameSettings, GameLogic and
 * buttons. It supports creating the board from user preferences
 * and simulating the game.
 */
public class GamePlay extends AppCompatActivity {
    GameSettings settings = GameSettings.getInstance();         // get Singleton
    private final int NUM_ROWS = settings.getRows();
    private final int NUM_COLUMNS = settings.getColumns();
    private final int NUM_IMPOSTERS = settings.getImpostersCount();
    private GameLogic gameLogic;
    Button buttons[][] = new Button[NUM_ROWS][NUM_COLUMNS];

    
    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, GamePlay.class);        // Start activity
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // Generates the board with imposters and scores
        setupGameLogic();

        // Creates the view of the buttons
        populateButtons();

        // Update the count of scans and imposters left
        updateScoreUI();
    }

    private void setupGameLogic() {
        ArrayList<ButtonCell> buttonCells = new ArrayList<>();
        // New game with provided options
        gameLogic = new GameLogic(buttonCells, NUM_ROWS, NUM_COLUMNS, NUM_IMPOSTERS);
        // Creates a board with a random imposters locations
        gameLogic.populateCells();
    }

    // Used course tutorial at: https://www.youtube.com/watch?v=4MFzuP1F-xQ
    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableForButtons);

        for (int row = 0; row < NUM_ROWS; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for (int column = 0; column < NUM_COLUMNS; column++) {
                final int FINAL_ROW = row;
                final int FINAL_COLUMN = column;

                final Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                button.setPadding(0,0,0,0);

                tableRow.addView(button);
                buttons[row][column] = button;
                buttons[row][column].setTextColor(TRANSPARENT);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonsClicked(FINAL_ROW, FINAL_COLUMN, button);
                    }
                });

            }
        }
    }

    private void buttonsClicked(int FINAL_ROW, int FINAL_COLUMN, Button button) {
        ButtonCell buttonCell = gameLogic.retrieveCell(FINAL_ROW, FINAL_COLUMN);
        if (buttonCell.isImposter()) {
            buttonCell.setRevealed(true);
            updateButtonsUI();
            gameLogic.decrementItems();         // decrement number of impostors
            gridButtonClicked(FINAL_ROW, FINAL_COLUMN);
        } else {
            // Not found imposter
            button.setTextColor(BLACK);
            updateButtonsUI();
            button.setEnabled(false);
            gameLogic.incrementScans();
        }
        updateScoreUI();
    }

    // Used course tutorial at: https://www.youtube.com/watch?v=4MFzuP1F-xQ
    private void gridButtonClicked(int row, int column) {
        final Button button = buttons[row][column];

        // Lock button sizes
        lockButtons();

        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sus);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Double tap on revealed cell
                button.setTextColor(WHITE);
                button.setEnabled(false);
                gameLogic.incrementScans();
                updateButtonsUI();
                updateScoreUI();
            }
        });
    }

    // Used course tutorial at: https://www.youtube.com/watch?v=4MFzuP1F-xQ
    private void lockButtons() {
        // Lock buttons to not make images distorted
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int column = 0; column < NUM_COLUMNS; column++) {
                Button button = buttons[row][column];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setHeight(height);
                button.setHeight(height);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateScoreUI() {
        TextView itemsLeftScore = findViewById(R.id.imposterCount);
        TextView scansScore = findViewById(R.id.scansNumber);
        TextView plays = findViewById(R.id.countPlays);

        itemsLeftScore.setText(gameLogic.getItemsLeft() + "");
        scansScore.setText(gameLogic.getScans() + "");
        plays.setText(getPlayedTimes(this));

        // The user won, print dialog
        if (gameLogic.getItemsLeft() == 0) {
            FragmentManager manager = getSupportFragmentManager();
            MessageWon dialog = new MessageWon();
            dialog.show(manager, "MessageDialog");
            settings.setPlaysCounter(settings.getPlaysCounter() + 1);       // Increment the times played
            savedPlayedTimesText(settings.getPlaysCounter() + "");
        }
    }

    private void savedPlayedTimesText(String count) {
        // Save the times played
        SharedPreferences preferences = this.getSharedPreferences("Played", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Times", count);
        editor.apply();
    }

    static public String getPlayedTimes(Context context) {

        // Default times played
        String defaultString = context.getResources().getString(R.string.default_times_played);
        SharedPreferences preferences = context.getSharedPreferences("Played", MODE_PRIVATE);
        return preferences.getString("Times", defaultString);
    }

    @SuppressLint("SetTextI18n")
    private void updateButtonsUI() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int column = 0; column < NUM_COLUMNS; column++) {
                ButtonCell buttonCell = gameLogic.retrieveCell(row, column);    // Get the cell at the position
                int number = gameLogic.numberForCell(buttonCell);               // Calculate the number for a cell
                buttons[row][column].setText("" + number);
            }
        }
    }

}
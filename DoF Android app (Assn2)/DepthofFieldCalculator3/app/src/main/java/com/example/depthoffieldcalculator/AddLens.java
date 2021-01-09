package com.example.depthoffieldcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.depthoffieldcalculator.model.Lens;
import com.example.depthoffieldcalculator.model.LensManager;

/**
 * AddLens activity models the adding lens to the LensManager.
 * Data includes editTexts of user inputs, radioGroup to select icons
 * going back toolbar button and toolbar save button which is enabled
 * if everything is entered. The activity supports adding the lens
 * to the LensManager checking the inputs.
 */
public class AddLens extends AppCompatActivity {

    private LensManager manager;

    private EditText editTextMake;              // Make input
    private EditText editTextFocalLength;       // Focal length input
    private EditText editTextNewAperture;       // Aperture input

    private Menu optionsMenu;           // Menu toolbar

    private ImageView imageViewIcons;   // Image view to see options
    private int iconID;                 // The ID of selected image

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, AddLens.class);        // Start activity
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lens);
        setTitle("Add the Lens");           // Title

        // Getting instance to use one object across all activities
        manager = LensManager.getInstance();

        // Setup the user inputs so that every entry should filled
        setupEditTextInput();

        // Setup chosen Lens icon
        setupRadioGroup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Get the menu
         getMenuInflater().inflate(R.menu.save_menu, menu);
         optionsMenu = menu;
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Checking which menu toolbar item was clicked
        switch (item.getItemId()) {
            case R.id.saveButton:       // The save button
                // Record and convert user input to right value
                String lensMake = editTextMake.getText().toString();
                int focalLength = Integer.parseInt(editTextFocalLength.getText().toString());
                double newAperture = Double.parseDouble(editTextNewAperture.getText().toString());

                // Input error checking to prevent storing invalid lens
                boolean invalidMake = lensMake.trim().isEmpty();
                boolean invalidFocalLength = focalLength <= 0;
                boolean invalidAperture = newAperture < 1.4;

                // Printing prompts what errors were found
                if (invalidMake) {
                    editTextMake.setError("Please enter name");
                }
                if (invalidFocalLength) {
                    editTextFocalLength.setError("Focal length cannot be 0");
                }
                if (invalidAperture) {
                    editTextNewAperture.setError("Aperture cannot be smaller F1.4");
                }

                // Save the lens if everything is fine
                if (!invalidMake && !invalidFocalLength && !invalidAperture) {
                    // Put extras to pass inputs to main List view activity
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("newLens", lensMake);             // Return lens name input
                    returnIntent.putExtra("focalLength", focalLength);      // Return focal length input
                    returnIntent.putExtra("aperture", newAperture);         // Return aperture input
                    returnIntent.putExtra("icon", iconID);                  // Return icon selected
                    setResult(RESULT_OK, returnIntent);
                    finish();               // Close activity
                }
                break;
            case R.id.backButton:           // The back button
                finish();                   // Close activity
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupEditTextInput() {
        // Initialize the editText fields to work with
        editTextMake = findViewById(R.id.newLensName);
        editTextFocalLength = findViewById(R.id.newLensLength);
        editTextNewAperture = findViewById(R.id.new_aperture_input);

        // Watching for changed text
        editTextMake.addTextChangedListener(userInput);
        editTextFocalLength.addTextChangedListener(userInput);
        editTextNewAperture.addTextChangedListener(userInput);
    }

    private TextWatcher userInput = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Enable button only when all fields are not empty
            MenuItem item = optionsMenu.findItem(R.id.saveButton);
            item.setEnabled(
                            !(editTextMake.getText().toString().trim().isEmpty()) &&                // Name is not empty
                            !(editTextFocalLength.getText().toString().trim().isEmpty()) &&         // Focal length is not empty
                            !(editTextNewAperture.getText().toString().trim().isEmpty())            // Aperture is not empty
            );
            hideKeyboard();                 // Hides keyboard after pressing enter on the last field
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void hideKeyboard() {
        View view = this.getCurrentFocus();                     // Create current view
        // Make the listener of Enter key
        editTextNewAperture.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Checks if the Enter pressing key
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (view != null) {
                        InputMethodManager methodManager =
                                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                        // Hiding the soft keyboard to end entering inputs
                        methodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return true;            // The Enter was pressed
                }
                return false;
            }
        });
    }

    // *Images are retrieved from: https://www.cleanpng.com/free/lens.html
    private void setupRadioGroup() {
        RadioGroup radioGroupLens = findViewById(R.id.radioGroup);
        imageViewIcons = findViewById(R.id.chose_lens_icon);
        iconID = R.drawable.canon;          // Initial lens selection

        // Add listener to display the image preview
        radioGroupLens.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radioButton_1:
                        imageViewIcons.setImageDrawable(getResources().getDrawable(R.drawable.canon));      // Set image to show
                        iconID = R.drawable.canon;                                                          // Record selected lens image icon
                        break;
                    case R.id.radioButton_2:
                        imageViewIcons.setImageDrawable(getResources().getDrawable(R.drawable.cannon));     // Set image to show
                        iconID = R.drawable.cannon;                                                         // Record selected lens image icon
                        break;
                    case R.id.radioButton_3:
                        imageViewIcons.setImageDrawable(getResources().getDrawable(R.drawable.nikon));      // Set image to show
                        iconID = R.drawable.nikon;                                                          // Record selected lens image icon
                        break;
                    case R.id.radioButton_4:
                        imageViewIcons.setImageDrawable(getResources().getDrawable(R.drawable.sigma));      // Set image to show
                        iconID = R.drawable.sigma;                                                          // Record selected lens image icon
                        break;
                    case R.id.radioButton_5:
                        imageViewIcons.setImageDrawable(getResources().getDrawable(R.drawable.tamron));     // Set image to show
                        iconID = R.drawable.tamron;                                                         // Record selected lens image icon
                        break;
                }
            }
        });
    }

}
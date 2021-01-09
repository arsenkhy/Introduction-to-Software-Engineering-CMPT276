package com.example.depthoffieldcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.depthoffieldcalculator.model.DepthFieldCalculator;
import com.example.depthoffieldcalculator.model.Lens;
import com.example.depthoffieldcalculator.model.LensManager;

import java.text.DecimalFormat;

/**
 * CalculateDepthOfField activity models the calculator for selected lens.
 * Data includes editTexts of user inputs, going back toolbar button
 * The activity supports calculating the Depth of Field and displays
 * the output whenever input is changed.
 */
public class CalculateDepthOfField extends AppCompatActivity {
    // Extra for getting the lens index from MainActivity
    private static final String EXTRA_LENS_INDEX =
            "com.example.depthoffieldcalculator.CalculateDepthOfField - the lensIndex";

    private LensManager manager;
    private int chosenLensIndex;            // Lens index to work with
    private Lens chosenLens;                // Lens to work with

    // The user text inputs initialization
    private EditText editTextCircleConfusion;
    private EditText editTextSubjectDistance;
    private EditText editTextAperture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_depth_of_field);
        setTitle("Calculate Depth of Field");          // Title

        // Extract the lens from activity to calculate DoF
        manager = LensManager.getInstance();                 // Getting instance to use one object across all activities
        extractDataFromIntent();
        chosenLens = manager.retrieveLens(chosenLensIndex);  // Chosen Lens

        // Prints the chosen lens from the list
        setupLensDisplay();

        // Re-calculates and displays results after user inputs
        setupEditTextInput();
    }

    public static Intent makeLaunchIntent(Context context, int lensIndex) {
        Intent intent = new Intent(context, CalculateDepthOfField.class);
        intent.putExtra(EXTRA_LENS_INDEX, lensIndex);               // Getting extras
        return intent;
    }

    private void extractDataFromIntent() {
        // Extract data from intent to use in activity
        Intent intent = getIntent();
        chosenLensIndex = intent.getIntExtra(EXTRA_LENS_INDEX, 0);
    }

    private void setupLensDisplay() {
        // Display the Lens currently used
        TextView text = findViewById(R.id.lensDisplay);
        String message = chosenLens.getLensName() + " " + chosenLens.getFocalLength() +
                "mm F" + chosenLens.getMaximumAperture();
        text.setText(message);
    }

    private void setupEditTextInput() {
        // Initialize the editText fields to work with
        editTextCircleConfusion = findViewById(R.id.cocInput);
        editTextSubjectDistance = findViewById(R.id.dtsInput);
        editTextAperture = findViewById(R.id.saInput);

        // Watching for changed text
        editTextCircleConfusion.addTextChangedListener(calculateTextWatcher);
        editTextSubjectDistance.addTextChangedListener(calculateTextWatcher);
        editTextAperture.addTextChangedListener(calculateTextWatcher);
    }

    private TextWatcher calculateTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                // Only compute when everything is entered to avoid runtime error
                if (inputIsNotEmpty()) {
                    // Record and convert user input to right value
                    double circleOfConfusion = Double.parseDouble(editTextCircleConfusion.getText().toString());    // COC
                    double distance = Double.parseDouble(editTextSubjectDistance.getText().toString());             // Distance to subject
                    double aperture = Double.parseDouble(editTextAperture.getText().toString());                    // Aperture

                    // Initialize textViews to display results on
                    TextView nearFocalOutput = findViewById(R.id.nfOutput);
                    TextView farFocalOutput = findViewById(R.id.ffOutput);
                    TextView depthFieldOutput = findViewById(R.id.dofOutput);
                    TextView hyperFocalOutput = findViewById(R.id.hfOutput);

                    // Checking if input is free of errors
                    boolean invalidCoC = circleOfConfusion <= 0;
                    boolean invalidDistance = distance <= 0;
                    boolean invalidAperture = aperture < chosenLens.getMaximumAperture() ||
                            aperture < 1.4;

                    // Identifying and prompting the errors
                    if (invalidAperture) {
                        editTextAperture.setError("Aperture need to be bigger F1.4 and max Lens aperture");
                    }
                    if (invalidDistance) {
                        editTextSubjectDistance.setError("Distance must bigger 0");
                    }
                    if (invalidCoC) {
                        editTextCircleConfusion.setError("Circle of Confusion must bigger 0");
                    }

                    // Checking if the inputs are correct to perform the calculations
                    if (!invalidCoC && !invalidAperture && !invalidDistance) {
                        // New object to calculate
                        DepthFieldCalculator calculator =
                                new DepthFieldCalculator(chosenLens, aperture, distance, circleOfConfusion);
                        // Displaying the results
                        nearFocalOutput.setText(formatM(calculator.getNearFocalPoint()) + "m");         // Near focal calculated
                        farFocalOutput.setText(formatM(calculator.getFarFocalPoint()) + "m");           // Far focal calculated
                        depthFieldOutput.setText(formatM(calculator.getDepthOfField()) + "m");          // Depth of Field calculated
                        hyperFocalOutput.setText(formatM(calculator.getHyperFocalDistance()) + "m");    // Hyperfocal calculated
                    }

                    hideKeyboard();         // Hides keyboard after making last input
                }
            }
        };

    private boolean inputIsNotEmpty() {
        // Checking whether all inputs are done to avoid runtime error
        return !(editTextAperture.getText().toString().isEmpty()) &&
               !(editTextCircleConfusion.getText().toString().isEmpty()) &&
               !(editTextSubjectDistance.getText().toString().isEmpty());
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();                     // Create current view
        // Make the listener of Enter key
        editTextAperture.setOnKeyListener(new View.OnKeyListener() {
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

    // *Copy from the Assignment #1
    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        final int MM_TO_M_CONVERTER = 1000;
        return df.format(distanceInM / MM_TO_M_CONVERTER);  //Calculations made in mm. Converted into m.
    }

}

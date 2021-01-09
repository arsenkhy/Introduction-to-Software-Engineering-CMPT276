package ca.cmpt276.as3.activityUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import ca.cmpt276.as3.model.R;

/**
 * Welcome class models a splash screen for user.
 * It supports launching main menu and and display animations
 */
public class WelcomeScreen extends AppCompatActivity {
    private final int SPLASH_TIME = 10000;          // The time activity is running
    private Handler handler = new Handler();        // Handler for timing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupWelcomeScreen();               // Set welcome time
        setupSkipButton();                  // The skip to the next activity
        animationSetup();                   // Animations of textViews
    }

    private void startMainMenu() {
        Intent intent = MainMenu.makeLaunchIntent(WelcomeScreen.this);
        startActivity(intent);
        finish();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startMainMenu();
        }
    };

    private void setupWelcomeScreen() {
        handler.postDelayed(runnable, SPLASH_TIME);
    }

    private void setupSkipButton() {
        Button buttonSkip = findViewById(R.id.skipButton);
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The skip has been pressed remove timing
                handler.removeCallbacks(runnable);
                startMainMenu();
            }
        });
    }

    private void animationSetup() {
        TextView imposterAnim = findViewById(R.id.imposter);
        TextView authorAnim = findViewById(R.id.author);

        // The dependency library found at: https://github.com/daimajia/AndroidViewAnimations
        // Set the animations
        YoYo.with(Techniques.Pulse)
                .duration(1500)
                .repeat(5)
                .playOn(imposterAnim);

        YoYo.with(Techniques.Tada)
                .delay(1000)
                .duration(1000)
                .repeat(1)
                .playOn(authorAnim);
    }
}

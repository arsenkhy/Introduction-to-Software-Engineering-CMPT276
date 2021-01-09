package ca.cmpt276.as3.activityUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.util.ArrayList;

import ca.cmpt276.as3.model.R;

/**
 * HelpActivity class creates the the activity for
 * a help and links. Data includes the arrayList of hyperlinks.
 * It supports make hyperlinks go to their assigned sites
 */
public class HelpActivity extends AppCompatActivity {

    // Intent
    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, HelpActivity.class);        // Start activity
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // list of textviews to assign hyperlinks
        ArrayList<TextView> hyperlinks = new ArrayList<>();

        // Add all texts that needed a link
        hyperlinks.add((TextView) findViewById(R.id.cmptHyperlink));
        hyperlinks.add((TextView) findViewById(R.id.image1));
        hyperlinks.add((TextView) findViewById(R.id.image2));
        hyperlinks.add((TextView) findViewById(R.id.image3));
        hyperlinks.add((TextView) findViewById(R.id.image4));
        hyperlinks.add((TextView) findViewById(R.id.image5));
        hyperlinks.add((TextView) findViewById(R.id.image6));
        hyperlinks.add((TextView) findViewById(R.id.fontSite));

        // Set the movement link
        for (TextView textView : hyperlinks) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
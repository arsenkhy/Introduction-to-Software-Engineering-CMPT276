package com.example.depthoffieldcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.depthoffieldcalculator.model.Lens;
import com.example.depthoffieldcalculator.model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * MainActivity activity models the lens list view.
 * Data includes listView with icons and LensManager lenses.
 * The activity supports selecting lenses to go to CalculateDepthOfField
 * and AddLens activities
 */
public class MainActivity extends AppCompatActivity {
    private static final int save_request = 777;        // The request code for a onActivityResult

    private LensManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Depth of Field Calculator");          // Title

        // Getting instance to use one object across all activities
        manager = LensManager.getInstance();

        updateListView();                  // Displays the Lens options
        clickListView();                   // Clicking the list to go to calculator
        clickFloatActionButton();          // Floating Action button to add lenses into list
    }

    private void clickFloatActionButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = AddLens.makeLaunchIntent(MainActivity.this);

            // Starting activity on result to get data from AddLens activity
            startActivityForResult(intent, save_request);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (save_request == requestCode) {
            if (resultCode == RESULT_OK) {
                // Get the values from the AddLens activity
                String make = data.getStringExtra("newLens");
                int focalLength = data.getIntExtra("focalLength", 0);
                double newAperture = data.getDoubleExtra("aperture", 0);
                int iconID = data.getIntExtra("icon", 0);

                manager.add(new Lens(make, newAperture, focalLength, iconID));          // Add lens to store
                updateListView();                                                       // Update the UI listView
                // Prompt that lens was added
                Toast.makeText(this, make + " is now added!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void clickListView() {
        ListView list = findViewById(R.id.listViewLens);
        // Make list clickable
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Passing the clicked lens index to perform calculations
                Intent intent = CalculateDepthOfField.makeLaunchIntent(MainActivity.this, position);
                startActivity(intent);
            }
        });
    }

    private void updateListView() {
       // Set the adapter after each calling
       ArrayAdapter<Lens> adapter = new MyListAdapter();
       ListView list = findViewById(R.id.listViewLens);
       list.setAdapter(adapter);
    }

    // *Used course tutorial on: https://www.youtube.com/watch?v=WRANgDgM2Zg
    private class MyListAdapter extends ArrayAdapter<Lens> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.list_view, manager.getLenses());
        }

        @SuppressLint("SetTextI18n")
        public View getView(int position, View convertView, ViewGroup parent) {
            // Checking if there is a view to work with
            View lensView = convertView;
            if (lensView == null) {
                lensView = getLayoutInflater().inflate(R.layout.list_view, parent, false);
            }

            // Retrieve the right lens
            Lens currentLens = manager.retrieveLens(position);

            // Find and display corresponding image
            ImageView imageView = lensView.findViewById(R.id.lensPicture);
            imageView.setImageResource(currentLens.getIconID());

            // Displaying lens make
            TextView textMake = lensView.findViewById(R.id.lens_name);
            textMake.setText(currentLens.getLensName());

            // Displaying lens focal length
            TextView textFocal = lensView.findViewById(R.id.lens_focal);
            textFocal.setText(currentLens.getFocalLength() + "mm");

            // Displaying lens make
            TextView textAperture = lensView.findViewById(R.id.lens_aperture);
            textAperture.setText("F" + currentLens.getMaximumAperture());

            return lensView;
        }

    }

}
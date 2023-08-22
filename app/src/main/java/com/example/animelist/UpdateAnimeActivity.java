package com.example.animelist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateAnimeActivity extends AppCompatActivity {

    EditText text_animeTitle_update, text_animeStudio_update, text_animeReleaseSeason_update;
    Button btn_updateAnime, btn_deleteAnime;
    String anime_id, anime_title, anime_studio, anime_release_season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_anime);

        // Find references
        text_animeTitle_update = findViewById(R.id.edit_animeTitle_update);
        text_animeStudio_update = findViewById(R.id.edit_animeStudio_update);
        text_animeReleaseSeason_update = findViewById(R.id.edit_animeReleaseSeason_update);
        btn_updateAnime = findViewById(R.id.btn_updateAnime);
        btn_deleteAnime = findViewById(R.id.btn_deleteAnime);

        // Fetch and set intent data to be displayed in the form
        getIntentData();

        // Set the action bar text to display the selected game's title only if an action bar exists
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(anime_title);
        }

        // Update game record in database when button is clicked
        btn_updateAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anime_title = text_animeTitle_update.getText().toString().trim();
                anime_studio = text_animeStudio_update.getText().toString().trim();
                anime_release_season = text_animeReleaseSeason_update.getText().toString().trim();

                // Check if any field is left blank
                if (anime_title.length() == 0 || anime_studio.length() == 0 ||
                        anime_release_season.length() == 0) {
                    Toast.makeText(UpdateAnimeActivity.this,
                            "Error: One or more fields are empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                MainActivity.db.Update(anime_id, anime_title, anime_studio,
                        Integer.parseInt(anime_release_season));
            }
        });

        // Delete game record from database when button is clicked
        btn_deleteAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // First ask for user confirmation to delete the game
                confirmDeletion();
            }
        });
    }

    // Fetch intent data from the RecyclerViewAdapter
    protected void getIntentData() {
        if (getIntent().hasExtra("game_id") && getIntent().hasExtra("anime_title") &&
                getIntent().hasExtra("anime_studio") &&
                getIntent().hasExtra("anime_release_season")) {
            anime_id = getIntent().getStringExtra("game_id");
            anime_title = getIntent().getStringExtra("anime_title");
            anime_studio = getIntent().getStringExtra("anime_studio");
            anime_release_season = getIntent().getStringExtra("anime_release_season_year");

            setIntentData();
        }
        else {
            // If there is no intent data, show toast message
            Toast.makeText(this, "There is no data in the database.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Set EditTexts with the intent data
    protected void setIntentData() {
        text_animeTitle_update.setText(anime_title);
        text_animeStudio_update.setText(anime_studio);
        text_animeReleaseSeason_update.setText(anime_release_season);
    }

    // Ask user for confirmation to delete the selected game from their list
    protected void confirmDeletion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Entry with game title '" + anime_title + "' is about to be deleted." +
                " Proceed?");

        // Delete when prompt is confirmed and return to main activity
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.db.Delete(anime_id);
                finish();
            }
        });

        // Do nothing when prompt is cancelled
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { }
        });

        builder.create().show();
    }
}
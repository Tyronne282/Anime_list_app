package com.example.animelist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAnimeActivity extends AppCompatActivity {

    EditText text_animeTitle, text_animeStudio, text_animeReleaseSeason;
    Button btn_addAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anime);

        // Find references
        text_animeTitle = findViewById(R.id.edit_animeTitle);
        text_animeStudio = findViewById(R.id.edit_animeStudio);
        text_animeReleaseSeason = findViewById(R.id.edit_animeReleaseSeason);
        btn_addAnime = findViewById(R.id.btn_addAnime);

        // Insert form data into table when the add button is clicked, then return to main activity
        btn_addAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if any field is left blank
                if (text_animeTitle.getText().toString().trim().length() == 0 ||
                        text_animeStudio.getText().toString().trim().length() == 0 ||
                        text_animeReleaseSeason.getText().toString().trim().length() == 0) {
                    Toast.makeText(AddAnimeActivity.this,
                            "Error: One or more fields are empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                MainActivity.db.Insert(text_animeTitle.getText().toString().trim(),
                        text_animeStudio.getText().toString().trim(),
                        Integer.parseInt(text_animeReleaseSeason.getText().toString().trim()));
                MainActivity.refreshAdapter();
                finish();
            }
        });
    }
}
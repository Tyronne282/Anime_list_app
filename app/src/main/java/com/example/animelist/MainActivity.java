package com.example.animelist;

import static androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Create object instances
    public static DataHelper db;
    static RecyclerView item_anime;
    static RecyclerViewAdapter recyclerViewAdapter;
    FloatingActionButton btn_addActivity;
    ArrayList<String> id, anime_title, anime_studio, anime_release_season;
    LinearLayoutManager layoutMgr_anime_list;

    // Worth inserting some test data as seeding to see if the adapter works
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find references
        item_anime = findViewById(R.id.item_anime);
        btn_addActivity = findViewById(R.id.btn_addAnime);

        // When add button is pressed, open activity that adds a new video game to the list
        btn_addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddAnimeActivity.class);
                startActivity(intent);
            }
        });

        // Initialise objects
        db = new DataHelper(MainActivity.this);
        id = new ArrayList<>();
        anime_title = new ArrayList<>();
        anime_studio = new ArrayList<>();
        anime_release_season = new ArrayList<>();
        layoutMgr_anime_list = new LinearLayoutManager(this);

        db.getData(this);

        // Initialise adapter and pass table columns as args to be shown in the main activity
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, this, id,
                anime_title, anime_studio, anime_release_season);
        item_anime.setAdapter(recyclerViewAdapter);
        item_anime.setLayoutManager(layoutMgr_anime_list);
    }

    // Refresh adapter (when database has been changed)
    public static void refreshAdapter() {
        recyclerViewAdapter.notifyDataSetChanged();
        item_anime.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Refresh activity after navigating to update activity
        if (requestCode == 1) {
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Display settings menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Menu item selection
        if (item.getItemId() == R.id.setting_toggleTheme) {
            // Check whether dark theme is on or not
            if (getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
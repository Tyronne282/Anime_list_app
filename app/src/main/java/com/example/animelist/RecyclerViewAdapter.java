package com.example.animelist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/***
 * Used to define the display layout of data from the database within the app
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    int index;

    // Create object instances
    Context context;
    Activity activity;
    ArrayList anime_id, anime_title, anime_studio, anime_release_season;

    // Constructor that will be called in the main activity with data passed as args
    public RecyclerViewAdapter(Context context, Activity activity, ArrayList id,
                               ArrayList anime_title, ArrayList anime_studio,
                               ArrayList anime_release_season) {
        this.context = context;
        this.activity = activity;
        this.anime_id = id;
        this.anime_title = anime_title;
        this.anime_studio = anime_studio;
        this.anime_release_season = anime_release_season;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a view from the list item fragment
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int index) {
        // Get strings from ArrayLists holding the data
        holder.text_anime_id.setText(String.valueOf(anime_id.get(index)));
        holder.text_anime_title.setText(String.valueOf(anime_title.get(index)));
        holder.text_anime_studio.setText(String.valueOf(anime_studio.get(index)));
        holder.text_anime_release_season.setText(String.valueOf(anime_release_season.get(index)));

        // Redirect to update game activity when a game list item is selected in main activity
        holder.layout_listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateAnimeActivity.class);

                // Pass information on table fields to update activity
                intent.putExtra("anime_id", String.valueOf(anime_id.get(index)));
                intent.putExtra("anime_title", String.valueOf(anime_title.get(index)));
                intent.putExtra("anime_studio", String.valueOf(anime_studio.get(index)));
                intent.putExtra("anime_release_year",
                        String.valueOf(anime_release_season.get(index)));

                // Refresh main activity when navigating to update activity
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return anime_id.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_anime_id, text_anime_title, text_anime_studio, text_anime_release_season;
        LinearLayout layout_listItem;

        // Find the text ids for the TextViews in the list item fragment
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_anime_id = itemView.findViewById(R.id.text_id);
            text_anime_title = itemView.findViewById(R.id.text_animeTitle);
            text_anime_studio = itemView.findViewById(R.id.text_animeStudio);
            text_anime_release_season = itemView.findViewById(R.id.text_animeReleaseSeason);
            layout_listItem = itemView.findViewById(R.id.layout_listItem);
        }
    }
}
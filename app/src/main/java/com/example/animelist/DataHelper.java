package com.example.animelist;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
public final class DataHelper extends SQLiteOpenHelper {

    private Context context;

    // Constants for database table for storing user's watched anime
    private static final String DB_NAME = "AnimeDatabase.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "watched_anime";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ANIME_TITLE = "anime_title";
    private static final String COLUMN_STUDIO = "anime_studio";
    private static final String COLUMN_RELEASE_SEASON = "release_season";

    // SQL statements to add and delete anime from the database
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_ANIME_TITLE + " TEXT, " + COLUMN_STUDIO + " TEXT, " +
                COLUMN_RELEASE_SEASON + " INTEGER); ";
    private  static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    DataHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        try {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public long Insert(String title, String studio, int year) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ANIME_TITLE, title);
        values.put(COLUMN_STUDIO, studio);
        values.put(COLUMN_RELEASE_SEASON, year);

        try {
            long newRowId = db.insertOrThrow(TABLE_NAME, null, values);
            Toast.makeText(context, "Added a new anime", Toast.LENGTH_SHORT).show();
            return newRowId;
        }
        catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(context, "Failed to add anime", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    public Cursor Read() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(sql, null);
        }
        return cursor;
    }

    public void getData(MainActivity mainActivity) {
        Cursor cursor = Read();

        if (cursor.getCount() == 0) {
            Toast.makeText(mainActivity, "The database is empty.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                mainActivity.id.add(cursor.getString(0));
                mainActivity.anime_title.add(cursor.getString(1));
                mainActivity.anime_studio.add(cursor.getString(2));
                mainActivity.anime_release_season.add(cursor.getString(3));
            }
        }
    }

    public long Update(String id,String title,String studio,int year) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ANIME_TITLE, title);
        values.put(COLUMN_STUDIO, studio);
        values.put(COLUMN_RELEASE_SEASON, year);

        try {
            long updateRowTd = db.update(TABLE_NAME, values,"id=?",new String[]{id});
            Toast.makeText(context, "Updated anime details",
                Toast.LENGTH_SHORT).show();
            return updateRowTd;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    public long Delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            long deleteRowId = db.delete(TABLE_NAME, "id=?", new String[]{id});
            Toast.makeText(context, "Successfully Deleted anime", Toast.LENGTH_SHORT).show();
            return  deleteRowId;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, "Failed to delete anime", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }
}

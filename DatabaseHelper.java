package com.example.beat_box;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "musicApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    private static final String TABLE_LIKED_SONGS = "liked_songs";
    private static final String COL_ID = "id";
    private static final String COL_SONG_TITLE = "song_title";

    // SQL query to create the table
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_LIKED_SONGS + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_SONG_TITLE + " TEXT NOT NULL" +
            ");";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table when the database is created
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and create a new one when the database version is upgraded
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKED_SONGS);
        onCreate(db);
    }

    // Save a song as liked in the database
    public void saveLikedSong(String songTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SONG_TITLE, songTitle);
        db.insert(TABLE_LIKED_SONGS, null, values); // Insert the song title into the table
    }

    // Remove a song from the liked songs
    public void removeLikedSong(String songTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LIKED_SONGS, COL_SONG_TITLE + "=?", new String[]{songTitle}); // Remove song by title
    }

    // Retrieve all liked songs
    @SuppressLint("Range")
    public List<String> getAllLikedSongs() {
        List<String> likedSongs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LIKED_SONGS, null); // Fetch all rows from the table
        if (cursor.moveToFirst()) {
            do {
                likedSongs.add(cursor.getString(cursor.getColumnIndex(COL_SONG_TITLE))); // Add song title to the list
            } while (cursor.moveToNext());
        }
        cursor.close();
        return likedSongs; // Return the list of liked songs
    }

    // Check if a song is liked
    public boolean isSongLiked(String songTitle) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LIKED_SONGS + " WHERE " + COL_SONG_TITLE + "=?", new String[]{songTitle});
        boolean isLiked = cursor.getCount() > 0;
        cursor.close();
        return isLiked; // Return true if song is liked, else false
    }
}

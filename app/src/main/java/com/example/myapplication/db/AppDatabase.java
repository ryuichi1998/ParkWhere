package com.example.myapplication.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Bookmark.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookmarkDao getBookmarkDao();               // Dao for bookmark data

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB_NAME").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

//    Usage:
//    AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
//    Bookmark bookmark = new Bookmark();
//    db.getBookmarkDao().insertBookmark(bookmark);

    //TODO: Add database for other data.

}

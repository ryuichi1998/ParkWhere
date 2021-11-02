package com.example.myapplication.db.bookmark;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.Bookmark;

@Database(entities = {Bookmark.class}, version = 1)
public abstract class BookmarkDatabase extends RoomDatabase {
    public abstract BookmarkDao getBookmarkDao();               // Dao for bookmark data

    private static BookmarkDatabase INSTANCE;

    public static BookmarkDatabase getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BookmarkDatabase.class, "DB_NAME").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

//    Usage:
//    BookmarkDatabase db = BookmarkDatabase.getDbInstance(this.getApplicationContext());
//    Bookmark bookmark = new Bookmark();
//    db.getBookmarkDao().insertBookmark(bookmark);

    //TODO: Add database for other data.

}

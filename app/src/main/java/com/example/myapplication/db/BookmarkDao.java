package com.example.myapplication.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookmarkDao {


    @Query("SELECT * FROM bookmark")
    List<Bookmark> getAll();

    @Insert
    void insertBookmark(Bookmark... bookmarks);

    @Delete
    void delete(Bookmark bookmark);

    //TODO: Implement all dao methods
}

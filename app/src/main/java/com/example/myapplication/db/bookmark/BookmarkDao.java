package com.example.myapplication.db.bookmark;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Bookmark;

import java.util.List;

@Dao
public interface BookmarkDao {


    @Query("SELECT * FROM Bookmark")
    LiveData<List<Bookmark>> getAll();

    @Insert
    void insertBookmark(Bookmark... bookmarks);

    @Delete
    void delete(Bookmark ... bookmark);

    @Update
    void update(Bookmark... bookmarks);
}

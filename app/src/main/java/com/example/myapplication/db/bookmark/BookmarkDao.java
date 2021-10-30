package com.example.myapplication.db.bookmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.db.bookmark.Bookmark;

import java.util.List;

@Dao
public interface BookmarkDao {


    @Query("SELECT * FROM Bookmark")
    LiveData<List<Bookmark>> getAll();

    @Insert
    void insertBookmark(Bookmark... bookmarks);

    @Delete
    void delete(Bookmark ... bookmark);

    //TODO: Implement all dao methods
}

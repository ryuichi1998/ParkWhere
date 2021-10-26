package com.example.myapplication.db.history;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.db.bookmark.Bookmark;

import java.util.List;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM History")
    List<History> getAllHistory();

    @Insert
    void insertHistory(History... history);

    @Delete
    void delete(History history);
}

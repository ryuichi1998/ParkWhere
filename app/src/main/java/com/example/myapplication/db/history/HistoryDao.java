package com.example.myapplication.db.history;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.db.bookmark.Bookmark;

import java.util.List;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM History ORDER BY History.date DESC, History.start_time DESC")
    List<History> getAllHistory();

    @Insert
    void insertHistory(History... history);

    @Delete
    void delete(History history);
}

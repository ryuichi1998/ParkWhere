package com.example.myapplication.db.history;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.model.History;

import java.util.List;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM History ORDER BY History.history_id DESC")
    List<History> getAllHistory();

    @Insert
    void insertHistory(History... history);

    @Delete
    void delete(History ... history);
}

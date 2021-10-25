package com.example.myapplication.db.history;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.db.carpark.CarParkDetailsDao;
import com.example.myapplication.db.carpark.CarParkDetailsDataBase;

@Database(entities = {History.class}, version =  1)
public abstract class HistoryDatabase extends RoomDatabase {
    // expose DAO
    public abstract HistoryDao getHistoryDao();

    // instance mode
    private static HistoryDatabase INSTANCE;
    public synchronized static HistoryDatabase getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), HistoryDatabase.class, "History_Database").build();
        }
        return INSTANCE;
    }
}

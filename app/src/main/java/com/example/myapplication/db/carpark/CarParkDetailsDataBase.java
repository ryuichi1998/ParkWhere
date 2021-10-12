package com.example.myapplication.db.carpark;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CarParkDetails.class}, version = 1)
public abstract class CarParkDetailsDataBase extends RoomDatabase {
    // expose DAO
    public abstract CarParkDetailsDao getCarParkDetailsDao();

    // instance mode
    private static CarParkDetailsDataBase INSTANCE;
    public synchronized static CarParkDetailsDataBase getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CarParkDetailsDataBase.class, "CarParkDetail_DataBase").build();
        }
        return INSTANCE;
    }
}

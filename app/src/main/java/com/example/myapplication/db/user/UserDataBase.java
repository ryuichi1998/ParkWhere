package com.example.myapplication.db.user;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.myapplication.db.carpark.CarParkDetailsDataBase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDataBase extends RoomDatabase {

    private static UserDataBase instance;

    public abstract UserDao userDao();

    public static synchronized UserDataBase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDataBase.class, "user_database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}

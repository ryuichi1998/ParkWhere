package com.example.myapplication.db.user;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDataBase  extends RoomDatabase {
    public abstract UserDao userDao();
}


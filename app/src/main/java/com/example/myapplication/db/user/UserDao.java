package com.example.myapplication.db.user;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM User WHERE userId = :userId")
    User getUserByUserId(int userId);

    @Query("SELECT  * FROM User WHERE email_address= :email and password= :pass")
    User login(String email, String pass);
}

package com.example.myapplication.model;

import android.widget.EditText;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "email_address")
    private String email;

    @ColumnInfo(name = "password")
    private String pass;

    @ColumnInfo(name = "vehicle_type")
    private int vehType;

    public User(String name, String email, String pass, int vehType) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.vehType = vehType;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public int getVehType() {
        return vehType;
    }
}

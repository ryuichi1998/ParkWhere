package com.example.myapplication.db.bookmark;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bookmark {
    @PrimaryKey(autoGenerate = true)
    public int bookmarkid;

    @ColumnInfo(name = "nickname")
    public String nickname;

    @ColumnInfo(name = "longitude")
    public double longitude;

    @ColumnInfo(name = "Latitude")
    public  double latitude;

    //TODO: Add all columns.
}


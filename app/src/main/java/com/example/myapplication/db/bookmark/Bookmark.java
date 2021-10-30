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

    public Bookmark(String nickname, double longitude, double latitude) {
        this.nickname = nickname;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "bookmarkid=" + bookmarkid +
                ", nickname='" + nickname + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}


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

    public Bookmark(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "bookmarkid=" + bookmarkid +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}


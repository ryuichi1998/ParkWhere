package com.example.myapplication.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bookmark {

    @NonNull
    @PrimaryKey
    public String nickname;

    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "avail_lots")
    public String avail_lots;

    public Bookmark(String nickname, String id) {
        this.nickname = nickname;
        this.id = id;
        this.avail_lots = "Click To Query";
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvail_lots() {
        return avail_lots;
    }

    public void setAvail_lots(String avail_lots) {
        this.avail_lots = avail_lots;
    }
}


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

    @Override
    public String toString() {
        return "Bookmark{" +
                "bookmarkid=" + bookmarkid +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}


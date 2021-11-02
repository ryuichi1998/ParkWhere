package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class History {
    @PrimaryKey(autoGenerate = true)
    public int history_id;

    @ColumnInfo(name = "carpark_name")
    public String carpark_name;

    @ColumnInfo(name = "carpark_id")
    public String carpark_id;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "start_time")
    public String start_time;

    @ColumnInfo(name = "duration")
    public String duration;

    public History(String carpark_name, String carpark_id, String date, String start_time, String duration) {
        this.carpark_name = carpark_name;
        this.carpark_id = carpark_id;
        this.date = date;
        this.start_time = start_time;
        this.duration = duration;
    }

    public String getCarpark_id() {
        return carpark_id;
    }

    public void setCarpark_id(String carpark_id) {
        this.carpark_id = carpark_id;
    }

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public String getCarpark_name() {
        return carpark_name;
    }

    public void setCarpark_name(String carpark_name) {
        this.carpark_name = carpark_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

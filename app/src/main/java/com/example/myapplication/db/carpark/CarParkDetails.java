package com.example.myapplication.db.carpark;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// primary key,
@Entity
public class CarParkDetails {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String id;

    public String address;

    public CarParkDetails(String id, String address, String car_park_type, String type_of_parking_system, String short_term_parking, String free_parking, String night_parking, String is_bookmarked) {
        this.id = id;
        this.address = address;
        this.car_park_type = car_park_type;
        this.type_of_parking_system = type_of_parking_system;
        this.short_term_parking = short_term_parking;
        this.free_parking = free_parking;
        this.night_parking = night_parking;
        this.is_bookmarked = is_bookmarked;
    }

    @ColumnInfo(name = "car_park_type")
    public String car_park_type;
    @ColumnInfo(name = "type_of_parking_system")
    public String type_of_parking_system;
    @ColumnInfo(name = "short_term_parking")
    public String short_term_parking;
    @ColumnInfo(name = "free_parking")
    public String free_parking;
    @ColumnInfo(name = "night_parking")
    public String night_parking;
    @ColumnInfo(name = "is_bookmarked")
    public String is_bookmarked;

    public String get_id() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCar_park_type() {
        return car_park_type;
    }

    public void setCar_park_type(String car_park_type) {
        this.car_park_type = car_park_type;
    }

    public String getType_of_parking_system() {
        return type_of_parking_system;
    }

    public void setType_of_parking_system(String type_of_parking_system) {
        this.type_of_parking_system = type_of_parking_system;
    }

    public String getShort_term_parking() {
        return short_term_parking;
    }

    public void setShort_term_parking(String short_term_parking) {
        this.short_term_parking = short_term_parking;
    }

    public String getFree_parking() {
        return free_parking;
    }

    public void setFree_parking(String free_parking) {
        this.free_parking = free_parking;
    }

    public String getNight_parking() {
        return night_parking;
    }

    public void setNight_parking(String night_parking) {
        this.night_parking = night_parking;
    }

    public String getIs_bookmarked() {
        return is_bookmarked;
    }

    public void setIs_bookmarked(String is_bookmarked) {
        this.is_bookmarked = is_bookmarked;
    }

    public void set_id(String _id) {
        this.id = _id;
    }

    @Override
    public String toString() {
        return "CarParkDetails{" +
                "_id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", car_park_type='" + car_park_type + '\'' +
                ", type_of_parking_system='" + type_of_parking_system + '\'' +
                ", short_term_parking='" + short_term_parking + '\'' +
                ", free_parking='" + free_parking + '\'' +
                ", night_parking='" + night_parking + '\'' +
                ", is_bookmarked='" + is_bookmarked + '\'' +
                '}';
    }
}

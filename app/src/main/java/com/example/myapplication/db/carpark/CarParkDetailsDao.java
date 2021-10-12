package com.example.myapplication.db.carpark;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao  // DataBase Access Object
public interface CarParkDetailsDao {
    @Insert
    void insertCarParkDetails(CarParkDetails ... carParkDetails);

    @Update
    void updateCarParkDetails(CarParkDetails ... carParkDetails);

//    @Query("DELETE FROM CarParkDetails")
//    void deleteCarParkDetails(CarParkDetails ... carParkDetails);

    @Query("SELECT * FROM CarParkDetails")
    List<CarParkDetails> getAllCarparkDetails();

    @Query("SELECT * FROM CarParkDetails WHERE is_bookmarked = 'YES'")
    List<CarParkDetails> getBookmarkedCarparkDetails();
}

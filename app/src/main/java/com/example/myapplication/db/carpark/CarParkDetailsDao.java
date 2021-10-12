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

    /**
     *          : which represents {id : [0 address,
     *                                    1 car_park_type,
     *                                    2 type_of_parking_system,
     *                                    3 short_term_parking,
     *                                    4 free_parking,
     *                                    5 night_parking])
     */
    @Query("SELECT * FROM CARPARKDETAILS WHERE id = :id")
    List<CarParkDetails> getCarParkDetailsById(String id);

    @Query("SELECT * FROM CarParkDetails WHERE is_bookmarked = 'YES'")
    List<CarParkDetails> getBookmarkedCarparkDetails();
}

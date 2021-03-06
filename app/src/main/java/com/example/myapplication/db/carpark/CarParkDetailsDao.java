package com.example.myapplication.db.carpark;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.CarParkDetails;

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

    @Query("SELECT * FROM CarParkDetails")
    LiveData<List<CarParkDetails>> getAllCarparkDetailsLive();
    /**
     *          : which represents {id : [0 address,
     *                                    1 car_park_type,
     *                                    2 type_of_parking_system,
     *                                    3 short_term_parking,
     *                                    4 free_parking,
     *                                    5 night_parking
     *                                    6 is_bookmarked])
     */
    @Query("SELECT * FROM CARPARKDETAILS WHERE id = :id")
    List<CarParkDetails> getCarParkDetailsById(String id);

    @Query("SELECT * FROM CarParkDetails WHERE is_bookmarked = 'YES'")
    List<CarParkDetails> getBookmarkedCarparkDetails();

    @Query("SELECT * FROM CarParkDetails WHERE address = :address")
    List<CarParkDetails> getCarParkDetailsByAddress(String address);
}

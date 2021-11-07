package com.example.myapplication.ui.tracking;

import android.app.Application;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.db.bookmark.BookmarkDatabase;
import com.example.myapplication.db.carpark.AsyncResponse;
import com.example.myapplication.db.carpark.CarParkDetailsDataBase;
import com.example.myapplication.model.Bookmark;
import com.example.myapplication.model.CarParkDetails;
import com.example.myapplication.repo.BookmarkRepository;
import com.example.myapplication.repo.DBEngine;

import java.io.IOException;
import java.util.List;

public class TrackingViewModel extends AndroidViewModel {
    private DBEngine repository;
    private LiveData<List<CarParkDetails>> carpark_list;
    private CarParkDetailsDataBase db;

    public TrackingViewModel(Application application) throws IOException {
        super(application);
        repository = new DBEngine(application);
        carpark_list = repository.getAllCarParkDetails();
    }

    public CarParkDetails getCarParkDetailByID(String id, AsyncResponse response) {
        return repository.getCarParkDetailByID(id, response);
    }

    public void updateCarParkDetails(String id,String type, String value) {repository.updateCarParkDetails(id, type, value);}

}
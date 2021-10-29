package com.example.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.DataMallCarParkAvailability;
import com.example.myapplication.retrofit.DataMallApiInterface;
import com.example.myapplication.retrofit.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<DataMallCarParkAvailability>> carParkAvailableList;

    public HomeViewModel() {
        carParkAvailableList = new MutableLiveData<>();
    }

    public  MutableLiveData<List<DataMallCarParkAvailability>> getCarParkAvailableList() {
        return carParkAvailableList;
    }

}
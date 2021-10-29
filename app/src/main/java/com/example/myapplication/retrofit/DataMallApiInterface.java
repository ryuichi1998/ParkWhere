package com.example.myapplication.retrofit;

import com.example.myapplication.model.DataMallCarParkAvailability;
import com.example.myapplication.model.DataMallCarParkAvailabilityInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface DataMallApiInterface {

    @GET ("CarParkAvailabilityv2")
    Call<DataMallCarParkAvailabilityInfo> getDataMallCarParkAvailability(@Header("AccountKey") String key);
}

package com.example.myapplication.RetroFit;

import com.example.myapplication.CarParkAvailability;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UraApi {

    @GET("invokeUraDS?service=Car_Park_Availability")
    Call<List<CarParkAvailability>> getPost();

}

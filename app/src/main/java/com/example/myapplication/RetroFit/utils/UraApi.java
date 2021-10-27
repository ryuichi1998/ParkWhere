package com.example.myapplication.RetroFit.utils;

import com.example.myapplication.CarParkAvailability;
import com.example.myapplication.RetroFit.response.URAAvalibilatyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UraApi {

    //  https://www.ura.gov.sg/uraDataService/invokeUraDS?service=Car_Park_Details

    @GET("/invokeUraDS/")
    Call<URAAvalibilatyResponse> getAvailableCarParks(
            @Query("service") String service,
            @Header("AccessKey") String key,
            @Header("Token") String token
    );

}

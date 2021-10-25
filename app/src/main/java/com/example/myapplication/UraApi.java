package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UraApi {

    @GET()
    Call<List<CarPark>> getPost();

}

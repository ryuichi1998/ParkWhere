package com.example.myapplication.RetroFit.request;

import com.example.myapplication.RetroFit.utils.Credentials;
import com.example.myapplication.RetroFit.utils.UraApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class URAService {

    public static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Credentials.URA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static UraApi uraApi = retrofit.create(UraApi.class);

    public UraApi getUraApi() {
        return uraApi;
    }

}

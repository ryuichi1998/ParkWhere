package com.example.myapplication.retrofit;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtil extends Application {

    final String TAG = getClass().getSimpleName();
    private static RetrofitUtil mInstance;
    public static Retrofit retrofit = null;
    private static String BASE_URL = "http://datamall2.mytransport.sg/ltaodataservice/";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized RetrofitUtil getmInstance() {
        return mInstance;
    }

    public static Retrofit getRetrofitClient() {
        if(retrofit == null) {

            okhttp3.OkHttpClient client =  new okhttp3.OkHttpClient.Builder().build();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }

        return retrofit;
    }
}

package com.example.myapplication.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.DataMallCarParkAvailability;
import com.example.myapplication.model.DataMallCarParkAvailabilityInfo;
import com.example.myapplication.retrofit.DataMallApiInterface;
import com.example.myapplication.retrofit.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataMallRepo {

    private static DataMallRepo instance;

    // LiveData
    private MutableLiveData<List<DataMallCarParkAvailability>> availableLots = new MutableLiveData<>();

    public static DataMallRepo getInstance() {
        if (instance == null) {
            instance = new DataMallRepo();
        }
        return instance;
    }

    private DataMallRepo() {
        availableLots = new MutableLiveData<>();
    }

    public LiveData<List<DataMallCarParkAvailability>> getLots(){

        DataMallApiInterface apiInterface = RetrofitUtil.getRetrofitClient().create(DataMallApiInterface.class);
        Call<DataMallCarParkAvailabilityInfo> call = apiInterface.getDataMallCarParkAvailability("3EK93kn7S5GPNDFR1ZNYGw==");
        call.enqueue(new Callback<DataMallCarParkAvailabilityInfo>() {
            @Override
            public void onResponse(Call<DataMallCarParkAvailabilityInfo> call, Response<DataMallCarParkAvailabilityInfo> response) {
                Log.d("Success", response.body().toString());
                if (response.code() == 200) {
                    Log.v("Tag", "the responnse" +response.body().toString());

                    List<DataMallCarParkAvailability> carParkAvailabilities = new ArrayList<>(response.body().getAvailabilities());
                    availableLots.setValue(response.body().getAvailabilities());

                } else {
                    availableLots.setValue(null);
                    try {
                        Log.v("Tag", "Error" + response.errorBody().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataMallCarParkAvailabilityInfo> call, Throwable t) {
                String message = t.getMessage();
                Log.d("failure", message);
            }
        });
        return availableLots;
    }


    public MutableLiveData<List<DataMallCarParkAvailability>> requestCarParkAvailability() {
        final MutableLiveData<List<DataMallCarParkAvailability>> mutableLiveData = new MutableLiveData<>();

        DataMallApiInterface apiInterface = RetrofitUtil.getRetrofitClient().create(DataMallApiInterface.class);
        Call<DataMallCarParkAvailabilityInfo> call = apiInterface.getDataMallCarParkAvailability("3EK93kn7S5GPNDFR1ZNYGw==");
        call.enqueue(new Callback<DataMallCarParkAvailabilityInfo>() {
            @Override
            public void onResponse(Call<DataMallCarParkAvailabilityInfo> call, Response<DataMallCarParkAvailabilityInfo> response) {
                Log.d("Success", response.body().toString());
                if (response.code() == 200) {
                    Log.v("Tag", "the responnse" +response.body().toString());

                    List<DataMallCarParkAvailability> carParkAvailabilities = new ArrayList<>(response.body().getAvailabilities());
                    mutableLiveData.setValue(response.body().getAvailabilities());

                    for (DataMallCarParkAvailability carParkAvailability: carParkAvailabilities) {
                        Log.v("Tag", "the available lots: " + carParkAvailability.getAvailableLots());
                        Log.v("Tag", "the Developemnt: " + carParkAvailability.getDevelopment());
                    }

                } else {
                    mutableLiveData.setValue(null);
                    try {
                        Log.v("Tag", "Error" + response.errorBody().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataMallCarParkAvailabilityInfo> call, Throwable t) {
                String message = t.getMessage();
                Log.d("failure", message);
            }
        });
        return mutableLiveData;
    }

}

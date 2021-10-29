package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataMallCarParkAvailabilityInfo {
    @SerializedName("value")
    @Expose
    private List<DataMallCarParkAvailability> availabilities;

    public List<DataMallCarParkAvailability> getAvailabilities() {
        return availabilities;
    }

    @Override
    public String toString() {
        return "Response {" +
                "availability=" + availabilities +
                '}';
    }
}

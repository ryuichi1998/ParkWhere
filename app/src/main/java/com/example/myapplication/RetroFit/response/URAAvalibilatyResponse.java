package com.example.myapplication.RetroFit.response;

import com.example.myapplication.CarParkAvailability;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class URAAvalibilatyResponse {
    @SerializedName("Result")
    @Expose
    private List<CarParkAvailability> availabilities;

    public List<CarParkAvailability> getAvailabilities() {
        return availabilities;
    }

    @Override
    public String toString() {
        return "URAAvalibilatyResponse{" +
                "availability=" + availabilities +
                '}';
    }
}

package com.example.myapplication.db.carpark;

import com.example.myapplication.model.CarParkDetails;

import java.util.List;

public interface AsyncResponse {
    public void queryFinish(List<CarParkDetails> cp_detail);
}

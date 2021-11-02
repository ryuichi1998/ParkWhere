package com.example.myapplication.db.history;

import com.example.myapplication.model.History;

import java.util.List;

public interface AsyncResponse {
    public void queryFinish(List<History> histories);
}

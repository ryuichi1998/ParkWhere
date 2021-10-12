package com.example.myapplication.db.carpark;

import android.accessibilityservice.GestureDescription;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.transform.Result;

public class DBEngine {
    public CarParkDetailsDao carpark_dao;

    public DBEngine(Context context) throws IOException {
        CarParkDetailsDataBase carpark_db = CarParkDetailsDataBase.getInstance(context);
        carpark_dao = carpark_db.getCarParkDetailsDao();
    }

    public void initializeDB(Context context) throws IOException {
        HashMap<String, ArrayList<String>> hm = CSVCarParkDetail.getDetailInfo(context);
        ArrayList<String> detail_storage_tmp;
        for (String key_ : hm.keySet()){
            detail_storage_tmp = hm.get(key_);
            System.out.println("Value is = " + detail_storage_tmp.toString());
            insertCarParkDetails(new CarParkDetails(key_, detail_storage_tmp.get(0), detail_storage_tmp.get(1), detail_storage_tmp.get(2), detail_storage_tmp.get(3), detail_storage_tmp.get(4), detail_storage_tmp.get(5)));
        }
    }

    // insert
    public void insertCarParkDetails(CarParkDetails ... carParkDetails){
        System.out.println("DataBaseOutput: Inserted");
        new InsertAsyncTask(carpark_dao).execute(carParkDetails);
    }

    static class InsertAsyncTask extends AsyncTask<CarParkDetails, Void, Void>{

        private CarParkDetailsDao dao;

        public InsertAsyncTask(CarParkDetailsDao cp_dao){
            dao = cp_dao;
        }

        @Override
        protected Void doInBackground(CarParkDetails... carParkDetails) {
            dao.insertCarParkDetails(carParkDetails);
            return null;
        }
    }
    // TODO:
    // delete
    // update
    // query
}

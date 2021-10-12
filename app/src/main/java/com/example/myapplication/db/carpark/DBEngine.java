package com.example.myapplication.db.carpark;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            insertCarParkDetails(new CarParkDetails(key_, detail_storage_tmp.get(0), detail_storage_tmp.get(1), detail_storage_tmp.get(2), detail_storage_tmp.get(3), detail_storage_tmp.get(4), detail_storage_tmp.get(5)));
        }
    }

    // insert
    public void insertCarParkDetails(CarParkDetails ... carParkDetails){
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
    public CarParkDetails getCarParkDetailByID(String id, AsyncResponse response){
        final CarParkDetails[] cp_detail_outer = new CarParkDetails[1];

        new QueryAsyncTask(response, carpark_dao, id).execute();

        return cp_detail_outer[0];
    }

    static class QueryAsyncTask extends AsyncTask<Void, Void, List<CarParkDetails>>{

        public AsyncResponse delegate = null;

        private CarParkDetailsDao dao;
        private String id;

        public QueryAsyncTask(AsyncResponse delegate, CarParkDetailsDao cp_dao, String id){
            dao = cp_dao;
            this.id = id;
            this.delegate = delegate;
        }

        @Override
        protected List<CarParkDetails> doInBackground(Void... voids) {
            System.out.println("Result" + ": " + dao.getCarParkDetailsById(id).get(0));
            return dao.getCarParkDetailsById(id);
        }

        @Override
        protected void onPostExecute(List<CarParkDetails> carParkDetails) {
            super.onPostExecute(carParkDetails);
            delegate.queryFinish(carParkDetails);
        }
    }
}

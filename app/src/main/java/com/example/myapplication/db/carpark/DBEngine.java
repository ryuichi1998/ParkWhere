package com.example.myapplication.db.carpark;

import android.content.Context;
import android.os.AsyncTask;

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
//            insertCarParkDetails(new CarParkDetails(key_, detail_storage_tmp.get(0), detail_storage_tmp.get(1), detail_storage_tmp.get(2), detail_storage_tmp.get(3), detail_storage_tmp.get(4), detail_storage_tmp.get(5),detail_storage_tmp.get(6),detail_storage_tmp.get(7),detail_storage_tmp.get(8)));
            insertCarParkDetails(new CarParkDetails(key_, detail_storage_tmp.get(0), detail_storage_tmp.get(1), detail_storage_tmp.get(2), detail_storage_tmp.get(3), detail_storage_tmp.get(4), detail_storage_tmp.get(5),detail_storage_tmp.get(6),detail_storage_tmp.get(7),detail_storage_tmp.get(8),detail_storage_tmp.get(9),detail_storage_tmp.get(10),detail_storage_tmp.get(11),detail_storage_tmp.get(12),detail_storage_tmp.get(13)));
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
    public void updateCarParkDetails(String id, String type, String value){
        new UpdateAsyncTask(carpark_dao, id, type, value).execute();
        System.out.println("Result: Executed");
    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Void>{
        private CarParkDetailsDao dao;
        private String type;
        private String value;
        private String id;

        public UpdateAsyncTask(CarParkDetailsDao carpark_dao, String id, String type, String value) {
            dao = carpark_dao;
            this.id = id;
            this.type = type;
            this.value = value;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            CarParkDetails cpd = dao.getCarParkDetailsById(id).get(0);
            System.out.println("Result: " + cpd.toString());

            /**
             *          : which represents {id : [0 address,
             *                                    1 car_park_type,
             *                                    2 type_of_parking_system,
             *                                    3 short_term_parking,
             *                                    4 free_parking,
             *                                    5 night_parking
             *                                    6 is_bookmarked])
             */
            switch (type){
                case "id":
                    cpd.setId(value);
                    break;
                case "longitude":
                    cpd.setLongitude(value);
                    break;
                case "latitude":
                    cpd.setLatitude(value);
                    break;
                case "address":
                    cpd.setAddress(value);
                    break;
                case "car_park_type":
                    cpd.setCar_park_type(value);
                    break;
                case "short_term_parking":
                    cpd.setShort_term_parking(value);
                    break;
                case "free_parking":
                    cpd.setFree_parking(value);
                    break;
                case "night_parking":
                    cpd.setNight_parking(value);
                    break;
                case "is_bookmarked":
                    cpd.setIs_bookmarked(value);
                    break;
                default:break;
            }
            dao.updateCarParkDetails(cpd);
            System.out.println("Result: Upadted!");

            return null;
        }

    }

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
//            System.out.println("Result" + ": " + dao.getCarParkDetailsById(id).get(0));
            return dao.getCarParkDetailsById(id);
        }

        @Override
        protected void onPostExecute(List<CarParkDetails> carParkDetails) {
            super.onPostExecute(carParkDetails);
            delegate.queryFinish(carParkDetails);
        }
    }

    // getAll
    public void getAllCarParkDetails(AsyncResponse response){
        new getAllAsyncTask(response, carpark_dao).execute();
    }

    static class getAllAsyncTask extends AsyncTask<Void, Void, List<CarParkDetails>>{

        public AsyncResponse delegate = null;

        private CarParkDetailsDao dao;
        private String id;

        public getAllAsyncTask(AsyncResponse delegate, CarParkDetailsDao cp_dao){
            dao = cp_dao;
            this.id = id;
            this.delegate = delegate;
        }

        @Override
        protected List<CarParkDetails> doInBackground(Void... voids) {
//            System.out.println("Result" + ": " + dao.getCarParkDetailsById(id).get(0));
            return dao.getAllCarparkDetails();
        }

        @Override
        protected void onPostExecute(List<CarParkDetails> carParkDetails) {
            super.onPostExecute(carParkDetails);
            delegate.queryFinish(carParkDetails);
        }
    }

}

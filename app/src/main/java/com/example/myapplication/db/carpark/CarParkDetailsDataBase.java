package com.example.myapplication.db.carpark;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Database(entities = {CarParkDetails.class}, version = 1)
public abstract class CarParkDetailsDataBase extends RoomDatabase {
    // expose DAO
    public abstract CarParkDetailsDao getCarParkDetailsDao();

    // instance mode
    private static CarParkDetailsDataBase INSTANCE;

    private static Context db_context;
    public synchronized static CarParkDetailsDataBase getInstance(Context context){
        db_context = context;
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CarParkDetailsDataBase.class, "CarParkDetail_DataBase").addCallback(roomCallback).build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitializedDBAsyncTask(INSTANCE).execute();
        }
    };

    private static class InitializedDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private CarParkDetailsDao dao;

        private InitializedDBAsyncTask(CarParkDetailsDataBase db){
            dao = db.getCarParkDetailsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HashMap<String, ArrayList<String>> hm = null;
            try {
                hm = CSVCarParkDetail.getDetailInfo(db_context);
                ArrayList<String> detail_storage_tmp;
                for (String key_ : hm.keySet()){
                    detail_storage_tmp = hm.get(key_);
                    dao.insertCarParkDetails(new CarParkDetails(key_, detail_storage_tmp.get(0), detail_storage_tmp.get(1), detail_storage_tmp.get(2), detail_storage_tmp.get(3), detail_storage_tmp.get(4), detail_storage_tmp.get(5),detail_storage_tmp.get(6),detail_storage_tmp.get(7),detail_storage_tmp.get(8)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    };
}

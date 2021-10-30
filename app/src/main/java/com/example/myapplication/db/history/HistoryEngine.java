package com.example.myapplication.db.history;

import android.content.Context;
import android.os.AsyncTask;

import com.example.myapplication.db.history.AsyncResponse;
import java.util.List;

public class HistoryEngine {
    public HistoryDao history_dao;

    public HistoryEngine(Context context){
        HistoryDatabase history_db = HistoryDatabase.getInstance(context);
        history_dao = history_db.getHistoryDao();
    }

    // insert
    public void insertHistory(History... histories){
        new InsertAsyncTask(history_dao).execute(histories);
    }


    static class InsertAsyncTask extends AsyncTask<History, Void, Void> {

        private HistoryDao dao;

        public InsertAsyncTask(HistoryDao his_dao){
            dao = his_dao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            dao.insertHistory(histories);
            return null;
        }
    }

    public void getAllHistory(AsyncResponse response){
        new GetAllHistoryAsyncTask(response, history_dao).execute();
    }

    static class GetAllHistoryAsyncTask extends AsyncTask<Void, Void, List<History>> {
        public AsyncResponse delegate = null;
        private HistoryDao dao;
        public GetAllHistoryAsyncTask(AsyncResponse delegate, HistoryDao history_dao) {
            dao = history_dao;
            this.delegate = delegate;
        }

        @Override
        protected List<History> doInBackground(Void... voids) {
            return dao.getAllHistory();
        }

        @Override
        protected void onPostExecute(List<History> histories) {
            super.onPostExecute(histories);
            delegate.queryFinish(histories);
        }
    }

    // Delete
    public void deleteHistory(History... histories){
        new deleteAsyncTask(history_dao).execute(histories);
    }

    private class deleteAsyncTask extends AsyncTask<History, Void, Void>{
        private HistoryDao dao;
        public deleteAsyncTask(HistoryDao history_dao) {
            dao = history_dao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            dao.delete(histories);
            return null;
        }
    }
}

package com.example.myapplication.db.bookmark;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.db.carpark.AsyncResponse;

import java.util.List;

public class BookmarkRepository {
    private BookmarkDao bookmark_dao;
    private LiveData<List<Bookmark>> bookmark_list;

    public BookmarkRepository(Application application) {
        BookmarkDatabase database = BookmarkDatabase.getDbInstance(application);
        bookmark_dao = database.getBookmarkDao();
        bookmark_list = bookmark_dao.getAll();
    }

    public void insert(Bookmark ... bookmark){
        new InsertBookmarkAsyncTask(bookmark_dao).execute(bookmark);
    }

    private class InsertBookmarkAsyncTask extends AsyncTask<Bookmark, Void, Void> {
        private BookmarkDao dao;
        public InsertBookmarkAsyncTask(BookmarkDao bookmark_dao) {
            this.dao = bookmark_dao;
        }

        @Override
        protected Void doInBackground(Bookmark... bookmarks) {
            dao.insertBookmark(bookmarks);
            return null;
        }
    }

    public LiveData<List<Bookmark>> getAllBookmarks(){
        return bookmark_list;
    }

    public void delete(Bookmark ... bookmarks){
        new DeleteBookmarkAsyncTask(bookmark_dao).execute(bookmarks);
    }


    private class DeleteBookmarkAsyncTask extends AsyncTask<Bookmark, Void, Void> {
        private BookmarkDao dao;
        public DeleteBookmarkAsyncTask(BookmarkDao bookmark_dao) {
            dao = bookmark_dao;
        }

        @Override
        protected Void doInBackground(Bookmark ... bookmarks) {
            dao.delete(bookmarks);
            return null;
        }
    }
}

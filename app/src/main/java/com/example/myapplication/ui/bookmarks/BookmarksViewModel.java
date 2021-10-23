package com.example.myapplication.ui.bookmarks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.db.bookmark.Bookmark;
import com.example.myapplication.db.bookmark.BookmarkDatabase;

import java.util.List;

public class BookmarksViewModel extends AndroidViewModel {
    //live data
    private MutableLiveData<List<Bookmark>> bookmark_list;
    private BookmarkDatabase db;

    public BookmarksViewModel(@NonNull Application application) {
        super(application);
        bookmark_list= new MutableLiveData<>();
        db=BookmarkDatabase.getDbInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Bookmark>> getBookmarkObserver(){
        return bookmark_list;
    }

    public void getAllBookmarkList(){
        List<Bookmark> bookmarkList=db.getBookmarkDao().getAll();

        if(bookmarkList.size()>0){
            bookmark_list.postValue(bookmarkList);
        }else{
            bookmark_list.postValue(null);
        }
    }

    public void insertBookmark(String carparkName, double log, double lat){
        Bookmark bookmark = new Bookmark();
        bookmark.nickname=carparkName;
        bookmark.longitude=log;
        bookmark.latitude=lat;
        db.getBookmarkDao().insertBookmark(bookmark);
        getAllBookmarkList();
    }

    public void deleteBookmark(Bookmark bm){
        db.getBookmarkDao().delete(bm);
        getAllBookmarkList();
    }

    // TODO: Implement the ViewModel

}
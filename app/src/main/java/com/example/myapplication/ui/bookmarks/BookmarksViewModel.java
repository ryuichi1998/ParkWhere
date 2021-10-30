package com.example.myapplication.ui.bookmarks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.db.bookmark.Bookmark;
import com.example.myapplication.db.bookmark.BookmarkDatabase;
import com.example.myapplication.db.bookmark.BookmarkRepository;

import java.util.List;

public class BookmarksViewModel extends AndroidViewModel {
    //live data
    private BookmarkRepository repository;
    private LiveData<List<Bookmark>> bookmark_list;
    private BookmarkDatabase db;

    public BookmarksViewModel(@NonNull Application application) {
        super(application);
        repository = new BookmarkRepository(application);
        bookmark_list = repository.getAllBookmarks();
    }

    public void insertBookmark(Bookmark ... bookmarks){
        repository.insert(bookmarks);
    }

    public void deleteBookmark(Bookmark ... bookmarks){
        repository.delete(bookmarks);
    }

    public void updateBookmark(Bookmark ... bookmarks){
        repository.update(bookmarks);
    }
    public LiveData<List<Bookmark>> getBookmark_list() {
        return bookmark_list;
    }
// TODO: Implement the ViewModel

}
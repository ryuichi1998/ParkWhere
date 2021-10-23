package com.example.myapplication.ui.bookmarks;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapplication.R;
import com.example.myapplication.db.bookmark.Bookmark;
import com.example.myapplication.db.bookmark.BookmarkDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class BookmarksFragment extends Fragment implements BookmarkAdapter.HandleBookmarkClick {

    private BookmarksViewModel viewModel;
    private View root;
    private RecyclerView recyclerView;
    private Button favRemoveBtn,addBtn;
    private TextView noBookmark;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected BookmarkAdapter mAdapter;
    //private Context context;
    public static BookmarkDatabase bookmarkDatabase;

    //private LayoutManagerType mCurrentLayoutManagerType;

    public static BookmarksFragment newInstance() {
        return new BookmarksFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remote server.
        initViewModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.bookmarks_fragment, container, false);
        root.setTag("BookmarkFragment");

        // BEGIN_INCLUDE(initializeRecyclerView)
        recyclerView=root.findViewById(R.id.bookmark_list);
        noBookmark=root.findViewById(R.id.noBookmark);
        addBtn= root.findViewById(R.id.addButton);

        mLayoutManager = new LinearLayoutManager(getActivity());
        setRecyclerViewLayoutManager();

        mAdapter = new BookmarkAdapter(this,this);
        // Set CustomAdapter as the adapter for RecyclerView.
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        //remove fav
        favRemoveBtn=root.findViewById(R.id.fav_remove_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "Carpark";
                viewModel.insertBookmark(name,1.55,2.55);
                mAdapter.notifyDataSetChanged();

            }
        });

        return root;
    }

    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable("layoutManager", LayoutManagerType.LINEAR_LAYOUT_MANAGER);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void removeBookmark(Bookmark bookmark) {
        viewModel.deleteBookmark(bookmark);

    }

    @Override
    public void itemClick(Bookmark bookmark) {

    }


    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }
    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(BookmarksViewModel.class);
        viewModel.getBookmarkObserver().observe(this,
                new Observer<List<Bookmark>>() {
                    @Override
                    public void onChanged(List<Bookmark> bookmarks) {
                        if(bookmarks==null){
                            noBookmark.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);

                        }else{
                            mAdapter.setBookmarkList(bookmarks);
                            recyclerView.setVisibility(View.VISIBLE);
                            noBookmark.setVisibility(View.GONE);
                        }
                    }
                });
    }


}
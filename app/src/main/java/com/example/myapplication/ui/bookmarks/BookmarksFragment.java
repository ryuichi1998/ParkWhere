package com.example.myapplication.ui.bookmarks;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapplication.R;
import com.example.myapplication.db.bookmark.Bookmark;
import com.example.myapplication.db.bookmark.BookmarkDatabase;
import com.example.myapplication.db.history.History;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class BookmarksFragment extends Fragment {

    private BookmarksViewModel bookmark_viewModel;
    private View root;
    private RecyclerView recyclerView;
    private Button favRemoveBtn,addBtn;
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.bookmarks_fragment, container, false);
        root.setTag("BookmarkFragment");

        // BEGIN_INCLUDE(initializeRecyclerView)
        recyclerView=root.findViewById(R.id.bookmark_recycle_view);
        favRemoveBtn=root.findViewById(R.id.fav_remove_btn);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        bookmark_viewModel = new ViewModelProvider(requireActivity()).get(BookmarksViewModel.class);

        bookmark_viewModel.getBookmark_list().observe(getViewLifecycleOwner(), new Observer<List<Bookmark>>() {
            @Override
            public void onChanged(List<Bookmark> bookmarks) {

                mAdapter = new BookmarkAdapter(getActivity().getApplicationContext(),bookmark_viewModel.getBookmark_list());

                // Set CustomAdapter as the adapter for RecyclerView.
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }
        });

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable("layoutManager", LayoutManagerType.LINEAR_LAYOUT_MANAGER);
        super.onSaveInstanceState(savedInstanceState);
    }

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            bookmark_viewModel.deleteBookmark(bookmark_viewModel.getBookmark_list().getValue().get(viewHolder.getAdapterPosition()));
            mAdapter.notifyDataSetChanged();
        }
    };

}
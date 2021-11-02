package com.example.myapplication.ui.bookmarks;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Bookmark;
import com.example.myapplication.db.bookmark.BookmarkDatabase;
import com.example.myapplication.retrofit.GetUrl;
import com.example.myapplication.utils.BookmarkAdapter;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class BookmarksFragment extends Fragment implements BookmarkAdapter.OnItemClickedListener {

    private Handler handler = new Handler();
    Runnable runnable;

    private BookmarksViewModel bookmark_viewModel;
    private View root;
    private RecyclerView recyclerView;
    private Button favRemoveBtn,addBtn;
    protected RecyclerView.LayoutManager mLayoutManager;
    public static BookmarkAdapter mAdapter;
    //private Context context;
    public static BookmarkDatabase bookmarkDatabase;

    public Activity activity;
    public static boolean first = true;
    //private LayoutManagerType mCurrentLayoutManagerType;

    String responseBody;
    HashMap<String, String> result_hash_map = new HashMap<>();

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

        activity = getActivity();

        // BEGIN_INCLUDE(initializeRecyclerView)
        recyclerView=root.findViewById(R.id.bookmark_recycle_view);
        favRemoveBtn=root.findViewById(R.id.fav_remove_btn);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        bookmark_viewModel = new ViewModelProvider(requireActivity()).get(BookmarksViewModel.class);

        BookmarkAdapter.OnItemClickedListener listener = this;
        bookmark_viewModel.getBookmark_list().observe(getViewLifecycleOwner(), new Observer<List<Bookmark>>() {
            @Override
            public void onChanged(List<Bookmark> bookmarks) {

                mAdapter = new BookmarkAdapter(getActivity().getApplicationContext(),bookmark_viewModel.getBookmark_list(), listener);

                // Set CustomAdapter as the adapter for RecyclerView.
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }
        });

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        if (first){
            first = false;
            handler.postDelayed(runnable = new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(runnable, 1000 * 50);   // every 5 minute
                    getAvailLots();
                }
            }, 0);
        }
        else{
            getAvailLots();
        }


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
            int position = viewHolder.getAdapterPosition();
            Bookmark removed_item = bookmark_viewModel.getBookmark_list().getValue().get(viewHolder.getAdapterPosition());
            bookmark_viewModel.deleteBookmark(removed_item);
            mAdapter.notifyDataSetChanged();

            Snackbar.make(recyclerView, removed_item.getNickname() , Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bookmark_viewModel.insertBookmark(removed_item);
                            mAdapter.notifyItemInserted(position);
                        }
                    }).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            new RecyclerViewSwipeDecorator.Builder(getActivity(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_forever_24)
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_forever_24)
                    .create()
                    .decorate();
        }
    };

    public void refresh_result() throws JSONException {
        responseBody = GetUrl.getJson(
                "https://api.data.gov.sg/v1/transport/carpark-availability");
        result_hash_map =  GetUrl.parse(responseBody);
    }

    public String[] getCarParkInfo(String id) throws JSONException {
        return GetUrl.getTotalLots(responseBody, result_hash_map, id);
    }

    public void getAvailLots(){
        new GetUrlAsyncTask().execute();
    }

    private class GetUrlAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                refresh_result();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(activity, "Available Lots Fetched", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void itemClicked(int position) throws JSONException {
        Bookmark current = bookmark_viewModel.getBookmark_list().getValue().get(position);
        String[] result = getCarParkInfo(current.getId());
        if (result == null){
            Toast.makeText(activity, "Location Information Unavailable" , Toast.LENGTH_SHORT).show();
            return;
        }
        current.setAvail_lots(result[1]);
        bookmark_viewModel.updateBookmark(current);
        mAdapter.notifyDataSetChanged();

        Toast.makeText(activity, "Available Slots: " + result[1] , Toast.LENGTH_SHORT).show();
    }
}
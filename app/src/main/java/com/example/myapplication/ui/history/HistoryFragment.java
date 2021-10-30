package com.example.myapplication.ui.history;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.db.history.AsyncResponse;
import com.example.myapplication.db.history.History;
import com.example.myapplication.db.history.HistoryEngine;
import com.example.myapplication.ui.tracking.TrackingFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HistoryFragment extends Fragment {
    private Activity main_activity;
    private View root;

    private HistoryViewModel mViewModel;
    private RecyclerView recyclerView;
    private TextView text_duration;
    private TextView carpark_name;

    private HistoryEngine history_engine;

    private TimerTask timer_task;

    private ArrayList<History> history_list;

    private HistoryItemAdapter itemAdapter;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.history_fragment, container, false);
        }

        main_activity = getActivity();

        recyclerView = root.findViewById(R.id.history_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        text_duration = root.findViewById(R.id.history_ongoing_duration);
        carpark_name = root.findViewById(R.id.history_ongoing_carpark_name);
        history_engine = new HistoryEngine(getContext());

        history_list = new ArrayList<History>();
        AsyncResponse response = new AsyncResponse() {
            @Override
            public void queryFinish(List<History> histories) {
                root.findViewById(R.id.history_loading_text).setVisibility(View.GONE);

                history_list.addAll(histories);
                itemAdapter = new HistoryItemAdapter(history_list, getContext());

                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(itemAdapter);
            }
        };

        // swipe motion
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        history_engine.getAllHistory(response);

        // timer for ongoing process
        Timer timer = new Timer();


        if (!TrackingFragment.is_in_progress){
            root.findViewById(R.id.history_ongoing_linear_layout).setVisibility(View.GONE);
            if (timer_task != null){
                timer_task.cancel();
            }

        }
        else{
            carpark_name.setText(TrackingFragment.selected_address);

            timer_task = new TimerTask() {
                @Override
                public void run() {
                    main_activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text_duration.setText(TrackingFragment.in_progress_time);
                        };
                    });
                }
            };

            timer.scheduleAtFixedRate(timer_task, 0, 1000);
        }
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        // TODO: Use the ViewModel
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (history_list != null){
                History removed_item = history_list.remove(viewHolder.getAdapterPosition());
                history_engine.deleteHistory(removed_item);
                itemAdapter.notifyDataSetChanged();
            }
        }
    };

}
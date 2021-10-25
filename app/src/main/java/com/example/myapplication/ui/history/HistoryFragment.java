package com.example.myapplication.ui.history;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.db.history.AsyncResponse;
import com.example.myapplication.db.history.History;
import com.example.myapplication.db.history.HistoryEngine;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private View root;

    private HistoryViewModel mViewModel;
    private RecyclerView recyclerView;

    private HistoryEngine history_engine;

    ArrayList<History> history_list;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.history_fragment, container, false);
        }

        recyclerView = root.findViewById(R.id.history_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        history_engine = new HistoryEngine(getContext());

        ArrayList<History> history_list = new ArrayList<History>();
        AsyncResponse response = new AsyncResponse() {
            @Override
            public void queryFinish(List<History> histories) {
                for (History each: histories){
                    history_list.add(each);
                }
                HistoryItemAdapter itemAdapter = new HistoryItemAdapter(history_list, getContext());

                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(itemAdapter);
            }
        };

        history_engine.getAllHistory(response);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        // TODO: Use the ViewModel
    }

}
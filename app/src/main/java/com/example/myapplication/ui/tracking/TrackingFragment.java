package com.example.myapplication.ui.tracking;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class TrackingFragment extends Fragment {
    private TrackingViewModel mViewModel;

    private View root;
    private Button btn_tracker;
    private TextView txtv3;


    public static TrackingFragment newInstance() {
        return new TrackingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.tracking_fragment, container, false);
        btn_tracker = root.findViewById(R.id.btn_tracker);
        txtv3 = root.findViewById(R.id.textView3);
        btn_tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtv3.setText("Hello");

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TrackingViewModel.class);
        // TODO: Use the ViewModel
    }

}
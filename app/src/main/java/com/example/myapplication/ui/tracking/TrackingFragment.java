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

public class TrackingFragment extends Fragment implements View.OnClickListener{
    private TrackingViewModel mViewModel;

    private View root;
    private Button btn_tracker;


    public static TrackingFragment newInstance() {
        return new TrackingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.tracking_fragment, container, false);
        btn_tracker = root.findViewById(R.id.btn_tracker);
        btn_tracker.setOnClickListener(this);   // Important to add this listener

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tracker:
                doSomething();
        }
    }

    private void doSomething() {
        TextView txtv3 = root.findViewById(R.id.textView3);
        txtv3.setText("New Attempt");
    }
}
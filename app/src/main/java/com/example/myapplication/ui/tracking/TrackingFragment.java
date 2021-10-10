package com.example.myapplication.ui.tracking;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        if (root == null) {
            root = inflater.inflate(R.layout.tracking_fragment, container, false);
        }
        btn_tracker = root.findViewById(R.id.btn_tracker);
        btn_tracker.setOnClickListener(this);   // Important to add this listener

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tracker:
                changeFrame(new TrackerText());
        }
    }

    private void changeFrame(Fragment frag) {
        FragmentManager frag_mgr = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = frag_mgr.beginTransaction();
        transaction.replace(R.id.tracker_background, frag);
        transaction.commit();
    }
}
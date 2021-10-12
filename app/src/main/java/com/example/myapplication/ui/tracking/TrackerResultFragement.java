package com.example.myapplication.ui.tracking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Query;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.db.carpark.AsyncResponse;
import com.example.myapplication.db.carpark.CarParkDetails;
import com.example.myapplication.db.carpark.DBEngine;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackerResultFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackerResultFragement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;
    private TextView location_text;
    private DBEngine db_engine;

    public TrackerResultFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackerTimerFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackerResultFragement newInstance(String param1, String param2) {
        TrackerResultFragement fragment = new TrackerResultFragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.tracker_result_fragment, container, false);

        location_text = (TextView) root.findViewById(R.id.location_text);

        try {
            db_engine = new DBEngine(getActivity().getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateResult(root);

        return root;
    }

    private void updateResult(View view) {
        AsyncResponse query = new AsyncResponse() {
            @Override
            public void queryFinish(List<CarParkDetails> cp_detail) {
                TextView geo_text = (TextView) view.findViewById(R.id.location_text);
                TextView cost_text = (TextView) view.findViewById(R.id.cost_text);
                TextView time_text = (TextView) view.findViewById(R.id.timer_text);

                CarParkDetails cpd = cp_detail.get(0);

                geo_text.setText(cpd.getAddress());

//                String[] timer_result = TrackingFragment.getTimeResult();
//                time_text.setText(timer_result[0] + "h " + timer_result[1] + "m " + timer_result[2] + "s");
//
                if (cpd.getFree_parking().trim() == "YES")
                    cost_text.setText("Free Parking: $0");
                else
                    // TODO: TO change (Bookmark is just used to test)
                    cost_text.setText(cpd.getIs_bookmarked());

            }
        };


        //TODO: Change the defualt id to a dynamic value
        CarParkDetails cpd = db_engine.getCarParkDetailByID("HG38", query);

        // TODO: Remove this, it's jsut a test for updater
        db_engine.updateCarParkDetails("HG38", "is_bookmarked", "YES");

    }
}
package com.example.myapplication.ui.tracking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.db.carpark.AsyncResponse;
import com.example.myapplication.db.carpark.CarParkDetails;
import com.example.myapplication.db.carpark.DBEngine;
import com.example.myapplication.db.history.History;
import com.example.myapplication.db.history.HistoryEngine;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

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

    private ArrayList<String> central_area_list;

    private View root;
    private TextView location_text;
    private DBEngine db_engine;
    private HistoryEngine history_engine;

    private String id = null;

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

        id = TrackingFragment.selected_id;

        try {
            db_engine = new DBEngine(getActivity().getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        history_engine = new HistoryEngine(getActivity().getApplicationContext());

        updateResult(root);

        central_area_list = new ArrayList<>();
        central_area_list.add("ACB");
        central_area_list.add("BBB");
        central_area_list.add("BRB1");
        central_area_list.add("CY");
        central_area_list.add("DUXM");
        central_area_list.add("HLM");
        central_area_list.add("KAB");
        central_area_list.add("KAM");
        central_area_list.add("KAS");
        central_area_list.add("PRM");
        central_area_list.add("SLS");
        central_area_list.add("SR1");
        central_area_list.add("SR2");
        central_area_list.add("TPM");
        central_area_list.add("UCS");
        central_area_list.add("WCB");

        return root;
    }

    private void updateResult(View view) {
        AsyncResponse query = new AsyncResponse() {
            @Override
            public void queryFinish(List<CarParkDetails> cp_detail) {
                TextView location_text = (TextView) view.findViewById(R.id.location_text);
                TextView cost_text = (TextView) view.findViewById(R.id.cost_text);
                TextView time_text = (TextView) view.findViewById(R.id.duration_text);
                TextView geo_text = (TextView) view.findViewById(R.id.geo_position_text);

                CarParkDetails cpd = cp_detail.get(0);

                // set location
                location_text.setText(cpd.getAddress());

                String[] timer_result = TrackingFragment.getTimeResult();

                // set time part
                time_text.setText(timer_result[0] + "h " + timer_result[1] + "m " + timer_result[2] + "s");
                String cost = estimatePrice(timer_result[0], timer_result[1]);
//
                // set cost part
                cost_text.setText("$ " + cost);

                // set geo position
                geo_text.setText(cpd.getLongitude() + ", " + cpd.getLatitude());

                // insert the result to history database
                // TODO: change TODAY
                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                history_engine.insertHistory(new History(cpd.getAddress(), cpd.getId(), formattedDate, TrackingFragment.start_time, timer_result[0] + "h " + timer_result[1] + "m " + timer_result[2] + "s"));

            }
        };


        if (id == null){
            throw new NoSuchElementException("Address is not selected!");
        }

        //TODO: Change the defualt id to a dynamic value
        CarParkDetails cpd = db_engine.getCarParkDetailByID(id, query);

        // TODO: Remove this, it's jsut a test for updater
        db_engine.updateCarParkDetails(id, "is_bookmarked", "YES");

    }

    private String estimatePrice(String hr, String m) {
        int hour = Integer.parseInt(hr);
        int min = Integer.parseInt(m);

        double cost = 0.0;

        boolean central = false;
        for (String place: central_area_list){
            if (id.contains(place)){
                central = true;
                break;
            }
        }

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat week_day_df = new SimpleDateFormat("EEEE", Locale.getDefault());

        String week_day = week_day_df.format(c);

        if (central && !week_day.equals("Sunday") && !week_day.equals("Saturday")){
            cost = 2 * 1.2 * hour + 1.2 * min/2;
        }
        else {
            cost = 2 * 0.6 * hour + 0.6 * min/2;
        }

        return String.valueOf(cost);
    }
}
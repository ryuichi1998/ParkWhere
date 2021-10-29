package com.example.myapplication.ui.tracking;

import android.content.Context;
import android.view.Gravity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.db.carpark.AsyncResponse;
import com.example.myapplication.db.carpark.CarParkDetails;
import com.example.myapplication.db.carpark.DBEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TrackingFragment extends Fragment implements View.OnClickListener{
    private Context main_content;
    private static Double time = 0.0;
    private TrackingViewModel mViewModel;

    // components
    private View root;
    private Button start_stop_btn;
    private TextView timer_text;

    public static String selected_id = null;

    private AutoCompleteTextView location_auto_complete;
    private ArrayList<String> address_array;
    // key: address, value : id
    private HashMap<String, String> location_hashmap;

    private Timer timer;
    private TimerTask timer_task;
//    private Double time = 0.0;
    boolean timer_started = false;

    // private DBEngine db_engine;
    private DBEngine db_engine;

    public static TrackingFragment newInstance() {
        return new TrackingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.tracking_fragment, container, false);
        }

        main_content = getActivity().getApplicationContext();

        try {
            db_engine= new DBEngine(main_content);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        db_engine= MainActivity.getDb_engine();

        timer_text = (TextView) root.findViewById(R.id.timer_text);
        start_stop_btn = (Button) root.findViewById(R.id.start_stop_btn);
        location_auto_complete = (AutoCompleteTextView) root.findViewById(R.id.location_auto_complete_text);

        address_array = new ArrayList<String>();
        location_hashmap = new HashMap<String, String>();

//        address_array = MainActivity.getAddress_array();
//        location_hashmap = MainActivity.getLocation_hashmap();

        initializeAutoCompleteTextView();
        locationSelected();


        start_stop_btn.setOnClickListener(this);   // Important to add this listener
//        location_spinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        timer = new Timer();

        return root;
    }

    private void locationSelected() {
        location_auto_complete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                selected_id = location_hashmap.get((String) adapter.getItemAtPosition(position));

                //TODO: hide keyboard upon completion
            }
        });
    }

    private void initializeAutoCompleteTextView() {
        // TODO
        AsyncResponse query = new AsyncResponse() {
            @Override
            public void queryFinish(List<CarParkDetails> cp_detail) {
                for (CarParkDetails item : cp_detail){
                    location_hashmap.put(item.getAddress(), item.getId());
                    address_array.add(item.address);
                }

                ArrayAdapter adapter = new ArrayAdapter(main_content, android.R.layout.simple_list_item_1, address_array);
//                adapter.setDropDownViewResource(android.R.layout.sim);
                location_auto_complete.setAdapter(adapter);
            }
        };

        db_engine.getAllCarParkDetails(query);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_stop_btn:
                startClicked();
                break;
        }
    }

    public void startClicked() {
        // check whether user has already picked a location
        if (selected_id == null){
            Context context = getActivity().getApplicationContext();
            CharSequence text = "Please pick a location first";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
            toast.show();

            return;
        }

        if (!timer_started) {

            // initialize timer to 0
            time = 0.0;

            timer_started = true;
            start_stop_btn.setText("STOP");

            StartTimer();
        }
        else {
            timer_started = false;
            start_stop_btn.setText("START");

            timer_task.cancel();

            //TODO: Fix why the button still appears when fragemnt changes
            start_stop_btn.setVisibility(View.INVISIBLE);

            // change fragment from timer view to result view
            replaceFragement(new TrackerResultFragement());
        }
    }

    private void replaceFragement(Fragment frag) {
        FragmentManager frg_mgr = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = frg_mgr.beginTransaction();
        transaction.replace(R.id.tracker_timer_constrain, frag);
        transaction.commit();
    }

    private void StartTimer() {
        timer_task = new TimerTask() {
            @Override
            public void run() {
                time++;
                timer_text.setText(getTimerText());
            }
        };

        timer.scheduleAtFixedRate(timer_task, 0, 1000);

    }

    private String getTimerText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = (rounded % 86400) / 3600;


        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    public static String[] getTimeResult(){
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = (rounded % 86400) / 3600;

        return new String[]{String.valueOf(hours), String.valueOf(minutes), String.valueOf(seconds)};

    }

}
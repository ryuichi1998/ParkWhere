package com.example.myapplication.ui.tracking;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.db.carpark.AsyncResponse;
import com.example.myapplication.model.CarParkDetails;
import com.example.myapplication.repo.DBEngine;
import com.example.myapplication.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TrackingFragment extends Fragment implements View.OnClickListener{
    private Context main_content;
    private Activity main_activity;

    private static Double time = 0.0;
    private TrackingViewModel mViewModel;

    // components
    private View root;
    private Button start_stop_btn;
    private TextView timer_text;

    public static boolean is_in_progress = false;

    public static String selected_id = null;
    public static String selected_address = null;

    public static String start_time;

    private AutoCompleteTextView location_auto_complete;
    private ArrayList<String> address_array;
    // key: address, value : id
    private HashMap<String, String> location_hashmap;

    public static Timer timer;
    public static TimerTask timer_task;
    public static String in_progress_time;

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

        main_activity = getActivity();
        main_content = getActivity().getApplicationContext();

        db_engine = MainActivity.getDb_engine();

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

        // continue the timer
        continueTimer(is_in_progress);

        return root;
    }

    private void locationSelected() {
        location_auto_complete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                selected_address = (String) adapter.getItemAtPosition(position);
                selected_id = location_hashmap.get(selected_address);

                // hide keyboard
                location_auto_complete.clearFocus();
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(location_auto_complete.getWindowToken(), 0);
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
        if (selected_address == null){
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
            in_progress_time = "0";
            start_time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

            timer_started = true;
            is_in_progress = true;
            start_stop_btn.setText("STOP");
            location_auto_complete.setEnabled(false);

            StartTimer(timer_text, true);
        }
        else {
            selected_address = null;

            timer_started = false;
            is_in_progress = false;
            start_stop_btn.setText("START");
            location_auto_complete.setEnabled(true);

            timer_task.cancel();

            //TODO: Fix why the button still appears when fragemnt changes
            start_stop_btn.setVisibility(View.INVISIBLE);

            root.findViewById(R.id.timer_text).setVisibility(View.GONE);
            // change fragment from timer view to result view
            replaceFragement(new TrackerResultFragement());
        }
    }

    private void continueTimer(boolean in_progress){
        if (in_progress){
            timer_started = true;
            start_stop_btn.setText("STOP");
            location_auto_complete.setEnabled(false);
            location_auto_complete.setText(selected_address);
            StartTimer(timer_text, false);
        }
    }

    private void replaceFragement(Fragment frag) {
        FragmentManager frg_mgr = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = frg_mgr.beginTransaction();
        transaction.replace(R.id.tracker_timer_constrain, frag);
        transaction.commit();
    }

    public void StartTimer(TextView textView, boolean is_first) {
        timer_task = new TimerTask() {
            @Override
            public void run() {
                if (is_first){
                    time++;
                }
                textView.setText(in_progress_time = getTimerText());
            }
        };

        timer.scheduleAtFixedRate(timer_task, 0, 1000);

    }

    public static String getTimerText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = (rounded % 86400) / 3600;


        return formatTime(seconds, minutes, hours);
    }

    public static String formatTime(int seconds, int minutes, int hours) {
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
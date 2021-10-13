package com.example.myapplication.ui.tracking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.carpark.CarParkDetails;
import com.example.myapplication.db.carpark.CarParkDetailsDao;
import com.example.myapplication.db.carpark.CarParkDetailsDataBase;
import com.example.myapplication.db.carpark.DBEngine;
import com.example.myapplication.db.user.User;
import com.example.myapplication.db.user.UserDao;
import com.example.myapplication.db.user.UserDataBase;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TrackingFragment extends Fragment implements View.OnClickListener{
    private static Double time = 0.0;
    private TrackingViewModel mViewModel;

    private View root;
    private Button start_stop_btn;
    private TextView timer_text;

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

        timer_text = (TextView) root.findViewById(R.id.timer_text);
        start_stop_btn = (Button) root.findViewById(R.id.start_stop_btn);
        start_stop_btn.setOnClickListener(this);   // Important to add this listener

        timer = new Timer();

        try {
            db_engine= new DBEngine(getActivity().getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_stop_btn:
                startClicked();
        }
    }

    public void startClicked() {
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
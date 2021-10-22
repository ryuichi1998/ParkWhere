package com.example.myapplication;

import android.os.Bundle;

import com.example.myapplication.db.carpark.DBEngine;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    // used as a interface for managing or accessing the Database (HDB-CarPark-Detail database)
    private static DBEngine db_engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.trackingFragment, R.id.historyFragment, R.id.bookmarksFragment, R.id.profileFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        //setting the Bottom navigation visibiliy
        try {
            DBEngine db_engine = new DBEngine(getApplicationContext());
            db_engine.initializeDB(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DBEngine getDb_engine() {
        return db_engine;
    }

    @Override
    public void onBackPressed() {
    }
}
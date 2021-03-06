package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.db.carpark.AsyncResponse;
import com.example.myapplication.model.CarParkDetails;
import com.example.myapplication.repo.DBEngine;
import com.example.myapplication.ui.bookmarks.BookmarksFragment;
import com.example.myapplication.ui.history.HistoryFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.profile.ProfileFragment;
import com.example.myapplication.ui.tracking.TrackingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.util.List;

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
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.trackingFragment:
                        selectedFragment = new TrackingFragment();
                        break;
                    case R.id.historyFragment:
                        selectedFragment = new HistoryFragment();
                        break;
                    case R.id.bookmarksFragment:
                        selectedFragment = new BookmarksFragment();
                        break;
                    case R.id.profileFragment:
                        selectedFragment = new ProfileFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commitNowAllowingStateLoss();
                return true;
            }
        });


        try {
            db_engine = new DBEngine(getApplication());
            AsyncResponse dummy = new AsyncResponse() {
                @Override
                public void queryFinish(List<CarParkDetails> cp_detail) {
                    return;
                }
            };
            db_engine.getCarParkDetailByID("", dummy);
            // TODO: DEBUG PURPOSE,TO REMOVE
//            db_engine.initializeDB(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
    }
    public static DBEngine getDb_engine() {
        return db_engine;
    }
}
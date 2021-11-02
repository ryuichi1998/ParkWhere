package com.example.myapplication.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.DataMallCarParkAvailability;
import com.example.myapplication.repo.DataMallRepo;
import com.example.myapplication.retrofit.DataMallApiInterface;
import com.example.myapplication.retrofit.RetrofitUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private DataMallRepo dataMallRepo;

    // Constructor
    public HomeViewModel() {
        dataMallRepo = DataMallRepo.getInstance();
    }

    public LiveData<List<DataMallCarParkAvailability>> getAvailableLots() {
        return dataMallRepo.getLots();
    }

    public class SavedStateViewModel extends ViewModel {
        private SavedStateHandle state;

        public SavedStateViewModel(SavedStateHandle savedStateHandle) {
            state = savedStateHandle;
        }
    }

}
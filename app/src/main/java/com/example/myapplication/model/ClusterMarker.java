package com.example.myapplication.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarker implements ClusterItem {

    private LatLng position;
    private String title;
    private String snippet;
    private DataMallCarParkAvailability dataMallCarParkAvailability;

    public ClusterMarker(LatLng position, String title, String snippet, DataMallCarParkAvailability dataMallCarParkAvailability) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.dataMallCarParkAvailability = dataMallCarParkAvailability;
    }

    public ClusterMarker() {
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return position;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return snippet;
    }

    public DataMallCarParkAvailability getDataMallCarParkAvailability() {
        return dataMallCarParkAvailability;
    }
}
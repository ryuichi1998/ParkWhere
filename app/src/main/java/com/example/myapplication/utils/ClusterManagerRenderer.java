package com.example.myapplication.utils;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.model.ClusterMarker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.BubbleIconFactory;
import com.google.maps.android.ui.IconGenerator;

public class ClusterManagerRenderer extends DefaultClusterRenderer<ClusterMarker> {

    private Context mContex;

    public ClusterManagerRenderer(Context context, GoogleMap map, ClusterManager<ClusterMarker> clusterManager) {
        super(context, map, clusterManager);
        mContex = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(@NonNull ClusterMarker item, @NonNull MarkerOptions markerOptions) {
        int availLots = item.getDataMallCarParkAvailability().getAvailableLots();
        String lots = String.valueOf(availLots);
        IconGenerator iconGenerator = new IconGenerator(mContex);

        int free = Color.parseColor("#34cb8b");

        iconGenerator.setStyle(IconGenerator.STYLE_DEFAULT);
        if (availLots == 0) {
            iconGenerator.setStyle(IconGenerator.STYLE_RED);
        } else if (availLots <= 50) {
            iconGenerator.setStyle(IconGenerator.STYLE_ORANGE);
        } else {
            iconGenerator.setStyle(IconGenerator.STYLE_GREEN);
            iconGenerator.setColor(free);
        }

        Bitmap icon = iconGenerator.makeIcon(lots);
        BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

}

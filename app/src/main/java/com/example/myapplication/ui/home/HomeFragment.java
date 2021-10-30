package com.example.myapplication.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.db.carpark.CarParkDetailsDao;
import com.example.myapplication.db.carpark.CarParkDetailsDataBase;
import com.example.myapplication.model.ClusterMarker;
import com.example.myapplication.model.DataMallCarParkAvailability;
import com.example.myapplication.model.DataMallCarParkAvailabilityInfo;
import com.example.myapplication.repo.DataMallRepo;
import com.example.myapplication.retrofit.DataMallApiInterface;
import com.example.myapplication.retrofit.RetrofitUtil;
import com.example.myapplication.utils.ClusterManagerRenderer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, EasyPermissions.PermissionCallbacks {

    private GoogleMap mMap;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    // Google's API for location services
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    private int GPS_REQUEST_CODE = 9001;

    CarParkDetailsDataBase carparkDatabase;
    CarParkDetailsDao carparkDao;
    Button buttonSearch;
    TextView textView;
    FloatingActionButton fab;

    MapView mapView;
    boolean isPermissionGranted;

    private DataMallRepo dataMallRepo;

    EditText inputSearch;
    private ClusterManager mClusterManager;
    private ClusterManagerRenderer mClusterMangerRenderer;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();

    private ClusterManager<ClusterMarker> clusterManager;
    private List<DataMallCarParkAvailability> carParkAvailabilities;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fab = root.findViewById(R.id.floatingActionButton);
        mapView = binding.mapView;
        inputSearch = binding.inputSearch;
        initMap(savedInstanceState);
        observeAnyChange();
        serchInit();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
//                observeAnyChange();
            }
        });

        return root;
    }

    // Observer data change
    private void observeAnyChange() {
        homeViewModel.getAvailableLots().observe(getActivity(), new Observer<List<DataMallCarParkAvailability>>() {
            @Override
            public void onChanged(List<DataMallCarParkAvailability> dataMallCarParkAvailabilities) {
                // Observing for any data change
                setUpClusterer(dataMallCarParkAvailabilities);
//                for (DataMallCarParkAvailability carParkAvailability : dataMallCarParkAvailabilities) {
//                    String[] latlong =  carParkAvailability.getLocation().split(("[\\s,]+"));
//                    double latitude = Double.parseDouble(latlong[0]);
//                    double longitude = Double.parseDouble(latlong[1]);
//
//                    LatLng location = new LatLng(latitude, longitude);
//                    Log.v("LOCATION", "locations: " + location);
//                    Log.v("Tag", "the available lots: " + carParkAvailability.getAvailableLots());
////                    Log.v("Tag", "the Developemnt: " + carParkAvailability.getLocation());
//                    MarkerOptions marker = new MarkerOptions().position(location)
//                            .title(carParkAvailability.getDevelopment())
//                            .snippet(carParkAvailability.getAvailableLots().toString());
//                    mMap.addMarker(marker);
//                }
            }
        });
    }

    private void setUpClusterer(List<DataMallCarParkAvailability> dataMallCarParkAvailabilities) {
        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        if (clusterManager == null) {
            clusterManager = new ClusterManager<ClusterMarker>(getActivity().getApplicationContext(), mMap);
        }
        if (mClusterMangerRenderer == null) {
            mClusterMangerRenderer = new ClusterManagerRenderer(
                    getActivity().getApplicationContext(),
                    mMap,
                    clusterManager);

            if (mMap != null) {
                clusterManager.setRenderer(mClusterMangerRenderer);
                mMap.setOnCameraIdleListener(clusterManager);
                mMap.setOnMarkerClickListener(clusterManager);
            }
        }

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.

        // Add cluster items (markers) to the cluster manager.
        addItems(dataMallCarParkAvailabilities);
    }

    private void serchInit() {
        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    // execute method for searching
                    geoLocate();
                }
                return false;
            }
        });
    }

    private void geoLocate() {
        Log.v("geoLocate", "geolocation");
        String saerchString = inputSearch.getText().toString();
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(saerchString, 1);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            Log.v("geoLocate: found location", address.toString());
//            Toast.makeText(getActivity(), address.toString(), Toast.LENGTH_SHORT);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
            mMap.moveCamera(cameraUpdate);
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(address.getAddressLine(0));
            mMap.addMarker(options);
        }
    }

    private void addItems(List<DataMallCarParkAvailability> dataMallCarParkAvailabilities) {

        // Add ten cluster items in close proximity, for purposes of this example.
        for (DataMallCarParkAvailability carParkAvailability : dataMallCarParkAvailabilities) {
            Log.v("Add Markers", "TEST");
            try {
                String snippet = carParkAvailability.getAvailableLots().toString();
                String title = carParkAvailability.getDevelopment();
                String[] latlong =  carParkAvailability.getLocation().split(("[\\s,]+"));
                double latitude = Double.parseDouble(latlong[0]);
                double longitude = Double.parseDouble(latlong[1]);
                LatLng location = new LatLng(latitude, longitude);
                Log.v("latlng", location.toString());
                Log.v("Tilte ", title);
                Log.v("snippet", snippet);

                ClusterMarker newClusterMarker = new ClusterMarker(
                        location,
                        title,
                        snippet,
                        carParkAvailability
                );
                clusterManager.addItem(newClusterMarker);
            } catch (Exception e) {
                Log.v("Add map markers", e.getMessage());
            }
            clusterManager.cluster();
        }
    }


    private void initMap(Bundle savedInstanceState) {
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        if (hasLocationPermission()) {
            getCurrentLocation();
        } else {
            requestLocationPermission();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }

    private void getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                gotoLocation(location.getLatitude(), location.getLongitude());
            }
        });
    }

    private void gotoLocation(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        mMap.moveCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


    private Boolean hasLocationPermission() {
        Boolean hasPermission = EasyPermissions.hasPermissions(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        return hasPermission;
    }

    private void requestLocationPermission() {
        EasyPermissions.requestPermissions(this, "This application requires Location Permission", 1, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
        getCurrentLocation();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, Collections.singletonList(Manifest.permission.ACCESS_FINE_LOCATION))) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

//    public void getDataMallAvailable() {
//        DataMallApiInterface apiInterface = RetrofitUtil.getRetrofitClient().create(DataMallApiInterface.class);
//        Call<DataMallCarParkAvailabilityInfo> call = apiInterface.getDataMallCarParkAvailability("3EK93kn7S5GPNDFR1ZNYGw==");
//        call.enqueue(new Callback<DataMallCarParkAvailabilityInfo>() {
//            @Override
//            public void onResponse(Call<DataMallCarParkAvailabilityInfo> call, Response<DataMallCarParkAvailabilityInfo> response) {
//                Log.d("Success", response.body().toString());
//                if (response.code() == 200) {
//                    Log.v("Tag", "the responnse" +response.body().toString());
//
//                    List<DataMallCarParkAvailability> carParkAvailabilities = new ArrayList<>(response.body().getAvailabilities());
//
//                    for (DataMallCarParkAvailability carParkAvailability: carParkAvailabilities) {
//                        Log.v("Tag", "the available lots: " + carParkAvailability.getAvailableLots());
//                        Log.v("Tag", "the Developemnt: " + carParkAvailability.getDevelopment());
//                    }
//
//                } else {
//                    try {
//                        Log.v("Tag", "Error" + response.errorBody().toString());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataMallCarParkAvailabilityInfo> call, Throwable t) {
//                String message = t.getMessage();
//                Log.d("failure", message);
//            }
//        });
//
//    }
}
//    private boolean isGPSEnabled() {
//
//        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        boolean isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        if (isEnabled) {
//            return true;
//        } else {
//
//            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
//                    .setTitle("GPS Permission")
//                    .setMessage("GPS is required for this app to work. Please enable GPS")
//                    .setPositiveButton("Yes", (((dialogInterface, i) -> {
//                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(intent);
//                    })))
//                    .setCancelable(false)
//                    .show();
//        }
//        return false;
//    }


//    private void checkPermission() {
//        Dexter.withContext(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
//            @Override
//            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                Toast.makeText(getActivity().getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
//                isPermissionGranted = true;
//            }
//
//            @Override
//            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri = Uri.fromParts("package", getActivity().getApplicationContext().getPackageName(), "");
//                intent.setData(uri);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                permissionToken.continuePermissionRequest();
//            }
//        }).check();
//    }

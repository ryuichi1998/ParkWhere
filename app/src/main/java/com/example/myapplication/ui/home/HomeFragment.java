package com.example.myapplication.ui.home;

import android.Manifest;
import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.example.myapplication.db.carpark.AsyncResponse;
import com.example.myapplication.model.Bookmark;
import com.example.myapplication.model.CarParkDetails;
import com.example.myapplication.db.carpark.CarParkDetailsDao;
import com.example.myapplication.db.carpark.CarParkDetailsDataBase;
import com.example.myapplication.repo.DBEngine;
import com.example.myapplication.model.ClusterMarker;
import com.example.myapplication.model.DataMallCarParkAvailability;
import com.example.myapplication.repo.DataMallRepo;
import com.example.myapplication.ui.bookmarks.BookmarksViewModel;
import com.example.myapplication.utils.ClusterManagerRenderer;
import com.example.myapplication.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class HomeFragment extends Fragment implements OnMapReadyCallback, EasyPermissions.PermissionCallbacks{

    private GoogleMap mMap;
    private HomeViewModel homeViewModel;
    private BookmarksViewModel bookmarksViewModel;
    private FragmentHomeBinding binding;
    private Activity main_activity;
    private HomeViewModel.SavedStateViewModel vm;
    // Google's API for location services
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    private int GPS_REQUEST_CODE = 9001;
    DBEngine dbEngine;

    CarParkDetailsDataBase carparkDatabase;
    CarParkDetailsDao carparkDao;
    Button buttonSearch;
    ImageButton add_bookmark_btn;
    TextView textView;
    FloatingActionButton fab;

    MapView mapView;
    boolean isPermissionGranted;

    private DataMallRepo dataMallRepo;

    private CarParkDetails carParkDetails;

    EditText inputSearch;
    TextView cpAddr;
    TextView lots;
    TextView weekdayR1;
    TextView weekdayR2;
    TextView satRate;
    TextView sunRate;
    TextView weekdayT1;
    TextView weekdayT2;
    TextView satT;
    TextView sunT;
    private ClusterManager mClusterManager;
    private ClusterManagerRenderer mClusterMangerRenderer;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();

    private ClusterManager<ClusterMarker> clusterManager;
    private List<DataMallCarParkAvailability> carParkAvailabilities;
    private LinearLayout carParkDetailLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        vm = new ViewModelProvider(this).get(HomeViewModel.SavedStateViewModel.class);
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        bookmarksViewModel = new ViewModelProvider(requireActivity()).get(BookmarksViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        fab = root.findViewById(R.id.floatingActionButton);
        add_bookmark_btn = root.findViewById(R.id.home_add_bookmark_btn);
        mapView = binding.mapView;
        inputSearch = binding.inputSearch;
        carParkDetailLayout = binding.popUpLayout;
//        initEditText(root);
        dbEngine = MainActivity.getDb_engine();
        if (mMap == null) {
            initMap(savedInstanceState);
        }
        initEditText(root);
        serchInit();

        try {
            dbEngine = new DBEngine(getActivity());
            AsyncResponse dummy = new AsyncResponse() {
                @Override
                public void queryFinish(List<CarParkDetails> cp_detail) {
                    return;
                }
            };
            dbEngine.getCarParkDetailByID("", dummy);
            // TODO: DEBUG PURPOSE,TO REMOVE
//            db_engine.initializeDB(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getCurrentLocation();
//                getCarParkDetByAddress("BLK 45/50/51 SIMS DRIVE");
//            }
//        });

        add_bookmark_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean[] flag = {true};
                Bookmark bm = new Bookmark(cpAddr.getText().toString(), "");
                bookmarksViewModel.getBookmark_list().observe(getViewLifecycleOwner(), new Observer<List<Bookmark>>() {
                    @Override
                    public void onChanged(List<Bookmark> bookmarks) {
                        if (flag[0]){
                            for (Bookmark each : bookmarksViewModel.getBookmark_list().getValue()){
                                // if contains same name then delete this
                                if (each.getNickname().equals(cpAddr.getText().toString())){
                                    bookmarksViewModel.deleteBookmark(each);
                                    Toast.makeText(getActivity(), "Removed from Bookmark", Toast.LENGTH_SHORT).show();
                                    add_bookmark_btn.setBackground(getResources().getDrawable(R.drawable.ic_heart_dark));
                                    add_bookmark_btn.setForeground(getResources().getDrawable(R.drawable.ic_heart_dark));
                                    flag[0] = false;
                                    return;
                                }
                            }
                            bookmarksViewModel.insertBookmark(bm);
                            Toast.makeText(getActivity(), "Added to Bookmark", Toast.LENGTH_SHORT).show();
                            add_bookmark_btn.setBackground(getResources().getDrawable(R.drawable.ic_heart_red));
                            add_bookmark_btn.setForeground(getResources().getDrawable(R.drawable.ic_heart_red));
                            flag[0] = false;
                        }
                    }

                });
            }
        });

        return root;
    }

    // Observer data change from api request
    private void observeAnyChange() {
        homeViewModel.getAvailableLots().observe(getActivity(), new Observer<List<DataMallCarParkAvailability>>() {
            @Override
            public void onChanged(List<DataMallCarParkAvailability> dataMallCarParkAvailabilities) {

//                if (dataMallCarParkAvailabilities == null) {
//                    return;
//                }
//
//                // Observing for any data change
////                setUpClusterer(dataMallCarParkAvailabilities);
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
                setUpClusterer(dataMallCarParkAvailabilities);
            }
        });
    }

    // init text fields
    private void initEditText(View view) {
        cpAddr = (TextView) view.findViewById(R.id.cp_addr);
        lots = (TextView) view.findViewById(R.id.textLots);
        weekdayR1 = (TextView) view.findViewById(R.id.textWeekdayR1);
        weekdayR2 = (TextView) view.findViewById(R.id.textWeekdayR2);
        satRate = (TextView) view.findViewById(R.id.textSatR);
        sunRate = (TextView) view.findViewById(R.id.textSunR);
        weekdayT1 = (TextView) view.findViewById(R.id.textWeekday1);
        weekdayT2 = (TextView) view.findViewById(R.id.textWeekday2);
        satT = (TextView) view.findViewById(R.id.textSat);
        sunT = (TextView) view.findViewById(R.id.textSun);
    }

    // sets up the cluster manager
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
                clusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<ClusterMarker>() {
                    @Override
                    public void onClusterItemInfoWindowClick(ClusterMarker item) {
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
                clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<ClusterMarker>() {
                    @Override
                    public boolean onClusterItemClick(ClusterMarker item) {
                        Log.d("cluster item","clicked");
                        carParkDetailLayout.setVisibility(View.VISIBLE);
                        cpAddr.setText(item.getTitle());
                        lots.setText(item.getDataMallCarParkAvailability().getAvailableLots().toString());
                        getCarParkDetByAddress(item.getTitle());

                        bookmarksViewModel.getBookmark_list().observe(getViewLifecycleOwner(), new Observer<List<Bookmark>>() {
                            @Override
                            public void onChanged(List<Bookmark> bookmarks) {
                                for (Bookmark each : bookmarksViewModel.getBookmark_list().getValue()) {
                                    // if contains same name then delete this
                                    if (each.getNickname().equals(cpAddr.getText().toString())) {
                                        add_bookmark_btn.setBackground(getResources().getDrawable(R.drawable.ic_heart_red));
                                        add_bookmark_btn.setForeground(getResources().getDrawable(R.drawable.ic_heart_red));
                                        return;
                                    }
                                }

                                add_bookmark_btn.setBackground(getResources().getDrawable(R.drawable.ic_heart_dark));
                                add_bookmark_btn.setForeground(getResources().getDrawable(R.drawable.ic_heart_dark));
                            }
                        });

                        return false;
                    }
                });
            }
        }

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.

        // Add cluster items (markers) to the cluster manager.
        addItems(dataMallCarParkAvailabilities);
    }

    // initialize search bar
    private void serchInit() {
        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    //hide soft keyboard
                    inputSearch.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputSearch.getWindowToken(), 0);

                    geoLocate();
                }
                return false;
            }
        });
    }

    // get carpark details with address
    private void getCarParkDetByAddress(String addr) {
        // TODO
        Log.v("ITEM", "TEST");
        AsyncResponse query = new AsyncResponse() {
            CarParkDetails cpd;
            @Override
            public void queryFinish(List<CarParkDetails> cp_detail) {
                for (CarParkDetails item : cp_detail){
                    if (item.getCategory().equals("HDB")) {
                        weekdayT1.setText("Type of Parking System:");
                        weekdayT2.setText("Short-Term Parking:");
                        satT.setText("Short-Term Parking Charges:");
                        sunT.setText("Free Parking:");
                        weekdayR1.setText(item.getCar_park_type());
                        weekdayR2.setText(item.getShort_term_parking());
                        satRate.setText("$0.60 per half-hour");
                        sunRate.setText(item.getFree_parking());

                    } else {
                        weekdayT1.setText("Weekdays Rate 1:");
                        weekdayT2.setText("Weekdays Rate 2:");
                        satT.setText("Sat Rate :");
                        sunT.setText("Sun/PH Rate:");
                        weekdayR1.setText(item.getWeekday_rate_1());
                        weekdayR2.setText(item.getWeekday_rate_2());
                        satRate.setText(item.getSat_rate());
                        sunRate.setText(item.getSun_rate());
                    }
                }
            }

        };
        dbEngine.getCarParkDetailsByAddress(addr, query);
    }

    // using google geolocate to set marker on the user input
    private void geoLocate() {
        Log.v("geoLocate", "geolocation");
        String searchString = inputSearch.getText().toString();
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
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

    // add carparks to maps using custom cluster manager
    private void addItems(List<DataMallCarParkAvailability> dataMallCarParkAvailabilities) {

        // Add ten cluster items in close proximity, for purposes of this example.
        for (DataMallCarParkAvailability carParkAvailability : dataMallCarParkAvailabilities) {
//            Log.v("Add Markers", "TEST");
            try {
                String snippet = carParkAvailability.getAvailableLots().toString();
                String title = carParkAvailability.getDevelopment();
                String[] latlong =  carParkAvailability.getLocation().split(("[\\s,]+"));
                double latitude = Double.parseDouble(latlong[0]);
                double longitude = Double.parseDouble(latlong[1]);
                LatLng location = new LatLng(latitude, longitude);
//                Log.v("latlng", location.toString());
//                Log.v("Tilte ", title);
//                Log.v("snippet", snippet);

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

    // Initialize map
    private void initMap(Bundle savedInstanceState) {
        if (mMap == null ) {
            mapView.getMapAsync(this);
            mapView.onCreate(savedInstanceState);
        }
        if (hasLocationPermission()) {
            getCurrentLocation();
        } else {
            requestLocationPermission();
        }
    }

    // map onready
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
        observeAnyChange();

        // on map click close car park details dialog
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                carParkDetailLayout.setVisibility(View.INVISIBLE);
            }
        });

        // close car park dialog on map drag
        googleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int reason) {
                if (reason ==REASON_GESTURE) {
                    carParkDetailLayout.setVisibility(View.INVISIBLE);
                }
            }


        });
    }



    // get user current location
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

    // moves camera to user location
    private void gotoLocation(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        mMap.moveCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    // Easypermissions checking if permission granted
    private Boolean hasLocationPermission() {
        Boolean hasPermission = EasyPermissions.hasPermissions(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        return hasPermission;
    }
    // Easypermissions request location
    private void requestLocationPermission() {
        EasyPermissions.requestPermissions(this, "This application requires Location Permission", 1, Manifest.permission.ACCESS_FINE_LOCATION);
    }
    // Easypermissions checking for location
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    // Easypermissions sets location and goes to current location when user grants permission
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
    // Easypermissions if permission denied
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, Collections.singletonList(Manifest.permission.ACCESS_FINE_LOCATION))) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mapView != null) {
            mapView.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mapView != null) {
            mapView.onStop();
        }
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mapView.onSaveInstanceState(savedInstanceState);
//    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
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

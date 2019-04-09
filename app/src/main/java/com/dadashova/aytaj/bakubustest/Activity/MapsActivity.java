package com.dadashova.aytaj.bakubustest.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.dadashova.aytaj.bakubustest.Containers.BusAdapter;
import com.dadashova.aytaj.bakubustest.Fragment.ListFragment;
import com.dadashova.aytaj.bakubustest.HttpClient.BusService;
import com.dadashova.aytaj.bakubustest.HttpClient.RetrofitInstance;
import com.dadashova.aytaj.bakubustest.POJOS.Bus;
import com.dadashova.aytaj.bakubustest.POJOS.ResponseModel;
import com.dadashova.aytaj.bakubustest.R;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, BusAdapter.SpinnerCallBack {

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private LatLng mUserLocation;
    private MarkerOptions mUserMarker;
    private List<MarkerOptions> mBuses;
    ResponseModel responseModel;
    Handler handler;
    Runnable runnable;
    private String mBusNum;
    private String mBusRoute;

    @BindView(R.id.text_title)
    TextView mToolbarTitle;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static final int MY_LOCATION_REQUEST_CODE = 1;
    public static final String FRAGMENT_TAG = "spinner";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getBusData();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_LOCATION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    mMap.setMyLocationEnabled(true);

                }

            }

        }

    }


    @Override
    protected void onResume() {

        super.onResume();

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {

                new RefreshLocations().execute();
                refreshLocations(mBusNum, mBusRoute);

                handler.postDelayed(runnable, 15000);
            }
        };

        handler.postDelayed(runnable, 15000);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mUserLocation = new LatLng(40.369611, 49.822672);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mUserLocation, 15));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mUserLocation));

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                mMap.setMyLocationEnabled(true);
            }
        } else {

            mMap.setMyLocationEnabled(true);

        }
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                //on click listener

                return false;
            }
        });



    }

    private void getBusData() {

        BusService busService = RetrofitInstance.getRetrofitInstance().create(BusService.class);

        Call<ResponseModel> call = busService.getBusData();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                Log.e("request", "-->" + response.code());

                responseModel = response.body();

                mBuses = new ArrayList();

                refreshLocations(mBusNum, mBusRoute);



            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {


                Log.e("fail", t.getMessage());


            }
        });


    }


    public void refreshLocations(String busNum, String busRoute) {

        mMap.clear();

        PolylineOptions polyline = new PolylineOptions();

        for (Bus bus : responseModel.getmBusList()) {
            if (bus.getmBus().getmDisplayRouteCode().equals(busNum) &&
                    bus.getmBus().getmRouteName().equals(busRoute)) {

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(bus.getmBus().getmLatitude()
                                .replace(",", ".")),
                                Double.parseDouble(bus.getmBus().getmLongitude()
                                        .replace(",", "."))))
                        .snippet(bus.getmBus().getmPlate())
                        .title(bus.getmBus().getmPrevStop())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus)));

               // The below code is for polyline.
             //  But the API doesn't give me proper information for polyline


//
//                polyline.add(new LatLng(Double.parseDouble(bus
//                        .getmBus()
//                        .getmLatitude()
//                        .replace(",", ".")),
//                        Double.parseDouble(bus
//                                .getmBus()
//                                .getmLongitude()
//                                .replace(",", "."))))
//                        .width(5)
//                        .color(Color.RED);
            }
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(bus.getmBus().
//                    getmLatitude()
//                    .replace(",", ".")),
//                    Double.parseDouble(bus.getmBus()
//                            .getmLongitude()
//                            .replace(",", "."))), 15));
        }



        mMap.addPolyline(polyline);


    }

    @Override
    public void onItemClicked(String busNum, String busRoute) {

        refreshLocations(busNum, busRoute);
        mBusNum = busNum;
        mBusRoute = busRoute;

        mToolbarTitle.setText(mBusRoute+"\n"+"No="+mBusNum);



        Fragment current = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (current != null) {
            getSupportFragmentManager().beginTransaction().remove(current).commit();
        }
    }

    public class RefreshLocations extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(final String... strings) {

            getBusData();

            return "";
        }
    }


    @OnClick(R.id.button_spinner)
    public void openSpinner(Button mSpinner) {

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new ListFragment(),
                        FRAGMENT_TAG)
                .addToBackStack("1")
                .commit();

    }



}
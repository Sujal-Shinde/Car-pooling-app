package com.example.cp.car_pooling_app;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Connect.Progressdialog;
import com.example.cp.car_pooling_app.Data.CreatedRides;
import com.example.cp.car_pooling_app.Data.SearchedData;
import com.example.cp.car_pooling_app.Data.userlocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class PassengerHome extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    Dialog dg;
    int resp;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;

    private LocationRequest locationRequest;

    private final int UPDATE_INTERVAL = 1000;
    private final int FASTEST_INTERVAL = 900;
    private final int REQ_PERMISSION = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_home);
        createGoogleApi();
        Button btnSearch=(Button)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

    }

    public void search()
    {
        final EditText editSrc = (EditText) findViewById(R.id.editPSource);
        final EditText editDest = (EditText) findViewById(R.id.editPDestination);
        final EditText editCost = (EditText) findViewById(R.id.editPCost);

        if (editSrc.getText().toString().equals("")||editDest.getText().toString().equals("")) {
            android.app.AlertDialog alert = new android.app.AlertDialog.Builder(PassengerHome.this).create();
            alert.setTitle("Enter All Details");
            alert.setMessage("All Fields Are Mandatory");
            alert.show();
        }
        else
        {
            final String src = editSrc.getText().toString().trim();
            final String dest = editDest.getText().toString().trim();
           // final String cost = editCost.getText().toString().trim();

            final ConnectionM conn = new ConnectionM();
            if (ConnectionM.checkNetworkAvailable(PassengerHome.this)) {
                Progressdialog dialog = new Progressdialog();
                dg = dialog.createDialog(PassengerHome.this);
                dg.show();
                Thread th1 = new Thread() {
                    @Override
                    public void run() {
                        try {
                            if (conn.searchRide(src,dest)) {
                                resp = 0;
                            } else {
                                resp = 1;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        hd.sendEmptyMessage(0);

                    }
                };
                th1.start();
            } else {
                Toast.makeText(PassengerHome.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:

                    ArrayList<String> rideId;
                    rideId= SearchedData.getRideId();
                    if(!rideId.isEmpty()) {
                        Intent intent = new Intent(PassengerHome.this, SearchedList.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(PassengerHome.this, "No record found", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 1:

                    Toast.makeText(getApplicationContext(), "Data not Found", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };



    private void createGoogleApi() {
        //Log.d(TAG, "createGoogleApi()");
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();
    }

    private boolean checkPermission() {
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQ_PERMISSION
        );
    }

    @Override
    public void onLocationChanged(Location location) {
        //Log.d(TAG, "onLocationChanged ["+location+"]");
        lastLocation = location;

        double latitude = location.getLatitude();
        double longitude=location.getLongitude();



        userlocation.setBook_lat(latitude);
        userlocation.setBook_lon(longitude);
        //writeActualLocation(location);
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Log.i(TAG, "onConnected()");
        getLastKnownLocation();
    }
    @Override
    public void onConnectionSuspended(int i) {
        //Log.w(TAG, "onConnectionSuspended()");
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Log.w(TAG, "onConnectionFailed()");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    getLastKnownLocation();

                } else {
                    // Permission denied
                    Toast.makeText(PassengerHome.this, "Failed !! \n Start GPS Service .....", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
    private void getLastKnownLocation() {
        //Log.d(TAG, "getLastKnownLocation()");
        if (checkPermission()) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {


                userlocation.setBook_lat(lastLocation.getLatitude());
                userlocation.setBook_lon(lastLocation.getLongitude());
                startLocationUpdates();
            } else {

                startLocationUpdates();
            }
        } else askPermission();
    }


    private void startLocationUpdates() {
        //Log.i(TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if (checkPermission())
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }


}

package com.example.cp.car_pooling_app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cp.car_pooling_app.Data.RideData;
import com.example.cp.car_pooling_app.Data.fillLocation;
import com.example.cp.car_pooling_app.Data.riderloc;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RiderMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googlemap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_map);




        final ActivityManager activitymanager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activitymanager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        if (supportsEs2) {
                /*if(latitude.equals(null)||longitude.equals(null))
                {
        			latitude="0.0";
        			longitude="0.0";
        		}*/
            if (googlemap == null) {
                MapFragment mFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.ridemap);
                mFrag.getMapAsync(this);
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        //DO WHATEVER YOU WANT WITH GOOGLEMAP
     /*   String latitude = SelectedArea.getLat();
        String longitude = SelectedArea.getLon();*/

        double latitude = riderloc.getR_lat();
        double longitude = riderloc.getR_lon();

        googlemap = map;
        /*  String name = SelectedArea.getAreaName();*/
        /*String name = selectedofferdata.getSeloffername();*/
        String name = RideData.getUsername();
        MarkerOptions marker = new MarkerOptions().position(new LatLng((latitude), (longitude))).title(name);
        CameraPosition camera = new CameraPosition.Builder().target(new LatLng((latitude), (longitude))).zoom(15).build();
        if (map != null) {
            //plot point
            googlemap.addMarker(marker);
            //focus camera to point
            googlemap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
        }
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }
}

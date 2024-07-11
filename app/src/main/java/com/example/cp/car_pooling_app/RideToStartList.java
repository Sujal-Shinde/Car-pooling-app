package com.example.cp.car_pooling_app;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Data.CreatedRides;
import com.example.cp.car_pooling_app.Data.SelectedPassenger;
import com.example.cp.car_pooling_app.Data.SelectedRide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RideToStartList extends AppCompatActivity {

    private ArrayList<String> id;
    private ArrayList<String> src;
    private ArrayList<String> dest;
    private ArrayList<String> date;
    private ArrayList<String> time;

    Dialog dg;
    int resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_to_start_list);

        id= CreatedRides.getRideId();
        src= CreatedRides.getSource();
        dest= CreatedRides.getDestinaion();
        date= CreatedRides.getDate();
        time= CreatedRides.getTime();
        rides_adapter adapter = new rides_adapter(RideToStartList.this, id, src, dest,date,time);
        ListView lst = (ListView) findViewById(R.id.listSRides);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               /* SelectedRide.setRideId(id.get(i));
                viewRequest();*/
                try {
                    String date_ = date.get(i);
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

                    String currDate = df.format(c.getTime());

                    Date newdate=df.parse(date_);
                    String rideDate=df.format(newdate);
                    if(currDate.equals(rideDate))
                    {
                        SelectedRide.setRideId(id.get(i));
                        Intent intent=new Intent(RideToStartList.this,UpdateLocation.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(RideToStartList.this, "Ride cannot be Start...", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {

                }
            }
        });


    }


}

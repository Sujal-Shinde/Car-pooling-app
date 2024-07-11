package com.example.cp.car_pooling_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Connect.Progressdialog;
import com.example.cp.car_pooling_app.Data.CreatedRides;
import com.example.cp.car_pooling_app.Data.RequestData;
import com.example.cp.car_pooling_app.Data.SelectedRide;

import java.util.ArrayList;

public class RiderList extends AppCompatActivity {

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
        setContentView(R.layout.activity_rider_list);

        id= CreatedRides.getRideId();
        src= CreatedRides.getSource();
        dest= CreatedRides.getDestinaion();
        date= CreatedRides.getDate();
        time= CreatedRides.getTime();
        rides_adapter adapter = new rides_adapter(RiderList.this, id, src, dest,date,time);
        ListView lst = (ListView) findViewById(R.id.listRiderList);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SelectedRide.setRideId(id.get(i));

                viewRequest();

            }
        });

    }

    public void viewRequest()
    {
        final ConnectionM conn = new ConnectionM();
        if (ConnectionM.checkNetworkAvailable(RiderList.this)) {
            Progressdialog dialog = new Progressdialog();
            dg = dialog.createDialog(RiderList.this);
            dg.show();

            Thread tthread = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.viewRequests()) {
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
            tthread.start();
        } else {
            Toast.makeText(RiderList.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:

                    ArrayList<String> req;
                    req= RequestData.getReqId();
                    if(!req.isEmpty()) {
                        Intent intent = new Intent(RiderList.this, PassengerReq.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(RiderList.this, "No request Found", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 1:

                    Toast.makeText(getApplicationContext(), "Data not Found", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

}

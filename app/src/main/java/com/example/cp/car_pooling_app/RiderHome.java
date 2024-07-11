package com.example.cp.car_pooling_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Connect.Progressdialog;
import com.example.cp.car_pooling_app.Data.CreatedRides;

import java.util.ArrayList;

public class RiderHome extends AppCompatActivity {

    Dialog dg;
    int resp;

    public static int st;
    //todo check status

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_home);

        Button btn = (Button) findViewById(R.id.btnCRider);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RiderHome.this, CreateRide.class);
                startActivity(intent);
            }
        });

        Button btnViewReq = (Button) findViewById(R.id.btnViewRiderReq);
        btnViewReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                st = 1;
                getRides();
            }
        });

        Button btnView = (Button) findViewById(R.id.btnViewStrtRide);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st = 2;
                getRides();
            }
        });

    }

    public void getRides() {
        final ConnectionM conn = new ConnectionM();
        if (ConnectionM.checkNetworkAvailable(RiderHome.this)) {
            Progressdialog dialog = new Progressdialog();
            dg = dialog.createDialog(RiderHome.this);
            dg.show();

            Thread tthread = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.viewCreatedRides()) {
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
            Toast.makeText(RiderHome.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:

                    ArrayList<String> rideId;
                    rideId = CreatedRides.getRideId();
                    if (!rideId.isEmpty()) {
                        if (st == 1) {
                            Intent intent = new Intent(RiderHome.this, RiderList.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(RiderHome.this, RideToStartList.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(RiderHome.this, "No rides Found", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case 1:

                    Toast.makeText(getApplicationContext(), "Data not Found", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

}

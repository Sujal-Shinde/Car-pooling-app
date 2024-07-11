package com.example.cp.car_pooling_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Connect.Progressdialog;
import com.example.cp.car_pooling_app.Data.LogUser;
import com.example.cp.car_pooling_app.Data.RideData;

public class RideDetails extends AppCompatActivity {

    Dialog dg;
    int resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);

        TextView txtName = (TextView) findViewById(R.id.txtVriderName);
        TextView txtPh = (TextView) findViewById(R.id.txtVRPhone);
        TextView txtGender = (TextView) findViewById(R.id.txtVRGender);
        TextView txtSource = (TextView) findViewById(R.id.txtVRSource);
        TextView txtDest = (TextView) findViewById(R.id.txtVRDestination);
        TextView txtCost = (TextView) findViewById(R.id.txtVRCost);
        TextView txtDate = (TextView) findViewById(R.id.txtVRDate);
        TextView txtTime = (TextView) findViewById(R.id.txtVRTime);

        txtName.setText(RideData.getUsername());
        txtGender.setText(RideData.getGender());
        txtPh.setText(RideData.getPhoneNo());
        txtCost.setText(RideData.getCost());
        txtDate.setText(RideData.getDate());
        txtTime.setText(RideData.getTime());
        txtSource.setText(RideData.getSrc());
        txtDest.setText(RideData.getDest());

        Button btnBook = (Button) findViewById(R.id.btnBook);
        Button btnmap=(Button)findViewById(R.id.btnridemap);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(LogUser.getUserId().equals(RideData.getUserId()))
                {
                    Toast.makeText(RideDetails.this, "Can't book your own Ride", Toast.LENGTH_SHORT).show();
                }
                else
                {

                bookRide();


                }
            }
        });

        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getriderlocation();


                /*Intent intent=new Intent(RideDetails.this,RiderMapActivity.class);
                startActivity(intent);*/
            }
        });


    }

    public void getriderlocation()
    {
        final ConnectionM conn = new ConnectionM();
        if (ConnectionM.checkNetworkAvailable(RideDetails.this)) {
            Progressdialog dialog = new Progressdialog();
            dg = dialog.createDialog(RideDetails.this);
            dg.show();
            Thread th1 = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.getRiderloc()) {
                            resp = 0;
                        } else {
                            resp = 1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hd1.sendEmptyMessage(0);

                }
            };
            th1.start();
        } else {
            Toast.makeText(RideDetails.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public void bookRide() {
        final ConnectionM conn = new ConnectionM();
        if (ConnectionM.checkNetworkAvailable(RideDetails.this)) {
            Progressdialog dialog = new Progressdialog();
            dg = dialog.createDialog(RideDetails.this);
            dg.show();
            Thread th1 = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.bookRide()) {
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
            Toast.makeText(RideDetails.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:
                    Intent intent=new Intent(RideDetails.this,Home.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Request Send Successfully", Toast.LENGTH_LONG).show();
                    break;

                case 1:
                    Toast.makeText(getApplicationContext(), "Try later", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    public Handler hd1 = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:
                    Intent intent=new Intent(RideDetails.this,RiderMapActivity.class);
                    startActivity(intent);
                   /* Toast.makeText(getApplicationContext(), "Request Send Successfully", Toast.LENGTH_LONG).show();*/
                    break;

                case 1:
                    Toast.makeText(getApplicationContext(), "Ride not started", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

}

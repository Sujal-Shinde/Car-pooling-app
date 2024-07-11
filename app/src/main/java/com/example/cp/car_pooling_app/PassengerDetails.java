package com.example.cp.car_pooling_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Connect.Progressdialog;
import com.example.cp.car_pooling_app.Data.RequestData;
import com.example.cp.car_pooling_app.Data.SelectedPassenger;
import com.example.cp.car_pooling_app.Data.SelectedRide;

import java.util.ArrayList;

public class PassengerDetails extends AppCompatActivity {

    Dialog dg;
    int resp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_details);

        TextView txtName = (TextView) findViewById(R.id.txtPName);
        TextView txtGender = (TextView) findViewById(R.id.txtPGender);
        TextView txtPh = (TextView) findViewById(R.id.txtPPhone);

        TextView txtSt = (TextView) findViewById(R.id.txtStatus);
        txtSt.setVisibility(View.GONE);

        final Button btnAcc = (Button) findViewById(R.id.btnPAccept);
        final Button btnRej = (Button) findViewById(R.id.btnPReject);
        final Button btnusermap=(Button)findViewById(R.id.btnmap);


        txtName.setText("Name : "+SelectedPassenger.getName());
        txtGender.setText("Gender :"+SelectedPassenger.getGender());
        txtPh.setText("Phone : "+SelectedPassenger.getPh());
        if (SelectedPassenger.getSt().equals("Accepted")) {
            txtSt.setVisibility(View.VISIBLE);
            txtSt.setText(SelectedPassenger.getSt());
            btnAcc.setVisibility(View.INVISIBLE);
            btnRej.setVisibility(View.INVISIBLE);
        } else if (SelectedPassenger.getSt().equals("Rejected")) {
            txtSt.setVisibility(View.VISIBLE);
            txtSt.setText(SelectedPassenger.getSt());
            btnAcc.setVisibility(View.INVISIBLE);
            btnRej.setVisibility(View.INVISIBLE);
        }

        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedPassenger.setSetResp("Accepted");
                reply();
            }
        });

        btnRej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedPassenger.setSetResp("Rejected");
                reply();
            }
        });

        btnusermap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getuserlocation();
            }
        });

    }

    public void reply() {
        final ConnectionM conn = new ConnectionM();
        if (ConnectionM.checkNetworkAvailable(PassengerDetails.this)) {
            Progressdialog dialog = new Progressdialog();
            dg = dialog.createDialog(PassengerDetails.this);
            dg.show();

            Thread tthread = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.respToReq()) {
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
            Toast.makeText(PassengerDetails.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:
                    if (SelectedRide.getRideStatus().equals("Booked")) {
                        ArrayList<String> req = RequestData.getStatus();
                        req.set(SelectedPassenger.getIndex(), "Accepted");
                        RequestData.setStatus(req);
                        Intent intent = new Intent(PassengerDetails.this, Home.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "request Accepted", Toast.LENGTH_LONG).show();
                    } else if (SelectedRide.getRideStatus().equals("Full")) {
                        Toast.makeText(getApplicationContext(), "Seats are full!!!", Toast.LENGTH_LONG).show();
                    }
                    break;

                case 1:

                    Toast.makeText(getApplicationContext(), "Try later", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };


    public void  getuserlocation()
    {
        final ConnectionM conn = new ConnectionM();
        if (ConnectionM.checkNetworkAvailable(PassengerDetails.this)) {
            Progressdialog dialog = new Progressdialog();
            dg = dialog.createDialog(PassengerDetails.this);
            dg.show();
            Thread th1 = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.getuserloc()) {
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
            Toast.makeText(PassengerDetails.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler hd1 = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:
                    Intent intent=new Intent(PassengerDetails.this,UserMapActivity.class);
                    startActivity(intent);
                    /* Toast.makeText(getApplicationContext(), "Request Send Successfully", Toast.LENGTH_LONG).show();*/
                    break;

                case 1:
                    Toast.makeText(getApplicationContext(), "some thing went wrong", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

}

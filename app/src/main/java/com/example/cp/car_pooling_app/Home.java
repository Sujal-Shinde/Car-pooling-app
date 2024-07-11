package com.example.cp.car_pooling_app;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Connect.Progressdialog;
import com.example.cp.car_pooling_app.Data.LogUser;

public class Home extends AppCompatActivity {

    Dialog dg;
    int resp;
    ImageView imgRider, imgPass, imgEmerg,imgHistory,imgLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgRider = (ImageView) findViewById(R.id.imgRider);
        imgPass = (ImageView) findViewById(R.id.imgPassenger);
        imgEmerg = (ImageView) findViewById(R.id.imgEmerg);
        imgHistory = (ImageView) findViewById(R.id.imgHistory);
        imgLogout = (ImageView) findViewById(R.id.imgLogout);




        imgRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, RiderHome.class);
                startActivity(intent);
            }
        });

        imgPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, PassengerMainActivity.class);
                startActivity(intent);
            }
        });

        imgEmerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getNumber();
            }
        });

        imgHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,HistoryList.class));
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    public void logout() {
        new android.support.v7.app.AlertDialog.Builder(Home.this)
                .setIcon(R.drawable.carpoollogo)
                .setTitle("")
                .setMessage("Are you sure you want logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Home.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void getNumber() {

        final ConnectionM conn = new ConnectionM();
        if (ConnectionM.checkNetworkAvailable(Home.this)) {
            Progressdialog dialog = new Progressdialog();
            dg = dialog.createDialog(Home.this);
            dg.show();
            Thread th1 = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.getEmergNum()) {
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
            Toast.makeText(Home.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
            //call();
        }
    }

//    public void call() {
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:+91" + LogUser.getEmergencyNum()));
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        startActivity(callIntent);
//    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:
                     startActivity(new Intent(getApplicationContext(),EmergencyList.class));
                    break;

                case 1:
                    //call();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed()
    {

    }
}

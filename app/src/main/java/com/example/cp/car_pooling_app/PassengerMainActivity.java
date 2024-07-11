package com.example.cp.car_pooling_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Connect.Progressdialog;
import com.example.cp.car_pooling_app.Data.CreatedRides;
import com.example.cp.car_pooling_app.Data.ReqAcceptlist;

import java.util.ArrayList;

public class PassengerMainActivity extends AppCompatActivity {

    Button btnsearch,btnaccept;
    Dialog dg;
    int resp;

    public static int st;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);

        btnsearch=(Button)findViewById(R.id.btnSRider);
        btnaccept=(Button)findViewById(R.id.btnaccept);


        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PassengerMainActivity.this,PassengerHome.class);
                startActivity(intent);
            }
        });

        btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(PassengerMainActivity.this,AcceptListActivity.class);
                startActivity(intent);


            }
        });

    }


}

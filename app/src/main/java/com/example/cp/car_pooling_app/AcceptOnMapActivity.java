package com.example.cp.car_pooling_app;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cp.car_pooling_app.Data.RideData;
import com.example.cp.car_pooling_app.Data.selacceptreq;

public class AcceptOnMapActivity extends AppCompatActivity {

    Dialog dg;
    int resp;

    Button btnmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_on_map);


        TextView txtName = (TextView) findViewById(R.id.txtVriderName);
        TextView txtPh = (TextView) findViewById(R.id.txtVRPhone);

        TextView txtSource = (TextView) findViewById(R.id.txtVRSource);
        TextView txtDest = (TextView) findViewById(R.id.txtVRDestination);
        TextView txtCost = (TextView) findViewById(R.id.txtVRCost);
        TextView txtDate = (TextView) findViewById(R.id.txtVRDate);
        TextView txtTime = (TextView) findViewById(R.id.txtVRTime);

        txtName.setText(selacceptreq.getFname());

        txtPh.setText(selacceptreq.getMobile());

        txtDate.setText(selacceptreq.getDate1());
        txtCost.setText(selacceptreq.getCost());

        txtTime.setText(selacceptreq.getTime());
        txtSource.setText(selacceptreq.getSource());
        txtDest.setText(selacceptreq.getDestination());


        btnmap=(Button)findViewById(R.id.btnridemap);
        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AcceptOnMapActivity.this,AcceptmapActivity.class);
                startActivity(intent);
            }
        });

    }
}

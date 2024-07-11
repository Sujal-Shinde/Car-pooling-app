package com.example.cp.car_pooling_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Data.Emergency;

public class EmergencyList extends AppCompatActivity {


    TextView txtPolice,txtNum1,txtNum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_list);


        txtPolice = (TextView) findViewById(R.id.txtCon1);
        txtNum1 = (TextView) findViewById(R.id.txtCon2);
        txtNum2 = (TextView) findViewById(R.id.txtCon3);


        txtPolice.setText("100");
        txtNum1.setText(Emergency.getNumber_1());
        txtNum2.setText(Emergency.getNumber_2());


        txtPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number =txtPolice.getText().toString().trim();
                call(number);
            }
        });

        txtNum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number =txtNum1.getText().toString().trim();
                call(number);
            }
        });



        txtNum2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number =txtNum2.getText().toString().trim();
                call(number);
            }
        });



       // Toast.makeText(getApplicationContext(),Emergency.getNumber_1(),Toast.LENGTH_LONG).show();

    }

        public void call(String number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+91" +number ));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"Please grant permission",Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(callIntent);
    }
}

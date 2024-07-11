package com.example.cp.car_pooling_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Data.fillLocation;

public class UpdateLocation extends AppCompatActivity {
    SharedPreferences.Editor myEditor;
    SharedPreferences myPreferences;
    public static Intent UpdateLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location);

        myPreferences=getSharedPreferences("LocationTracker", Context.MODE_PRIVATE);
        myEditor=myPreferences.edit();
        UpdateLocation=new Intent(this,LocationService.class);

        Switch btnlocation=(Switch)findViewById(R.id.switch1);
        btnlocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(((Switch)v).isChecked())
                {
                    fillLocation.setUpdateLocationStatus(1);
                    Toast.makeText(getApplicationContext(), "Location Update Service Is Started", Toast.LENGTH_SHORT).show();
                    startService(UpdateLocation);
                    myEditor.putBoolean("location", true);
                    myEditor.commit();
                }
                else
                {
                    fillLocation.setUpdateLocationStatus(0);
                    Toast.makeText(getApplicationContext(), "Location Update Service Is Stopped", Toast.LENGTH_SHORT).show();
                    stopService(UpdateLocation);
                    myEditor.putBoolean("location", false);
                    myEditor.commit();
                }
            }
        });
        if(myPreferences.getBoolean("location", false))
        {
            //Set default value
            btnlocation.setChecked(true);
        }
    }
}

package com.example.cp.car_pooling_app;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Connect.Progressdialog;
import com.example.cp.car_pooling_app.Data.CreateRideData;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateRide extends AppCompatActivity {

    Dialog dg;
    int resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);

        Button btnCreate = (Button) findViewById(R.id.btnRCreateRide);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              createRide();
            }
        });


        final EditText time = (EditText) findViewById(R.id.editRTime);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                final TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateRide.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            selectedHour=selectedHour-12;
                            AM_PM = "PM";
                        }
                        time.setText( selectedHour + ":" + selectedMinute+" "+AM_PM);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


    }

    public void createRide()
    {
        final EditText source = (EditText) findViewById(R.id.editRSource);
        final EditText dest = (EditText) findViewById(R.id.editRDestination);
        final EditText seats = (EditText) findViewById(R.id.editRSeats);
        final EditText date = (EditText) findViewById(R.id.editRDate);
        final EditText time = (EditText) findViewById(R.id.editRTime);
        final EditText cost = (EditText) findViewById(R.id.editRCost);
        final EditText vnum = (EditText) findViewById(R.id.editVNum);


        final EditText[] allEts = {source, dest, seats, date,time,cost,vnum};
        boolean st = true;
        for (EditText editText : allEts) {
            String text = editText.getText().toString();
            if (text.length() == 0) {
                editText.setError("empty field");
                editText.requestFocus();
                st = false;
                break;
            }
        }


        if (!isValidUname(source.getText().toString()) && !isValidUname(dest.getText().toString())) {

            source.setError("Invalid source name");
            dest.setError("Invalid destination name");
            st = false;
        } else if (!isValidUname(dest.getText().toString())) {
            dest.setError("Invalid destination name");
            st = false;
        }
        if (st) {

            String availabeSeats = seats.getText().toString();
            String src=source.getText().toString();
            String des=dest.getText().toString();
            String dt=date.getText().toString();
            String tm=time.getText().toString();
            String costt=cost.getText().toString();
            String vehnum = vnum.getText().toString();

            CreateRideData.setCost(costt);
            CreateRideData.setSource(src);
            CreateRideData.setDest(des);
            CreateRideData.setSeats(availabeSeats);
            CreateRideData.setDate(dt);
            CreateRideData.setTime(tm);
            CreateRideData.setVehicle_number(vehnum);
            viewDialog();
        }
    }

    private boolean isValidUname(String name) {
        String N_Pattern = "^([A-Za-z\\+]+[A-Za-z0-9]{1,10})$";
        Pattern pattern = Pattern.compile(N_Pattern);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }


    public void viewDialog()
    {
        new AlertDialog.Builder(CreateRide.this)

                .setTitle("Car Pooling app")
                .setMessage("Create Ride?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       create();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void create()
    {
        final ConnectionM conn = new ConnectionM();
        if (ConnectionM.checkNetworkAvailable(CreateRide.this)) {
            Progressdialog dialog = new Progressdialog();
            dg = dialog.createDialog(CreateRide.this);
            dg.show();

            Thread tthread = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.createRide()) {
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
            Toast.makeText(CreateRide.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:
                     Intent intent=new Intent(CreateRide.this,Home.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Ride Created Successfully", Toast.LENGTH_LONG).show();
                    break;

                case 1:

                    Toast.makeText(getApplicationContext(), "Invalid Date format or Try later", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

}

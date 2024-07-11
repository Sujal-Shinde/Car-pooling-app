package com.example.cp.car_pooling_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Connect.Progressdialog;
import com.example.cp.car_pooling_app.Data.RideData;
import com.example.cp.car_pooling_app.Data.SearchedData;
import com.example.cp.car_pooling_app.Data.SelectedRide;

import java.util.ArrayList;

public class SearchedList extends AppCompatActivity {

    Dialog dg;
    int resp;

    private ArrayList<String> rideid;
    private ArrayList<String> name;
    private ArrayList<String> src;
    private ArrayList<String> dest;
    private ArrayList<String> date;
    private ArrayList<String> time;
    private ArrayList<String> cost;
    private ArrayList<String> booked;
    private ArrayList<String> userId;
    private ArrayList<String> seats;
    private ArrayList<Double> rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_list);

        name = SearchedData.getName();
        rideid= SearchedData.getRideId();
        src=SearchedData.getSrc();
        dest=SearchedData.getDest();
        date=SearchedData.getDate();
        time=SearchedData.getTime();
        cost=SearchedData.getCost();
        booked=SearchedData.getBooked();
        userId=SearchedData.getUserId();
        seats=SearchedData.getSeats();
        rating = SearchedData.getRating();

        search_adapter adapter = new search_adapter(SearchedList.this, rideid, name,src, dest,date,time,cost,seats,booked,rating);
        ListView lst = (ListView) findViewById(R.id.listSearch);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RideData.setRideId(rideid.get(i));
                RideData.setUserId(userId.get(i));
                RideData.setSrc(src.get(i));
                RideData.setDest(dest.get(i));
                RideData.setTime(time.get(i));
                RideData.setDate(date.get(i));
                RideData.setCost(cost.get(i));
               getRiderData();
            }
        });

    }

    public void getRiderData()
    {
        final ConnectionM conn = new ConnectionM();
        if (ConnectionM.checkNetworkAvailable(SearchedList.this)) {
            Progressdialog dialog = new Progressdialog();
            dg = dialog.createDialog(SearchedList.this);
            dg.show();
            Thread th1 = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.getRiderData()) {
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
            Toast.makeText(SearchedList.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:

                    Intent intent=new Intent(SearchedList.this,RideDetails.class);
                    startActivity(intent);
                                        break;

                case 1:

                    Toast.makeText(getApplicationContext(), "Data not found", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };


}

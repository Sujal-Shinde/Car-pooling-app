package com.example.cp.car_pooling_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cp.car_pooling_app.Data.RequestData;
import com.example.cp.car_pooling_app.Data.SelectedPassenger;

import java.util.ArrayList;

public class PassengerReq extends AppCompatActivity {

    public static ArrayList<String> reqId;
    public static ArrayList<String> userId;
    public static ArrayList<String> name;
    public static ArrayList<String> gender;
    public static ArrayList<String> ph;
    public static ArrayList<String> status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_req);
        name= RequestData.getName();
        userId=RequestData.getUserId();
        reqId=RequestData.getReqId();
        gender=RequestData.getGender();
        ph=RequestData.getPh();
        status=RequestData.getStatus();


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PassengerReq.this,
                android.R.layout.simple_list_item_1,
                name);

        ListView lst = (ListView) findViewById(R.id.listViewPassenger);
        lst.setAdapter(arrayAdapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SelectedPassenger.setIndex(i);
                SelectedPassenger.setReqId(reqId.get(i));
                SelectedPassenger.setUserId(userId.get(i));
                SelectedPassenger.setName(name.get(i));
                SelectedPassenger.setGender(gender.get(i));
                SelectedPassenger.setPh(ph.get(i));
                SelectedPassenger.setSt(status.get(i));
                Intent intent=new Intent(PassengerReq.this,PassengerDetails.class);
                startActivity(intent);
            }
        });
    }
}

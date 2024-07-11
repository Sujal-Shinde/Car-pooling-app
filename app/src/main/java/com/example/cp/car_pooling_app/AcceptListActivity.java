package com.example.cp.car_pooling_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Connect.Progressdialog;
import com.example.cp.car_pooling_app.Data.ReqAcceptlist;
import com.example.cp.car_pooling_app.Data.selacceptreq;

import java.util.ArrayList;

public class AcceptListActivity extends AppCompatActivity {

ListView listAccept;
    Dialog dg;
    int resp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_list);

        listAccept=(ListView)findViewById(R.id.listAcceptreq);

        getAcceptreq_list();

        listAccept.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selacceptreq.setRequest_id(ReqAcceptlist.getRequest_id().get(position));
                selacceptreq.setRide_id(ReqAcceptlist.getRide_id().get(position));
                selacceptreq.setPassenger_id(ReqAcceptlist.getPassenger_id().get(position));
                selacceptreq.setStatus_(ReqAcceptlist.getStatus_().get(position));
                selacceptreq.setDate1(ReqAcceptlist.getDate1().get(position));
                selacceptreq.setLat(ReqAcceptlist.getLat().get(position));
                selacceptreq.setLon(ReqAcceptlist.getLon().get(position));
                selacceptreq.setRider_id(ReqAcceptlist.getRider_id().get(position));
                selacceptreq.setSource(ReqAcceptlist.getSource().get(position));
                selacceptreq.setDestination(ReqAcceptlist.getDestination().get(position));
                selacceptreq.setFname(ReqAcceptlist.getFname().get(position));
                selacceptreq.setEmail(ReqAcceptlist.getEmail().get(position));
                selacceptreq.setMobile(ReqAcceptlist.getMobile().get(position));
                selacceptreq.setAddr(ReqAcceptlist.getAddr().get(position));
                selacceptreq.setTime(ReqAcceptlist.getTime().get(position));
                selacceptreq.setCost(ReqAcceptlist.getCost().get(position));



                Intent intent=new Intent(AcceptListActivity.this,AcceptOnMapActivity.class);
                startActivity(intent);



            }
        });





    }

    public void getAcceptreq_list()
    {
        final ConnectionM conn = new ConnectionM();
        if (ConnectionM.checkNetworkAvailable(AcceptListActivity.this)) {
            Progressdialog dialog = new Progressdialog();
            dg = dialog.createDialog(AcceptListActivity.this);
            dg.show();

            Thread tthread = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.getAccept_req()) {
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
            Toast.makeText(AcceptListActivity.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:

                    ArrayList<String> reqId,source,destination,Status;
                    reqId = ReqAcceptlist.getRequest_id();
                    source=ReqAcceptlist.getSource();
                    destination=ReqAcceptlist.getDestination();
                    Status=ReqAcceptlist.getStatus_();
                    if (!reqId.isEmpty()) {

                        AcceptAdapter adapter = new AcceptAdapter(AcceptListActivity.this, reqId,source, destination,Status);

                        listAccept.setAdapter(adapter);


                    } else {
                        Toast.makeText(AcceptListActivity.this, "No Accept Request", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case 1:

                    Toast.makeText(getApplicationContext(), "Data not Found", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}

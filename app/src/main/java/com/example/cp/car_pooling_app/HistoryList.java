package com.example.cp.car_pooling_app;

import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Connect.Progressdialog;
import com.example.cp.car_pooling_app.Data.History;
import com.example.cp.car_pooling_app.Data.ReqAcceptlist;

import java.util.ArrayList;

public class HistoryList extends AppCompatActivity {

    Dialog dg;
    int resp;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);


        list = (ListView) findViewById(R.id.historyList);
        getAcceptreq_list();



    }


    public void getAcceptreq_list()
    {
        final ConnectionM conn = new ConnectionM();
        if (ConnectionM.checkNetworkAvailable(HistoryList.this)) {
            Progressdialog dialog = new Progressdialog();
            dg = dialog.createDialog(HistoryList.this);
            dg.show();

            Thread tthread = new Thread() {
                @Override
                public void run() {
                    try {
                        if (conn.getHistory()) {
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
            Toast.makeText(HistoryList.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:
                    ArrayList<Integer> r_id;
                    ArrayList<String> src;
                    ArrayList<String> dest;
                    ArrayList<String> cost;
                    ArrayList<String> date;
                    ArrayList<String> v_num;
                    ArrayList<Integer> d_id;
                    ArrayList<String> d_name;
                    ArrayList<String> d_con;

                    r_id = History.getR_id();
                    src=History.getSrc();
                    dest=History.getDest();
                    cost = History.getCost();
                    date = History.getDate();
                    v_num = History.getV_num();
                    d_id = History.getD_id();
                    d_name = History.getD_name();
                    d_con = History.getD_con();

                   // Toast.makeText(getApplicationContext(),src.get(0),Toast.LENGTH_LONG).show();

                    HistoryAdapter adapter = new HistoryAdapter(HistoryList.this, r_id,src, dest, cost, date, v_num,d_id,d_name, d_con);
                    list.setAdapter(adapter);

                    break;

                case 1:

                    Toast.makeText(getApplicationContext(), "Data not Found", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}

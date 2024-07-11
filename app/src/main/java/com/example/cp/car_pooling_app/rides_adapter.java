package com.example.cp.car_pooling_app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class rides_adapter extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> id;
    private ArrayList<String> src;
    private ArrayList<String> dest;
    private ArrayList<String> date;
    private ArrayList<String> time;

    public rides_adapter(Activity context, ArrayList<String> id_, ArrayList<String> src_, ArrayList<String> dest_, ArrayList<String> date_, ArrayList<String> time_) {
        super(context, R.layout.adapter_ride, id_);
        this.context = context;
        this.id = id_;
        this.src = src_;
        this.dest = dest_;
        this.date = date_;
        this.time = time_;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_ride, null, true);

        TextView txtSrc = (TextView) rowView.findViewById(R.id.txtRLSource);
        TextView txtDest = (TextView) rowView.findViewById(R.id.txtRLDestination);
        TextView txtDate = (TextView) rowView.findViewById(R.id.txtRLDate);
        TextView txtTime = (TextView) rowView.findViewById(R.id.txtRLTime);

        txtSrc.setText("Source: "+src.get(position));
        txtDest.setText("Destination: "+dest.get(position));
        txtDate.setText("Date: "+date.get(position));
        txtTime.setText("Time: "+time.get(position));

        return rowView;
    }

}

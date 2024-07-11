package com.example.cp.car_pooling_app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class search_adapter extends ArrayAdapter<String> {
    private Activity context;

    private ArrayList<Double> rating;
    private ArrayList<String> id;
    private ArrayList<String> name;
    private ArrayList<String> src;
    private ArrayList<String> dest;
    private ArrayList<String> date;
    private ArrayList<String> time;
    private ArrayList<String> cost;
    private ArrayList<String> seats;
    private ArrayList<String> booked;

    public search_adapter(Activity context, ArrayList<String> id_, ArrayList<String> name_,ArrayList<String> src_, ArrayList<String> dest_, ArrayList<String> date_, ArrayList<String> time_, ArrayList<String> cost_, ArrayList<String> seats_, ArrayList<String> booked_,ArrayList<Double> rating_) {
        super(context, R.layout.adapter_search, id_);
        this.context = context;
        this.id = id_;
        this.name = name_;
        this.src = src_;
        this.dest = dest_;
        this.date = date_;
        this.time = time_;
        this.cost = cost_;
        this.seats = seats_;
        this.booked = booked_;
        this.rating = rating_;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_search, null, true);

        TextView txtName = (TextView) rowView.findViewById(R.id.txtSName);
        TextView txtSrc = (TextView) rowView.findViewById(R.id.txtSSrc);
        TextView txtDest = (TextView) rowView.findViewById(R.id.txtSDesst);
        TextView txtDate = (TextView) rowView.findViewById(R.id.txtSDate);
        TextView txtTime = (TextView) rowView.findViewById(R.id.txtSTime);
        TextView txtCost = (TextView) rowView.findViewById(R.id.txtSCost);
        TextView txtSeats = (TextView) rowView.findViewById(R.id.txtSSeats);
        TextView txtBooked = (TextView) rowView.findViewById(R.id.txtSBookedSeats);
        TextView txtRating = (TextView) rowView.findViewById(R.id.txtSRating);

        txtName.setText("Driver: "+name.get(position));
        txtSrc.setText("Soruce: "+src.get(position));
        txtDest.setText("Destination: "+dest.get(position));
        txtDate.setText(date.get(position));
        txtTime.setText(time.get(position));
        txtCost.setText(cost.get(position));
        txtSeats.setText(seats.get(position));
        txtBooked.setText("Booked: "+booked.get(position));
        txtRating.setText("Rating: "+String.valueOf(rating.get(position)));

        return rowView;
    }

}

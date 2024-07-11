package com.example.cp.car_pooling_app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AcceptAdapter extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> reqid;
    private ArrayList<String> src;
    private ArrayList<String> dest;
    private ArrayList<String> status;


    public AcceptAdapter(Activity context, ArrayList<String> id_, ArrayList<String> src_, ArrayList<String> dest_, ArrayList<String> status_) {
        super(context, R.layout.acceptreqadpterlayout, id_);
        this.context = context;
        this.reqid = id_;
        this.src = src_;
        this.dest = dest_;
        this.status=status_;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.acceptreqadpterlayout, null, true);

        TextView txtSrc = (TextView) rowView.findViewById(R.id.txtRLSource);
        TextView txtDest = (TextView) rowView.findViewById(R.id.txtRLDestination);

        TextView txtAccept = (TextView) rowView.findViewById(R.id.txtAcceptstu);

        txtSrc.setText(src.get(position));
        txtDest.setText(dest.get(position));
        txtAccept.setText(status.get(position));


        return rowView;
    }

}

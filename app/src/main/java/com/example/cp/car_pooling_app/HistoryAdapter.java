package com.example.cp.car_pooling_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Data.LogUser;
import com.example.cp.car_pooling_app.Data.RespMsg;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HistoryAdapter extends ArrayAdapter<String> {


    private Activity context;
    private ArrayList<Integer> r_id;
    private ArrayList<String> src;
    private ArrayList<String> dest;
    private ArrayList<String> cost;
    private ArrayList<String> date;
    private ArrayList<String> v_num;
    private ArrayList<Integer> d_id;
    private ArrayList<String> d_name;
    private ArrayList<String> d_con;

    ApiInterface apiInterface;
    Retrofit retrofit;

    public HistoryAdapter(@NonNull Activity context,ArrayList<Integer> r_id, ArrayList<String> src, ArrayList<String> dest, ArrayList<String> cost, ArrayList<String> date, ArrayList<String> v_num, ArrayList<Integer> d_id,ArrayList<String> d_name, ArrayList<String> d_con) {
        super(context, R.layout.history_item,src);
        this.context = context;
        this.r_id = r_id;
        this.src = src;
        this.dest = dest;
        this.cost = cost;
        this.date = date;
        this.v_num = v_num;
        this.d_id  =d_id;
        this.d_name = d_name;
        this.d_con = d_con;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.history_item, null, true);


        TextView txtSrc = (TextView) rowView.findViewById(R.id.txtHSource);
        TextView txtDest = (TextView) rowView.findViewById(R.id.txtHDest);
        TextView txtCost = (TextView) rowView.findViewById(R.id.txtHCost);
        TextView txtDate = (TextView) rowView.findViewById(R.id.txtHDate);
        TextView txtVNum = (TextView) rowView.findViewById(R.id.txtHVeh);
        TextView txtDName = (TextView) rowView.findViewById(R.id.txtHDName);
        TextView txtDCon = (TextView) rowView.findViewById(R.id.txtHDCon);
        Button btnRate = (Button) rowView.findViewById(R.id.btnRate);
        final RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.ratingBar);
        Button btnPay = rowView.findViewById(R.id.btnPay);

        if (!src.get(0).equals(null)) {
            txtSrc.setText(src.get(position));
            txtDest.setText(dest.get(position));
            txtCost.setText(cost.get(position));
            txtDate.setText(date.get(position).substring(0, 8));
            txtVNum.setText(v_num.get(position));
            txtDName.setText(d_name.get(position));
            txtDCon.setText(d_con.get(position));
        }

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float rating = ratingBar.getRating();
                if (!ApiClient.checkNetworkAvailable(context))
                    Toast.makeText(context,"No internet connection available",Toast.LENGTH_LONG).show();
                else if (rating == 0)
                    Toast.makeText(context,"Please give rating",Toast.LENGTH_LONG).show();
                else{
                    final ProgressDialog pd = new ProgressDialog(context);
                    pd.setTitle("Alert");
                    pd.setMessage("Please wait...");
                    pd.show();

                    retrofit = ApiClient.getClient();
                    apiInterface = retrofit.create(ApiInterface.class);

                    Call<RespMsg> call = apiInterface.giveRating(String.valueOf(r_id.get(position)),LogUser.getUserId(),
                            String.valueOf(d_id.get(position)),String.valueOf(rating));

                    call.enqueue(new Callback<RespMsg>() {
                        @Override
                        public void onResponse(Call<RespMsg> call, Response<RespMsg> response) {
                            if (response.isSuccessful())
                            {
                                String message = response.body().getMsg();
                                //context.finish();
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                            }
                            if (pd.isShowing())
                                pd.dismiss();
                        }

                        @Override
                        public void onFailure(Call<RespMsg> call, Throwable t) {
                            Toast.makeText(context,t.toString(),Toast.LENGTH_LONG).show();
                            if (pd.isShowing())
                                pd.dismiss();
                        }
                    });
                }

//                Toast.makeText(context,String.valueOf(ratingBar.getRating() +" "+r_id.get(position)
//                +" "+d_id.get(position))+" "+LogUser.getUserId(),Toast.LENGTH_LONG).show();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PaymentActivity.class);
                intent.putExtra("Amount",cost.get(position));
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}

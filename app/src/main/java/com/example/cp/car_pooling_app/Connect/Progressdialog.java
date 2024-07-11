package com.example.cp.car_pooling_app.Connect;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;

import com.example.cp.car_pooling_app.R;

public class Progressdialog {
    public Dialog createDialog(Context context)
    {
        try {
            Dialog dialog=new Dialog(context,android.R.style.Theme_Translucent);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loading);
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //onBackPressed();
                    dialog.dismiss();
                }
            });
            return dialog;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}

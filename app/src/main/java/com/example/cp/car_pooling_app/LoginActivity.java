package com.example.cp.car_pooling_app;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Connect.Progressdialog;

public class LoginActivity extends AppCompatActivity  {

    Dialog dg;
    int resp;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_PHONE_STATE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//TODO
        Button btnLogin=(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Intent intent=new Intent(LoginActivity.this,Home.class);
                startActivity(intent);*/
                login();
            }
        });

        Button btnSignup=(Button)findViewById(R.id.btnRegister);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,Register.class);
                startActivity(intent);
            }
        });

    }

    public void login()
    {
        final EditText editMail = (EditText) findViewById(R.id.editMail);
        final EditText editPass = (EditText) findViewById(R.id.editPass);
        if (editMail.getText().toString().equals("")||editPass.getText().toString().equals("")) {
            android.app.AlertDialog alert = new android.app.AlertDialog.Builder(LoginActivity.this).create();
            alert.setTitle("Enter All Details");
            alert.setMessage("All Fields Are Mandatory");
            alert.show();
        }
        else
        {
         /*   Intent intent=new Intent(UserLogin.this,UserHome.class);
            startActivity(intent);*/
            final String uname = editMail.getText().toString().trim();
            final String pass = editPass.getText().toString().trim();
            final ConnectionM conn = new ConnectionM();
            if (ConnectionM.checkNetworkAvailable(LoginActivity.this)) {
                Progressdialog dialog = new Progressdialog();
                dg = dialog.createDialog(LoginActivity.this);
                dg.show();
                Thread th1 = new Thread() {
                    @Override
                    public void run() {
                        try {
                            if (conn.authUser(uname,pass)) {
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
                Toast.makeText(LoginActivity.this, "Sorry no network access.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            dg.cancel();
            switch (resp) {
                case 0:
                    final EditText editPass = (EditText) findViewById(R.id.editPass);
                    editPass.setText("");
                    Intent intent=new Intent(LoginActivity.this,Home.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                    break;

                case 1:
                    final EditText editPass2 = (EditText) findViewById(R.id.editPass);
                    editPass2.setText("");
                    Toast.makeText(getApplicationContext(), "Invalid Mail or Password", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };


    @Override
    public void onBackPressed()
    {

    }

    

}

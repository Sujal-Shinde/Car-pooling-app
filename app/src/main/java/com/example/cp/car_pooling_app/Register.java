package com.example.cp.car_pooling_app;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cp.car_pooling_app.Connect.ConnectionM;
import com.example.cp.car_pooling_app.Data.UserData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText editfname,editlname,editemail,editmobile,editadhar,editpasswsord,editaddress,editrepass,editcon1,edticon2,editgend;

    Button btnregister;

    ProgressDialog dg;
    int resp;
    Pattern p = Pattern.compile("[7-9][0-9]{9}");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editfname=(EditText)findViewById(R.id.editPFName);
        editlname=(EditText)findViewById(R.id.editPLName);
        editemail=(EditText)findViewById(R.id.editPMail);
        editmobile=(EditText)findViewById(R.id.editPMobile);
        editaddress=(EditText)findViewById(R.id.editPAddress);
        editadhar=(EditText)findViewById(R.id.editPAadhar);
        editpasswsord=(EditText)findViewById(R.id.editPPass);
        editrepass=(EditText)findViewById(R.id.editPRPass);
        editcon1 = (EditText) findViewById(R.id.editCont1);
        edticon2 = (EditText) findViewById(R.id.editCont2);
        editgend = (EditText) findViewById(R.id.editGender);



        btnregister=(Button)findViewById(R.id.btnPSubmit);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateSubmit();
            }
        });




    }


    public void validateSubmit()
    {
        final String fname = editfname.getText().toString().trim(),
                lname = editlname.getText().toString().trim(),
                email = editemail.getText().toString().trim(),
                contact = editmobile.getText().toString().trim(),
                address = editaddress.getText().toString().trim(),
                adhar = editadhar.getText().toString().trim(),
                password = editpasswsord.getText().toString().trim(),
                repassword = editrepass.getText().toString().trim(),
                con1 = editcon1.getText().toString().trim(),
                con2 = edticon2.getText().toString().trim(),
                gender = editgend.getText().toString().trim();


        Matcher m = p.matcher(con1);
        Matcher m1 = p.matcher(con2);
        Matcher m2 = p.matcher(contact);



        final EditText[] Alledit = {editfname, editlname, editemail, editmobile, editaddress,editadhar,editpasswsord,editrepass,editcon1,edticon2,editgend};
        for (EditText edit : Alledit)
        {
            if (edit.getText().toString().trim().length() == 0)
            {
                edit.setError("Empty Field");
                edit.requestFocus();
            }
        }

        if (!isValidEmail(email))
        {
            editemail.setError("Invalid Mail-Id");
        }

        else if (!repassword.equals(password))
        {
            editrepass.setError("Re-Password Not Match");
        }
        else if (contact.length()!=10 || !m2.matches())
        {
            editmobile.setError("Invalid Number");
        }
        else if (!m.matches() || con1.length() !=10)
        {
            editcon1.setError("Invlaid mobile no");
        }
        else if (!m1.matches() || con2.length() !=10)
        {
            edticon2.setError("Invlaid mobile no");
        }
        else
        {
            UserData.setFname(fname);
            UserData.setLname(lname);
            UserData.setEmail(email);
            UserData.setContact(contact);
            UserData.setAddress(address);
            UserData.setAdhar(adhar);
            UserData.setPassword(password);
            UserData.setContact_1(con1);
            UserData.setContact_2(con2);
            UserData.setGender(gender);
            register();
        }
    }

    public void register()
    {
        final ConnectionM conn = new ConnectionM();
        if (ConnectionM.checkNetworkAvailable(Register.this))
        {
            dg = new ProgressDialog(Register.this);
            dg.setMessage("Processing ...");
            dg.show();

            Thread thread = new Thread()
            {
                @Override
                public void run()
                {
                    try
                    {
                        resp = conn.register();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    hd.sendEmptyMessage(0);
                }
            };
            thread.start();
        }
        else
        {
            Toast.makeText(Register.this,"Sorry no network access.", Toast.LENGTH_LONG).show();
        }
    }

    public Handler hd = new Handler() {
        public void handleMessage(Message msg)
        {
            if (dg.isShowing())
                dg.dismiss();

           // Toast.makeText(getApplicationContext(),resp,Toast.LENGTH_LONG).show();

            switch (resp)
            {
                case 1:
                    Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_LONG).show();
                    finish();
                    break;

                case 2:
                    Toast.makeText(getApplicationContext(), "Email Id already exists", Toast.LENGTH_LONG).show();
                    break;

                case 3:
                    Toast.makeText(getApplicationContext(), "Try Later", Toast.LENGTH_LONG).show();
                    break;

                case 0:
                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    private boolean isValidEmail(String email)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}

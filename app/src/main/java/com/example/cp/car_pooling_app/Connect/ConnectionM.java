package com.example.cp.car_pooling_app.Connect;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ArrayAdapter;

import com.example.cp.car_pooling_app.ApiInterface;
import com.example.cp.car_pooling_app.CreateRide;
import com.example.cp.car_pooling_app.Data.CreateRideData;
import com.example.cp.car_pooling_app.Data.CreatedRides;
import com.example.cp.car_pooling_app.Data.Emergency;
import com.example.cp.car_pooling_app.Data.History;
import com.example.cp.car_pooling_app.Data.LogUser;
import com.example.cp.car_pooling_app.Data.ReqAcceptlist;
import com.example.cp.car_pooling_app.Data.RequestData;
import com.example.cp.car_pooling_app.Data.RideData;
import com.example.cp.car_pooling_app.Data.SearchedData;
import com.example.cp.car_pooling_app.Data.SelectedPassenger;
import com.example.cp.car_pooling_app.Data.SelectedRide;
import com.example.cp.car_pooling_app.Data.UserData;
import com.example.cp.car_pooling_app.Data.fillLocation;
import com.example.cp.car_pooling_app.Data.riderloc;
import com.example.cp.car_pooling_app.Data.userlocation;
import com.example.cp.car_pooling_app.HistoryAdapter;
import com.example.cp.car_pooling_app.LocationService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class ConnectionM {


  // public final String serverUrl = "http://demoproject.in/carpooling_service/Service1.svc/";

    public final String serverUrl = ApiInterface.Url;

    public static boolean checkNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public boolean authUser(String uname, String pass)
    {
        try
        {

            final String TAG_id = "userId";
            final String TAG_number="number";
            StringBuilder result = new StringBuilder();
//http://my-demo.in/~/Service1.svc
            String url = String.format(serverUrl + "userLogin/" + uname + "/" + pass);
            // String url = String.format("http://192.168.1.5/~/Service1.svc/userLogin/" + uname+"/"+pass);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String id = jobj.getString(TAG_id);
                String num= jobj.getString(TAG_number);
                if (!id.equals("null")) {
                    LogUser.setUsreMail(uname);
                    LogUser.setUserId(id);
                    LogUser.setEmergencyNum(num);
                    return true;
                } else {
                    return false;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return false;
        }
    }

    //createRide
    public boolean createRide() {
        try {
            final String TAG_id = "msg";
            StringBuilder result = new StringBuilder();

            HttpClient httpclient = new DefaultHttpClient();
            String url = String.format(serverUrl + "createRide");
            // String url = String.format("http://192.168.1.5/~/Service1.svc/createRide");

            HttpPost httpPost = new HttpPost(url);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("userId", LogUser.getUserId());
            jsonObject.accumulate("source", CreateRideData.getSource());
            jsonObject.accumulate("dest", CreateRideData.getDest());
            jsonObject.accumulate("seat", CreateRideData.getSeats());
            jsonObject.accumulate("cost", CreateRideData.getCost());
            jsonObject.accumulate("date", CreateRideData.getDate());
            jsonObject.accumulate("time", CreateRideData.getTime());
            jsonObject.accumulate("vehicle_number",CreateRideData.getVehicle_number());
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response = httpclient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String msg = jobj.getString(TAG_id);

                if (msg.equals("Created")) {

                    return true;

                } else if (msg.equals("Invalid")) {
                    return false;
                } else {
                    return false;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (Exception e) {
            return false;
        }
    }

    //viewCreatedRides
    public boolean viewCreatedRides() {
        try {
            final String TAG_id = "rideId";
            final String TAG_src = "source";
            final String TAG_dest = "dest";
            final String TAG_date = "date";
            final String TAG_time = "time";

            String source = null, destination, date, id, time;

            StringBuilder result = new StringBuilder();
            //String url = String.format("http://192.168.1.5/~/Service1.svc/searchBusStop");
            String url = String.format(serverUrl + "viewCreatedRides/" + LogUser.getUserId());

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));

            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                br.close();
                JSONArray jarrayobj = new JSONArray(result.toString());
                ArrayList<String> stringArray1, stringArray2, stringArray3, stringArray4, stringArray5;
                stringArray1 = new ArrayList<String>(jarrayobj.length());
                stringArray2 = new ArrayList<String>(jarrayobj.length());
                stringArray3 = new ArrayList<String>(jarrayobj.length());
                stringArray4 = new ArrayList<String>(jarrayobj.length());
                stringArray5 = new ArrayList<String>(jarrayobj.length());

                for (int i = 0; i < jarrayobj.length(); i++) {
                    JSONObject job = jarrayobj.getJSONObject(i);
                    id = job.optString(TAG_id);
                    source = job.optString(TAG_src);
                    destination = job.optString(TAG_dest);
                    date = job.optString(TAG_date);
                    time = job.optString(TAG_time);

                    stringArray1.add(id);
                    stringArray2.add(source);
                    stringArray3.add(destination);
                    stringArray4.add(date);
                    stringArray5.add(time);

                }
                CreatedRides.setRideId(stringArray1);
                CreatedRides.setSource(stringArray2);
                CreatedRides.setDestinaion(stringArray3);
                CreatedRides.setDate(stringArray4);
                CreatedRides.setTime(stringArray5);
                return true;
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());

            }
        } catch (Exception e) {
            return false;
        }
    }

    //viewRequests
    public boolean viewRequests() {
        try {
            final String TAG_reqid = "req_id";
            final String TAG_user = "user_id";
            final String TAG_st = "status";
            final String TAG_name = "username";
            final String TAG_gender = "gender";
            final String TAG_ph = "ph";

            String reqId, userId, status, name, gender, phone;

            StringBuilder result = new StringBuilder();
            //String url = String.format("http://192.168.1.5/~/Service1.svc/searchBusStop");
            String url = String.format(serverUrl + "viewReq/" + SelectedRide.getRideId());

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));

            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                br.close();
                JSONArray jarrayobj = new JSONArray(result.toString());
                ArrayList<String> stringArray1, stringArray2, stringArray3, stringArray4, stringArray5, stringArray6;
                stringArray1 = new ArrayList<String>(jarrayobj.length());
                stringArray2 = new ArrayList<String>(jarrayobj.length());
                stringArray3 = new ArrayList<String>(jarrayobj.length());
                stringArray4 = new ArrayList<String>(jarrayobj.length());
                stringArray5 = new ArrayList<String>(jarrayobj.length());
                stringArray6 = new ArrayList<String>(jarrayobj.length());

                for (int i = 0; i < jarrayobj.length(); i++) {
                    JSONObject job = jarrayobj.getJSONObject(i);
                    reqId = job.optString(TAG_reqid);
                    userId = job.optString(TAG_user);
                    status = job.optString(TAG_st);
                    name = job.optString(TAG_name);
                    gender = job.optString(TAG_gender);
                    phone = job.optString(TAG_ph);

                    stringArray1.add(reqId);
                    stringArray2.add(userId);
                    stringArray3.add(status);
                    stringArray4.add(name);
                    stringArray5.add(gender);
                    stringArray6.add(phone);

                }
                RequestData.setReqId(stringArray1);
                RequestData.setUserId(stringArray2);
                RequestData.setStatus(stringArray3);
                RequestData.setName(stringArray4);
                RequestData.setGender(stringArray5);
                RequestData.setPh(stringArray6);
                return true;
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());

            }
        } catch (Exception e) {
            return false;
        }
    }

    //respToReq
    public boolean respToReq() {
        try {

            final String TAG_id = "resp";

            StringBuilder result = new StringBuilder();
//http://my-demo.in/~/Service1.svc
            String url = String.format(serverUrl + "respRideReq/" + SelectedPassenger.getReqId() + "/" + SelectedRide.getRideId() + "/" + LogUser.getUserId() + "/" + SelectedPassenger.getSetResp());
            // String url = String.format("http://192.168.1.5/~/Service1.svc/userLogin/" + uname+"/"+pass);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String id = jobj.getString(TAG_id);
                if (id.equals("Booked")) {
                    SelectedRide.setRideStatus(id);
                    return true;
                } else if (id.equals("Full")) {
                    SelectedRide.setRideStatus(id);
                    return true;
                } else {
                    return false;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return false;
        }
    }

    //searchRide
    public boolean searchRide(String src, String dest) {
        try {
            final String TAG_rideId = "r_id";
            final String TAG_name = "name";
            final String TAG_src = "source";
            final String TAG_dest = "dest";
            final String TAG_cost = "cost";
            final String TAG_seats = "seats";
            final String TAG_date = "date";
            final String TAG_time = "time";
            final String TAG_userId = "uId";
            final String TAG_bookedSeats = "bookedSeats";
            final String TAG_rating = "rating";


            StringBuilder result = new StringBuilder();

            String rideId, name,userId, source, destination, cost_, seats, date, time, booked;
            double rating;

            HttpClient httpclient = new DefaultHttpClient();
            String url = String.format(serverUrl + "viewRides");
            // String url = String.format("http://192.168.1.6/Car_pooling_service/Service1.svc/viewRides");

            HttpPost httpPost = new HttpPost(url);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("source", src);
            jsonObject.accumulate("dest", dest);
           // jsonObject.accumulate("cost", cost);
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response = httpclient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                br.close();
                JSONArray jarrayobj = new JSONArray(result.toString());
                ArrayList<String> nameList,stringArray1, stringArray2, stringArray3, stringArray4, stringArray5, stringArray6, stringArray7, stringArray8, stringArray9;
                ArrayList<Double> ratingList;
                stringArray1 = new ArrayList<String>(jarrayobj.length());
                stringArray2 = new ArrayList<String>(jarrayobj.length());
                stringArray3 = new ArrayList<String>(jarrayobj.length());
                stringArray4 = new ArrayList<String>(jarrayobj.length());
                stringArray5 = new ArrayList<String>(jarrayobj.length());
                stringArray6 = new ArrayList<String>(jarrayobj.length());
                stringArray7 = new ArrayList<String>(jarrayobj.length());
                stringArray8 = new ArrayList<String>(jarrayobj.length());
                stringArray9 = new ArrayList<String>(jarrayobj.length());
                ratingList = new ArrayList<>(jarrayobj.length());
                nameList = new ArrayList<>(jarrayobj.length());

                for (int i = 0; i < jarrayobj.length(); i++) {
                    JSONObject job = jarrayobj.getJSONObject(i);

                    rideId = job.optString(TAG_rideId);
                    source = job.optString(TAG_src);
                    destination = job.optString(TAG_dest);
                    cost_ = job.optString(TAG_cost);
                    seats = job.optString(TAG_seats);
                    date = job.optString(TAG_date);
                    time = job.optString(TAG_time);
                    userId = job.optString(TAG_userId);
                    booked = job.optString(TAG_bookedSeats);
                    rating = job.optDouble(TAG_rating);
                    name = job.optString(TAG_name);

                    stringArray1.add(rideId);
                    stringArray2.add(source);
                    stringArray3.add(destination);
                    stringArray4.add(cost_);
                    stringArray5.add(seats);
                    stringArray6.add(date);
                    stringArray7.add(time);
                    stringArray8.add(userId);
                    stringArray9.add(booked);
                    ratingList.add(rating);
                    nameList.add(name);

                }
                SearchedData.setRideId(stringArray1);
                SearchedData.setSrc(stringArray2);
                SearchedData.setDest(stringArray3);
                SearchedData.setCost(stringArray4);
                SearchedData.setSeats(stringArray5);
                SearchedData.setDate(stringArray6);
                SearchedData.setTime(stringArray7);
                SearchedData.setUserId(stringArray8);
                SearchedData.setBooked(stringArray9);
                SearchedData.setRating(ratingList);
                SearchedData.setName(nameList);

                return true;
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (Exception e) {
            return false;
        }
    }

    //getRiderData
    public boolean getRiderData() {
        try {

            final String TAG_name = "name";
            final String TAG_ph = "ph";
            final String TAG_address = "addr";
            final String TAG_gender = "gender";

            StringBuilder result = new StringBuilder();
//http://my-demo.in/~/Service1.svc
            String url = String.format(serverUrl + "riderDetails/" + RideData.getUserId());
            // String url = String.format("http://192.168.1.5/~/Service1.svc/userLogin/" + uname+"/"+pass);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String id = jobj.getString(TAG_name);
                String ph = jobj.getString(TAG_ph);
                String addr = jobj.getString(TAG_address);
                String gen = jobj.getString(TAG_gender);

                if (!id.equals("null")) {
                    RideData.setUsername(id);
                    RideData.setGender(gen);
                    RideData.setPhoneNo(ph);
                    return true;
                } else {
                    return false;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return false;
        }
    }

    //bookRide
    public boolean bookRide() {
        try {

            final String TAG_resp = "resp";

            StringBuilder result = new StringBuilder();

            String url = String.format(serverUrl + "bookRide/" + RideData.getRideId() + "/" + LogUser.getUserId()+"/"+ userlocation.getBook_lat()+"/"+userlocation.getBook_lon()+"/"+RideData.getUserId());
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String resp = jobj.getString(TAG_resp);

                if (resp.equals("Request_send")) {
                    return true;
                } else {
                    return false;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return false;
        }
    }

    //getEmergencyNumber
    public boolean getEmergencyNumber() {
        try {

            final String TAG_number = "number";

            StringBuilder result = new StringBuilder();
            String url = String.format(serverUrl + "getEmergency/" + LogUser.getUserId());
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String number = jobj.getString(TAG_number);
                if (!number.equals("null")) {
                    LogUser.setEmergencyNum(number);
                    return true;
                } else {
                    return false;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void UpdateLocation() {
        String responseString;
        try {
            final String TAG_RESULT = "msg";

            String url = String.format(serverUrl + "uploadlocation");
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("lat", fillLocation.getLatitude());
            jsonObject.accumulate("lon", fillLocation.getLongitude());
            jsonObject.accumulate("rideId", SelectedRide.getRideId());
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            HttpResponse response = httpclient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();

                JSONObject jsonObj = new JSONObject(responseString);
                String result = jsonObj.getString(TAG_RESULT);

                fillLocation.setResult(0);
            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean getRiderloc() {
        try {

            final String TAG_rideid = "ride_id";
            final String TAG_lat = "lat";
            final String TAG_lon = "lon";
            final String TAG_loc_id = "loc_id";

            StringBuilder result = new StringBuilder();
//http://my-demo.in/~/Service1.svc
            String url = String.format(serverUrl + "getriderloc/" + RideData.getRideId());
            // String url = String.format("http://192.168.1.5/~/Service1.svc/userLogin/" + uname+"/"+pass);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String rid = jobj.getString(TAG_rideid);
                String lat = jobj.getString(TAG_lat);
                 String lon = jobj.getString(TAG_lon);
                String loc_id = jobj.getString(TAG_loc_id);

                if (!loc_id.equals("null")) {


                    riderloc.setR_id(rid);
                    riderloc.setR_lat(Double.parseDouble(lat));
                    riderloc.setR_lon(Double.parseDouble(lon));
                    riderloc.setLoc_id(loc_id);
                    return true;
                } else {
                    return false;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return false;
        }
    }


    public boolean getuserloc() {
        try {

            final String TAG_req_id = "request_id";
            final String TAG_rid = "ride_id";
            final String TAG_uid = "user_id";
            final String TAG_status = "status";

            final String TAG_userlat = "user_lat";
            final String TAG_userlon = "user_lon";

            StringBuilder result = new StringBuilder();

            String url = String.format(serverUrl + "getuserloc/" + SelectedPassenger.getUserId());

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String rqid = jobj.getString(TAG_req_id);
                String rid = jobj.getString(TAG_rid);
                String  uid = jobj.getString(TAG_uid);
                String status = jobj.getString(TAG_status);
                String user_lat = jobj.getString(TAG_userlat);
                String user_lon= jobj.getString(TAG_userlon);

                if (!rqid.equals("null")) {


                    userlocation.setRq_id(rqid);
                    userlocation.setRide_id(rid);
                    userlocation.setUser_id(uid);
                    userlocation.setStatus(status);
                    userlocation.setLatitude(Double.parseDouble(user_lat));
                    userlocation.setLongitude(Double.parseDouble(user_lon));
                    return true;
                } else {
                    return false;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return false;
        }
    }


    public int register()
    {
        try
        {
            final String TAG_id = "msg";
            StringBuilder result = new StringBuilder();

            HttpClient httpclient = new DefaultHttpClient();
            String url = String.format(serverUrl + "register");
            HttpPost httpPost = new HttpPost(url);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("fname",UserData.getFname());
            jsonObject.accumulate("lname",UserData.getLname());
            jsonObject.accumulate("email",UserData.getEmail());
            jsonObject.accumulate("mobile",UserData.getContact());
            jsonObject.accumulate("addr",UserData.getAddress());
            jsonObject.accumulate("aadhaar",UserData.getAdhar());
            jsonObject.accumulate("pass1",UserData.getPassword());
            jsonObject.accumulate("contact_1",UserData.getContact_1());
            jsonObject.accumulate("contact_2",UserData.getContact_2());
            jsonObject.accumulate("gender",UserData.getGender());



            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response = httpclient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String msg = jobj.getString(TAG_id);
                if (msg.trim().equalsIgnoreCase("Data inserted"))
                    return 1;
                else if (msg.trim().equalsIgnoreCase("Email-id already exist"))
                    return 2;
                else
                    return 3;

            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return 0;
        }
    }


    public boolean getAccept_req() {
        try {
            final String TAG_reqid = "request_id";
            final String TAG_ride_id = "ride_id";
            final String TAG_passengerid = "passenger_id";
            final String TAG_status = "status_";
            final String TAG_date1 = "date1";
            final String TAG_lat = "lat";
            final String TAG_lon = "lon";
            final String TAG_riderid = "rider_id";
            final String TAG_source = "source";
            final String TAG_destination = "destination";
            final String TAG_fname = "fname";
            final String TAG_email = "email";
            final String TAG_mobile = "mobile";
            final String TAG_addr = "addr";
            final String TAG_time="time";
            final String TAG_cost="cost";



            StringBuilder result = new StringBuilder();
            //String url = String.format("http://192.168.1.5/~/Service1.svc/searchBusStop");
            String url = String.format(serverUrl + "getAccept_req/" +LogUser.getUserId());

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));

            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                br.close();
                JSONArray jarrayobj = new JSONArray(result.toString());
                ArrayList<String> stringArray1, stringArray2, stringArray3, stringArray4, stringArray5,stringArray6,stringArray7,stringArray8,stringArray9,stringArray10,stringArray11,stringArray12,stringArray13,stringArray14,stringArray15,stringArray16;
                stringArray1 = new ArrayList<String>(jarrayobj.length());
                stringArray2 = new ArrayList<String>(jarrayobj.length());
                stringArray3 = new ArrayList<String>(jarrayobj.length());
                stringArray4 = new ArrayList<String>(jarrayobj.length());
                stringArray5 = new ArrayList<String>(jarrayobj.length());
                stringArray6 = new ArrayList<String>(jarrayobj.length());
                stringArray7 = new ArrayList<String>(jarrayobj.length());
                stringArray8 = new ArrayList<String>(jarrayobj.length());
                stringArray9 = new ArrayList<String>(jarrayobj.length());
                stringArray10 = new ArrayList<String>(jarrayobj.length());
                stringArray11 = new ArrayList<String>(jarrayobj.length());
                stringArray12 = new ArrayList<String>(jarrayobj.length());
                stringArray13 = new ArrayList<String>(jarrayobj.length());
                stringArray14 = new ArrayList<String>(jarrayobj.length());
                stringArray15 = new ArrayList<String>(jarrayobj.length());
                stringArray16 = new ArrayList<String>(jarrayobj.length());


                for (int i = 0; i < jarrayobj.length(); i++) {
                    JSONObject job = jarrayobj.getJSONObject(i);
                   String req_id = job.optString(TAG_reqid);
                   String  ride_id = job.optString(TAG_ride_id);
                   String  passengerid = job.optString(TAG_passengerid);
                   String status =job.optString(TAG_status);

                   String date1=job.optString(TAG_date1);
                   String lat=job.optString(TAG_lat);
                   String lon=job.optString(TAG_lon);
                   String rider_id=job.optString(TAG_riderid);
                   String source=job.optString(TAG_source);
                   String destination=job.optString(TAG_destination);
                   String fname=job.optString(TAG_fname);
                   String email=job.optString(TAG_email);
                   String mobile=job.optString(TAG_mobile);
                   String addr=job.optString(TAG_addr);
                    String time=job.optString(TAG_time);
                    String cost=job.optString(TAG_cost);







                    stringArray1.add(req_id);
                    stringArray2.add(ride_id);
                    stringArray3.add(passengerid);
                    stringArray4.add(status);
                    stringArray5.add(date1);
                    stringArray6.add(lat);
                    stringArray7.add(lon);
                    stringArray8.add(rider_id);
                    stringArray9.add(source);
                    stringArray10.add(destination);
                    stringArray11.add(fname);
                    stringArray12.add(email);
                    stringArray13.add(mobile);
                    stringArray14.add(addr);
                    stringArray15.add(time);
                    stringArray16.add(cost);

                }


                    ReqAcceptlist.setRequest_id(stringArray1);
                ReqAcceptlist.setRide_id(stringArray2);
                ReqAcceptlist.setPassenger_id(stringArray3);
                ReqAcceptlist.setStatus_(stringArray4);
                ReqAcceptlist.setDate1(stringArray5);
                ReqAcceptlist.setLat(stringArray6);
                ReqAcceptlist.setLon(stringArray7);
                ReqAcceptlist.setRider_id(stringArray8);
                ReqAcceptlist.setSource(stringArray9);
                ReqAcceptlist.setDestination(stringArray10);
                ReqAcceptlist.setFname(stringArray11);
                ReqAcceptlist.setEmail(stringArray12);
                ReqAcceptlist.setMobile(stringArray13);
                ReqAcceptlist.setAddr(stringArray14);
                ReqAcceptlist.setTime(stringArray15);
                ReqAcceptlist.setCost(stringArray16);

                return true;
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());

            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean getHistory() {
        try {

            String source,dest,cost,date,vehicle_num,d_name,d_con,message;
            int d_id,r_id;

            StringBuilder result = new StringBuilder();
            //String url = String.format("http://192.168.1.5/~/Service1.svc/searchBusStop");
            String url = String.format(serverUrl + "getHistory/" + LogUser.getUserId());
            //String url = String.format(serverUrl + "getHistory/1");

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));

            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                br.close();

                JSONArray jarrayobj = new JSONArray(result.toString());
                ArrayList<Integer> rIdList;
                 ArrayList<String> srcList;
                 ArrayList<String> destList;
                 ArrayList<String> costList;
                 ArrayList<String> dateList;
                ArrayList<String> vNumList;
                ArrayList<Integer> dIdList;
                ArrayList<String> dNameList;
                ArrayList<String> dConList;

                rIdList = new ArrayList<>(jarrayobj.length());
                srcList = new ArrayList<>(jarrayobj.length());
                destList = new ArrayList<>(jarrayobj.length());
                costList = new ArrayList<>(jarrayobj.length());
                dateList = new ArrayList<>(jarrayobj.length());
                dIdList = new ArrayList<>(jarrayobj.length());
                vNumList = new ArrayList<>(jarrayobj.length());
                dNameList = new ArrayList<>(jarrayobj.length());
                dConList = new ArrayList<>(jarrayobj.length());


                for (int i = 0; i < jarrayobj.length(); i++) {

                    JSONObject job = jarrayobj.getJSONObject(i);
                    r_id = job.optInt("r_id");
                    source = job.optString("source");
                    dest = job.optString("dest");
                    cost = job.optString("cost");
                    date = job.optString("date");
                    vehicle_num = job.optString("vehicle_num");
                    d_id = job.optInt("d_id");
                    d_name = job.optString("d_name");
                    d_con = job.optString("d_con");

                    rIdList.add(r_id);
                    srcList.add(source);
                    destList.add(dest);
                    costList.add(cost);
                    dateList.add(date);
                    vNumList.add(vehicle_num);
                    dIdList.add(d_id);
                    dNameList.add(d_name);
                    dConList.add(d_con);

                }

                History.setR_id(rIdList);
                History.setSrc(srcList);
                History.setDest(destList);
                History.setCost(costList);
                History.setDate(dateList);
                History.setV_num(vNumList);
                History.setD_id(dIdList);
                History.setD_name(dNameList);
                History.setD_con(dConList);

                return true;
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());

            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean getEmergNum() {
        try {


            StringBuilder result = new StringBuilder();
//http://my-demo.in/~/Service1.svc
            String url = String.format(serverUrl + "getEmergencyNumbers/" + LogUser.getUserId());
            // String url = String.format("http://192.168.1.5/~/Service1.svc/userLogin/" + uname+"/"+pass);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String num1 = jobj.getString("number_1");
                String num2 = jobj.getString("number_2");

                if (!num1.equals("null")) {
                    Emergency.setNumber_1(num1);
                    Emergency.setNumber_2(num2);
                    return true;
                } else {
                    return false;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return false;
        }
    }




}

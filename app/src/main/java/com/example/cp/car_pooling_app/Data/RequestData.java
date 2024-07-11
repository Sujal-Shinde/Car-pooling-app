package com.example.cp.car_pooling_app.Data;

import java.util.ArrayList;

public class RequestData {

    public static ArrayList<String> reqId;
    public static ArrayList<String> userId;
    public static ArrayList<String> name;
    public static ArrayList<String> gender;
    public static ArrayList<String> ph;
    public static ArrayList<String> status;

    public static ArrayList<String> getGender() {
        return gender;
    }

    public static ArrayList<String> getName() {
        return name;
    }

    public static ArrayList<String> getPh() {
        return ph;
    }

    public static ArrayList<String> getReqId() {
        return reqId;
    }

    public static ArrayList<String> getStatus() {
        return status;
    }

    public static ArrayList<String> getUserId() {
        return userId;
    }

    public static void setGender(ArrayList<String> gender) {
        RequestData.gender = gender;
    }

    public static void setName(ArrayList<String> name) {
        RequestData.name = name;
    }

    public static void setReqId(ArrayList<String> reqId) {
        RequestData.reqId = reqId;
    }

    public static void setPh(ArrayList<String> ph) {
        RequestData.ph = ph;
    }

    public static void setStatus(ArrayList<String> status) {
        RequestData.status = status;
    }

    public static void setUserId(ArrayList<String> userId) {
        RequestData.userId = userId;
    }

}

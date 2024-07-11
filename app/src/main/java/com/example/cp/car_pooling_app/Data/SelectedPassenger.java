package com.example.cp.car_pooling_app.Data;

public class SelectedPassenger {

    public static int index;

    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        SelectedPassenger.index = index;
    }

    public static String reqId;
    public static String userId;
    public static String name;
    public static String st;
    public static String gender;
    public static String ph;

    public static String setResp;

    public static String getSetResp() {
        return setResp;
    }

    public static void setSetResp(String setResp) {
        SelectedPassenger.setResp = setResp;
    }

    public static void setUserId(String userId) {
        SelectedPassenger.userId = userId;
    }

    public static void setPh(String ph) {
        SelectedPassenger.ph = ph;
    }

    public static void setReqId(String reqId) {
        SelectedPassenger.reqId = reqId;
    }

    public static void setName(String name) {
        SelectedPassenger.name = name;
    }

    public static void setGender(String gender) {
        SelectedPassenger.gender = gender;
    }

    public static String getGender() {
        return gender;
    }

    public static String getName() {
        return name;
    }

    public static String getPh() {
        return ph;
    }

    public static String getReqId() {
        return reqId;
    }

    public static String getSt() {
        return st;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setSt(String st) {
        SelectedPassenger.st = st;
    }
    
}

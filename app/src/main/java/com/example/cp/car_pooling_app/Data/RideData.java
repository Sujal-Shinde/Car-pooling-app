package com.example.cp.car_pooling_app.Data;

public class RideData {

    public static String name;
    public static String rideId;
    public static String src;
    public static String dest;
    public static String cost;
    public static String date;
    public static String time;

    public static String userId;
    public static String username;
//    public static String address;
    public static String phoneNo;
    public static String gender;

    public static void setTime(String time) {
        RideData.time = time;
    }

    public static void setSrc(String src) {
        RideData.src = src;
    }

    public static void setDest(String dest) {
        RideData.dest = dest;
    }

    public static void setRideId(String rideId) {
        RideData.rideId = rideId;
    }

    public static String getCost() {
        return cost;
    }

    public static String getDate() {
        return date;
    }

    public static String getPhoneNo() {
        return phoneNo;
    }

    public static String getDest() {
        return dest;
    }

    public static String getGender() {
        return gender;
    }

    public static String getRideId() {
        return rideId;
    }

    public static String getTime() {
        return time;
    }

    public static String getSrc() {
        return src;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void setCost(String cost) {
        RideData.cost = cost;
    }

    public static void setDate(String date) {
        RideData.date = date;
    }

    public static void setGender(String gender) {
        RideData.gender = gender;
    }

    public static void setPhoneNo(String phoneNo) {
        RideData.phoneNo = phoneNo;
    }

    public static void setUserId(String userId) {
        RideData.userId = userId;
    }

    public static void setUsername(String username) {
        RideData.username = username;
    }


}

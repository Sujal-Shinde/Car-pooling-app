package com.example.cp.car_pooling_app.Data;

import java.util.ArrayList;

public class CreatedRides {

    public static ArrayList<String> rideId;
    public static ArrayList<String> source;
    public static ArrayList<String> destinaion;
    public static ArrayList<String> date;
    public static ArrayList<String> time;

    public static void setTime(ArrayList<String> time) {
        CreatedRides.time = time;
    }

    public static void setSource(ArrayList<String> source) {
        CreatedRides.source = source;
    }

    public static void setDate(ArrayList<String> date) {
        CreatedRides.date = date;
    }

    public static ArrayList<String> getDate() {
        return date;
    }

    public static ArrayList<String> getDestinaion() {
        return destinaion;
    }

    public static ArrayList<String> getRideId() {
        return rideId;
    }

    public static ArrayList<String> getSource() {
        return source;
    }

    public static ArrayList<String> getTime() {
        return time;
    }

    public static void setDestinaion(ArrayList<String> destinaion) {
        CreatedRides.destinaion = destinaion;
    }

    public static void setRideId(ArrayList<String> rideId) {
        CreatedRides.rideId = rideId;
    }

}

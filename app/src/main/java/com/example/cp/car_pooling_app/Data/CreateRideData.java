package com.example.cp.car_pooling_app.Data;

public class CreateRideData {

public static String source;
    public static String dest;
    public static String seats;
    public static String time;
    public static String date;
    public static String cost;
    public static String vehicle_number;

    public static String getVehicle_number() {
        return vehicle_number;
    }

    public static void setVehicle_number(String vehicle_number) {
        CreateRideData.vehicle_number = vehicle_number;
    }

    public static String getCost() {
        return cost;
    }

    public static void setCost(String cost) {
        CreateRideData.cost = cost;
    }

    public static void setDate(String date) {
        CreateRideData.date = date;
    }

    public static String getDate() {
        return date;
    }

    public static String getDest() {
        return dest;
    }

    public static String getSeats() {
        return seats;
    }

    public static String getSource() {
        return source;
    }

    public static String getTime() {
        return time;
    }

    public static void setDest(String dest) {
        CreateRideData.dest = dest;
    }

    public static void setSeats(String seats) {
        CreateRideData.seats = seats;
    }

    public static void setSource(String source) {
        CreateRideData.source = source;
    }

    public static void setTime(String time) {
        CreateRideData.time = time;
    }

}

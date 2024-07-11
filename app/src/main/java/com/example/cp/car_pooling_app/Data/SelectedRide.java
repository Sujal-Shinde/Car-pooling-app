package com.example.cp.car_pooling_app.Data;

public class SelectedRide {

    public static String rideId;

    public static void setRideId(String rideId) {
        SelectedRide.rideId = rideId;
    }

    public static String getRideId() {
        return rideId;
    }

    public static String rideStatus;

    public static String getRideStatus() {
        return rideStatus;
    }

    public static void setRideStatus(String rideStatus) {
        SelectedRide.rideStatus = rideStatus;
    }
}

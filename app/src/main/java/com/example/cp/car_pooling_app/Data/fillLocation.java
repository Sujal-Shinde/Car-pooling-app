package com.example.cp.car_pooling_app.Data;

public class fillLocation {

    public static int UpdateLocationStatus;
    public static double Latitude;
    public static double Longitude;
    public static int Result;

    public static double getLatitude() {
        return Latitude;
    }

    public static double getLongitude() {
        return Longitude;
    }

    public static int getUpdateLocationStatus() {
        return UpdateLocationStatus;
    }

    public static int getResult() {
        return Result;
    }

    public static void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public static void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public static void setResult(int result) {
        Result = result;
    }

    public static void setUpdateLocationStatus(int updateLocationStatus) {
        UpdateLocationStatus = updateLocationStatus;
    }

}

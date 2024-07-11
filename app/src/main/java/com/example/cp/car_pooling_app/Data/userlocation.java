package com.example.cp.car_pooling_app.Data;

public class userlocation {
    public static Double latitude,longitude,book_lat,book_lon;
    public static String rq_id,ride_id,user_id,status;

    public static Double getBook_lat() {
        return book_lat;
    }

    public static void setBook_lat(Double book_lat) {
        userlocation.book_lat = book_lat;
    }

    public static Double getBook_lon() {
        return book_lon;
    }

    public static void setBook_lon(Double book_lon) {
        userlocation.book_lon = book_lon;
    }

    public static String getRq_id() {
        return rq_id;
    }

    public static void setRq_id(String rq_id) {
        userlocation.rq_id = rq_id;
    }

    public static String getRide_id() {
        return ride_id;
    }

    public static void setRide_id(String ride_id) {
        userlocation.ride_id = ride_id;
    }

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        userlocation.user_id = user_id;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        userlocation.status = status;
    }

    public static Double getLatitude() {
        return latitude;
    }

    public static void setLatitude(Double latitude) {
        userlocation.latitude = latitude;
    }

    public static Double getLongitude() {
        return longitude;
    }

    public static void setLongitude(Double longitude) {
        userlocation.longitude = longitude;
    }
}

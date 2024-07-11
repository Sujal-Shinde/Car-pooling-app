package com.example.cp.car_pooling_app.Data;

import java.util.ArrayList;

public class SearchedData {

    public static ArrayList<String> rideId;
    public static ArrayList<String> name;
    public static ArrayList<String> src;
    public static ArrayList<String> dest;
    public static ArrayList<String> cost;
    public static ArrayList<String> seats;
    public static ArrayList<String> date;
    public static ArrayList<String> time;
    public static ArrayList<String> userId;
    public static ArrayList<String> booked;
    public static ArrayList<Double> rating;

    public static void setUserId(ArrayList<String> userId) {
        SearchedData.userId = userId;
    }

    public static ArrayList<String> getUserId() {
        return userId;
    }

    public static ArrayList<String> getBooked() {
        return booked;
    }

    public static ArrayList<String> getCost() {
        return cost;
    }

    public static ArrayList<String> getDate() {
        return date;
    }

    public static ArrayList<String> getDest() {
        return dest;
    }

    public static ArrayList<String> getRideId() {
        return rideId;
    }

    public static ArrayList<String> getSeats() {
        return seats;
    }

    public static ArrayList<String> getSrc() {
        return src;
    }

    public static ArrayList<String> getTime() {
        return time;
    }

    public static void setBooked(ArrayList<String> booked) {
        SearchedData.booked = booked;
    }

    public static void setCost(ArrayList<String> cost) {
        SearchedData.cost = cost;
    }

    public static void setDate(ArrayList<String> date) {
        SearchedData.date = date;
    }

    public static void setRideId(ArrayList<String> rideId) {
        SearchedData.rideId = rideId;
    }

    public static void setDest(ArrayList<String> dest) {
        SearchedData.dest = dest;
    }

    public static void setSeats(ArrayList<String> seats) {
        SearchedData.seats = seats;
    }

    public static void setSrc(ArrayList<String> src) {
        SearchedData.src = src;
    }

    public static void setTime(ArrayList<String> time) {
        SearchedData.time = time;
    }

    public static ArrayList<Double> getRating() {
        return rating;
    }

    public static void setRating(ArrayList<Double> rating) {
        SearchedData.rating = rating;
    }

    public static ArrayList<String> getName() {
        return name;
    }

    public static void setName(ArrayList<String> name) {
        SearchedData.name = name;
    }
}


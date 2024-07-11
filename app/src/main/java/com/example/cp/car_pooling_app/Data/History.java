package com.example.cp.car_pooling_app.Data;

import java.util.ArrayList;

public class History {

    private static ArrayList<Integer> r_id;
    private static ArrayList<String> src;
    private static ArrayList<String> dest;
    private static ArrayList<String> cost;
    private static ArrayList<String> date;
    private static ArrayList<String> v_num;
    private static ArrayList<Integer> d_id;
    private static ArrayList<String> d_name;
    private static ArrayList<String> d_con;

    public static ArrayList<String> getSrc() {
        return src;
    }

    public static void setSrc(ArrayList<String> src) {
        History.src = src;
    }

    public static ArrayList<String> getDest() {
        return dest;
    }

    public static void setDest(ArrayList<String> dest) {
        History.dest = dest;
    }

    public static ArrayList<String> getCost() {
        return cost;
    }

    public static void setCost(ArrayList<String> cost) {
        History.cost = cost;
    }

    public static ArrayList<String> getDate() {
        return date;
    }

    public static void setDate(ArrayList<String> date) {
        History.date = date;
    }

    public static ArrayList<String> getV_num() {
        return v_num;
    }

    public static void setV_num(ArrayList<String> v_num) {
        History.v_num = v_num;
    }

    public static ArrayList<String> getD_name() {
        return d_name;
    }

    public static void setD_name(ArrayList<String> d_name) {
        History.d_name = d_name;
    }

    public static ArrayList<String> getD_con() {
        return d_con;
    }

    public static void setD_con(ArrayList<String> d_con) {
        History.d_con = d_con;
    }

    public static ArrayList<Integer> getD_id() {
        return d_id;
    }

    public static void setD_id(ArrayList<Integer> d_id) {
        History.d_id = d_id;
    }

    public static ArrayList<Integer> getR_id() {
        return r_id;
    }

    public static void setR_id(ArrayList<Integer> r_id) {
        History.r_id = r_id;
    }
}


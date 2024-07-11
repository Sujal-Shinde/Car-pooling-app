package com.example.cp.car_pooling_app.Data;

public class LogUser {

    public static String userId;
    public static String usreMail;

    public static String emergencyNum;

    public static String getEmergencyNum() {
        return emergencyNum;
    }

    public static void setEmergencyNum(String emergencyNum) {
        LogUser.emergencyNum = emergencyNum;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getUsreMail() {
        return usreMail;
    }

    public static void setUsreMail(String usreMail) {
        LogUser.usreMail = usreMail;
    }

    public static void setUserId(String userId) {
        LogUser.userId = userId;
    }
}

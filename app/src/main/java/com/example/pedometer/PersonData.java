package com.example.pedometer;

import org.litepal.crud.DataSupport;

/**
 * Created by weailily on 2017/5/3 0003.
 */
public class PersonData extends DataSupport {
    private String userId;//用户Id
    private  String userName;//用户名
    private  String userPwd;//用户密码
    private int userAge;//年龄
    private double userheight;//身高
    private double userBodyWeight;//体重

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public int getUserAge() {
        return userAge;
    }

    public double getUserheight() {
        return userheight;
    }

    public double getUserBodyWeight() {
        return userBodyWeight;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public void setUserheight(double userheight) {
        this.userheight = userheight;
    }

    public void setUserBodyWeight(double userBodyWeight) {
        this.userBodyWeight = userBodyWeight;
    }
}
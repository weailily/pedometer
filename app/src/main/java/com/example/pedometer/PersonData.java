package com.example.pedometer;

import org.litepal.crud.DataSupport;

public class PersonData extends DataSupport {
    private int  userId;//用户Id
    private  String userName;//用户名
    private  String userPwd;//用户密码
    private String userOtherName;//昵称
    private  String sex;//性别
    private int userAge;//年龄
    private double userHeight;//身高
    private double userBodyWeight;//体重


    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public String getUserOtherName() {
        return userOtherName;
    }

    public String getSex() {
        return sex;
    }

    public int getUserAge() {
        return userAge;
    }

    public double getUserHeight() {
        return userHeight;
    }

    public double getUserBodyWeight() {
        return userBodyWeight;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public void setUserOtherName(String userOtherName) {
        this.userOtherName = userOtherName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public void setUserHeight(double userHeight) {
        this.userHeight = userHeight;
    }

    public void setUserBodyWeight(double userBodyWeight) {
        this.userBodyWeight = userBodyWeight;
    }
}
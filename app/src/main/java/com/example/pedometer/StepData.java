package com.example.pedometer;


import org.litepal.crud.DataSupport;


public class StepData extends DataSupport {
    private int id;//主键
    private String  stepCount ;//每天步数
    private String today;//日期
    private double distance;//路程
    private double energy;//消耗的能量

    public int getId() {
        return id;
}

    public String getStepCount() {
        return stepCount;
    }

    public String getToday() {
        return today;
    }

    public double getDistance() {
        return distance;
    }

    public double getEnergy() {
        return energy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStepCount(String stepCount) {
        this.stepCount = stepCount;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String  toString(){
     String s = "      "+today+"                                               "+stepCount+"步";
     return s;
     }
}

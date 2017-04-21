package com.example.pedometer;


import org.litepal.crud.DataSupport;


public class StepData extends DataSupport {
    private int id;//主键
    private String  stepCount ;//每天步数
    private String today;//日期

    public int getId() {
        return id;
    }

    public String getStepCount() {
        return stepCount;
    }

    public String getToday() {
        return today;
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

     public String  toString(){
     String s = "      "+today+"                                               "+stepCount+"步";
     return s;
     }
}

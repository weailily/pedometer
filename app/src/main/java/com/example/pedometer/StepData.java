package com.example.pedometer;


import org.litepal.crud.DataSupport;

import java.util.Date;

public class StepData extends DataSupport {
    private int id;//主键
    private int stepCount;//每天步数
    private Date today;//日期

    public int getId() {
        return id;
    }

    public int getStepCount() {
        return stepCount;
    }

    public Date getToday() {
        return today;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public void setToday(Date today) {
        this.today = today;
    }

}

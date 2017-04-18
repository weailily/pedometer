package com.example.pedometer;

import android.content.Context;

import org.litepal.LitePalApplication;


public class MyApplication extends LitePalApplication {
    private  static Context context;

    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}

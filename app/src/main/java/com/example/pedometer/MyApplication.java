package com.example.pedometer;

import android.content.Context;
import android.widget.Toast;

import org.litepal.LitePalApplication;


public class MyApplication extends LitePalApplication {
    private  static Context context;

    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        Toast.makeText(MyApplication.this,"context触发",Toast.LENGTH_SHORT).show();
    }
    public static Context getContext(){
        return context;
    }
}

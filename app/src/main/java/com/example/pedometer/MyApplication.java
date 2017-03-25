package com.example.pedometer;

import android.app.Application;
import android.content.Context;

/**
 * Created by weailily on 2017/3/13 0013.
 */
public class MyApplication extends Application {
    private  static Context context;

    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}

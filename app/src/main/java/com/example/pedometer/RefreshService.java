package com.example.pedometer;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by weailily on 2017/4/21 0021.
 */
public class RefreshService extends IntentService {
    public  RefreshService(){
        super("RefreshService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

package com.example.pedometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class LoadActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_load);
        handler.sendEmptyMessageDelayed(0,2000);

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };
    public void getHome(){
        SharedPreferencesUtils sp = new SharedPreferencesUtils(this);
        String username=(String)sp.getParam("username", "");
        if(username.equals("")) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        }
        finish();
    }
}

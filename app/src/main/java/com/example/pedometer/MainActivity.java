package com.example.pedometer;


import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView stepCount;
    StepService.StepBinder binder;
    String count;
    private boolean quit = true;

    private Handler  handler = new Handler(){
        public void handleMessage(Message msg){
            count = Long.toString(msg.what);
            stepCount.setText(count);
        }
    };

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (StepService.StepBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepCount = (TextView)findViewById(R.id.step_count);
        Log.d("StepService","bangding");
        final Intent intent = new Intent(this,StepService.class);
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        Log.d("StepService","Service is Binded");
        //Log.d("StepService","步数为"+binder.getStepCount());
        //count = Long.toString(binder.getStepCount());
       // stepCount.setText(binder.getStepCount()+"");
        display();
    }
    public  void onDestroy(){
        super.onDestroy();
        this.quit = false;
        unbindService(conn);

    }

    public void display(){
        new Thread( new Runnable() {
            @Override
            public void run() {
                while(quit) {
                    try{
                        Thread.sleep(200);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    Message message =  new Message();
                    message.what = binder.getStepCount();
                    handler.sendMessage(message);

                }
            }
        }).start();
    }


}

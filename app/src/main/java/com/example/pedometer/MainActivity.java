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
import android.view.View;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public TextView stepCount;
    public TextView lookData;
    public TextView setPlan;
    public TextView mA_home;
    public TextView mA_sport;
    public TextView mA_information;
    StepService.StepBinder binder = null;
    //private String save_stepCount;


    /**
     * 连接后台服务成功后，回调onServiceConnected方法，实例化CallBack接口里的方法，
     * 采用Handler机制来改变UI组件
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (StepService.StepBinder) iBinder;
            binder.getService().setCallback(new StepService.CallBack() {
                @Override
                public void onDataChanged(String data) {
                  //  save_stepCount = data;
                    Message msg = new Message();
                    msg.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putString("data",data);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    Handler  handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    stepCount.setText(msg.getData().getString("data"));
                    break;

                default:
                    break;
            }
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 30000);
            Message message = new Message( );
            message.what = 2;
            handler.sendMessage(message);
        }
    };
    public void initViews(){
        lookData = (TextView)findViewById(R.id.look_data);
        stepCount = (TextView)findViewById(R.id.step_count);
        setPlan = (TextView)findViewById(R.id.plan_set);
        mA_home = (TextView)findViewById(R.id.tv_home);
        mA_sport = (TextView)findViewById(R.id.tv_sport);
        mA_information = (TextView)findViewById(R.id.tv_info);
    }
    public void viewAddListener(){
        lookData.setOnClickListener(this);
        setPlan.setOnClickListener(this);
        mA_sport.setOnClickListener(this);
        mA_information.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewAddListener();
        Connector.getDatabase();
        final Intent intent = new Intent(this, StepService.class);
        bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.plan_set:
                Intent intent1 = new Intent(MainActivity.this,SetPlanActivity.class);
                startActivity(intent1);
                break;
            case R.id.look_data:
                Intent intent2 = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_sport:
                Intent intent3 = new Intent(MainActivity.this, SportActivity.class);
                startActivity(intent3);
                break;
            case R.id.tv_info:
                Intent intent4 = new Intent(MainActivity.this, PersonalActivity.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }


    public  void onDestroy() {
        super.onDestroy();
        unbindService(conn);

    }
}

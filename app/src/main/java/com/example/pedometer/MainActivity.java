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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public TextView stepCount;
    public TextView lookData;
    public TextView setPlan;
    StepService.StepBinder binder = null;


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
                    Message msg = new Message();
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

        Handler  handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                stepCount.setText(msg.getData().getString("data"));
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lookData = (TextView)findViewById(R.id.look_data);
        stepCount = (TextView)findViewById(R.id.step_count);
        setPlan = (TextView)findViewById(R.id.plan_set);

        lookData.setOnClickListener(this);
        setPlan.setOnClickListener(this);

        final Intent intent = new Intent(this,StepService.class);
        bindService(intent,conn, Service.BIND_AUTO_CREATE);


    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.plan_set:
                Intent intent1 = new Intent(MainActivity.this,SetPlanActivity.class);
                startActivity(intent1);
                break;
            case R.id.look_data:
                Intent intent2 = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent2);
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

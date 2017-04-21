package com.example.pedometer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StepService extends Service {
    public static int  stepCount = 0;

    private boolean quit = true;

    private  SensorManager sensorManager;
    public StepDetector listener;

    private StepBinder binder = new StepBinder();
    public class StepBinder extends Binder{
        public StepService getService(){
            //Log.d("StepService","返回函数中步数为"+stepCount);
            return StepService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("StepService","Service is Created");
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);//获取系统传感器管理服务
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//加速度传感器
        listener = new StepDetector();//计步监听类实例
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);//为加速度传感器注册监听器
        new Thread( new Runnable() {
            @Override
            public void run() {
                while(quit) {
                    try{
                        Thread.sleep(200);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    if(callback != null) {
                        stepCount = listener.getStepCounts();//每0.2秒获取一次步数
                        callback.onDataChanged(Integer.toString(stepCount));
                        // Log.d("StepService", "进程中的步数为" + stepCount);
                    }
                }
            }
        }).start();
        new Thread( new Runnable() {
            @Override
            public void run() {
                StepData sData = DataSupport.findLast(StepData.class);
                if(sData == null){
                    sData = new StepData();
                    sData.setId(0);
                    sData.setStepCount("0");
                    String today = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).format(Calendar.getInstance().getTime());
                    Log.d("SetPlanActivity",today);
                    sData.setToday(today);
                    sData.save();
                }
                else {
                    String lastDate = sData.getToday();
                    String curDate =  new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).format(Calendar.getInstance().getTime());
                    if (!curDate.equals(lastDate)) {
                        StepData stepData = new StepData();
                        stepData.setStepCount("0");
                        String today1 = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).format(Calendar.getInstance().getTime());
                        sData.setToday(today1);
                        stepData.save();
                    }

                }
                while(quit) {
                    try{
                        Thread.sleep(30000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    StepData stepData = new StepData();
                    stepData.setStepCount(Integer.toString(stepCount));
                    //Toast.makeText(MyApplication.getContext(),stepData.toString(),Toast.LENGTH_SHORT).show();
                    String today2 = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).format(Calendar.getInstance().getTime());
                    sData.setToday(today2);
                    int maxId = DataSupport.findLast(StepData.class).getId();
                    stepData.update(maxId);
                }
            }
        }).start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit = false;
        if (sensorManager != null){
            sensorManager.unregisterListener(listener);
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private CallBack callback = null;

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    public interface  CallBack{
        void onDataChanged(String data);
    }
}

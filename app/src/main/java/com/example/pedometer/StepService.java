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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StepService extends Service {
    public static int  stepCount = 0;
    public static int sCount ;//从数据库中取出的数据
    //public boolean addsCount;//是否加过了数据库中步数

    private boolean quit = true;

    private  SensorManager sensorManager;
    public StepDetector listener;

    public PersonData data;

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
        Log.d("StepService", "Service is Created");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);//获取系统传感器管理服务
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//加速度传感器
        listener = new StepDetector();//计步监听类实例
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);//为加速度传感器注册监听器
        SharedPreferencesUtils sp = new SharedPreferencesUtils(this);
        String name = sp.getParam("username","").toString().trim();
        List<PersonData> personDatas = DataSupport.where("userName = ?",name).find(PersonData.class);
        data = personDatas.get(0);
        new Thread( new Runnable() {
            @Override
            public void run() {
                StepData sData = DataSupport.findLast(StepData.class);
                if(sData == null){
                    sData = new StepData();
                    sData.setStepCount("0");
                    String today = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).format(Calendar.getInstance().getTime());
                    sData.setToday(today);
                    sData.setDistance(0);
                    sData.setEnergy(0);
                    sData.save();
                }
                else {
                    String lastDate = sData.getToday();
                    String curDate =  new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).format(Calendar.getInstance().getTime());
                    if (!curDate.equals(lastDate)) {
                        StepData stepData = new StepData();
                        stepData.setStepCount("0");
                        stepData.setToday(curDate);
                        sData.setDistance(0);
                        sData.setEnergy(0);
                        stepData.save();
                        sCount = 0;
                    }
                    else{
                        sCount = Integer.valueOf(sData.getStepCount());
                    }
                }
                while(quit) {
                        StepData stepData = new StepData();
                        String lastDate = DataSupport.findLast(StepData.class).getToday();
                        String today2 = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).format(Calendar.getInstance().getTime());
                        if (!today2.equals(lastDate)) {
                            stepData.setStepCount("0");
                            stepData.setToday(today2);
                            sData.setDistance(0);
                            sData.setEnergy(0);
                            stepData.save();
                            sCount = 0;
                        }else {
                        stepData.setStepCount(Integer.toString(stepCount));
                        stepData.setToday(today2);
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String distance = df.format(stepCount*0.68/1000);
                        String energy = df.format(stepCount*0.68*data.getUserBodyWeight()*1.036/1000);
                        stepData.setDistance(Double.valueOf(distance));
                        stepData.setEnergy(Double.valueOf(energy));
                        int maxId = DataSupport.findLast(StepData.class).getId();
                        stepData.update(maxId);
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (quit) {
                    if (callback != null) {
                        stepCount = listener.getStepCounts();//每0.2秒获取一次步数
                        stepCount = stepCount+sCount;
                        callback.onDataChanged(Integer.toString(stepCount));
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        StepData stepData = new StepData();
        stepData.setStepCount(Integer.toString(stepCount));
        String today = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).format(Calendar.getInstance().getTime());
        stepData.setToday(today);
        DecimalFormat df = new DecimalFormat("#0.00");
        String distance = df.format(stepCount*0.68/1000);
        String energy = df.format(stepCount*0.68*data.getUserBodyWeight()*1.036/1000);
        stepData.setDistance(Double.valueOf(distance));
        stepData.setEnergy(Double.valueOf(energy));
        int maxId = DataSupport.findLast(StepData.class).getId();
        stepData.update(maxId);
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

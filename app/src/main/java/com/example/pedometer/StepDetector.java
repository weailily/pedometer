package com.example.pedometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;


/**
 * 计步的主要算法类，自适应波峰检测法
 */

public class StepDetector implements SensorEventListener {

    public static int stepCounts = 0;//步数

    //现在传感器加速度的值
    public float aValueNew = 0;

    //上次传感器加速度的值
    public float aValueOld = 0;

    //上升的标志位
    public boolean isDirectionUp = false;

    //持续上升次数
    public int continueUpCount = 0;

    //上一点的持续上升的次数，为了记录波峰的上升次数
    public int continueUpFormerCount = 0;

    //上一点的状态，上升还是下降
    public boolean lastStatus =false;

    //波峰值
    public float peakOfWave = 0;

    //波谷值
    float valleyOfWave = 0;

    //此次波峰的时间
    public long timeOfThisPeak = 0;

    //上次波峰的时间
    public long timeOfLastPeak = 0;

    //时间差
    public long differenceOfTime = 0;


    public StepDetector() {
    }

    /**
     * 当传感器值发生变化时回调该方法
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //获取加速度传感器三方向的值
        float xValue = sensorEvent.values[0];
        float yValue = sensorEvent.values[1];
        float zValue = sensorEvent.values[2];
        aValueNew = (float) Math.sqrt((xValue*xValue)+(yValue*yValue)+(zValue*zValue));
       // Log.d("StepDetector","x= "+xValue+" ");
        //Log.d("StepDetector","y= "+yValue+" ");
      // Log.d("StepDetector","z= "+zValue+" ");
      //Log.d("StepService","a= "+aValueNew+" ");
        DetectorNewStep(aValueNew);
    }
    public void  DetectorNewStep(float value){
        if(aValueOld == 0){
            aValueOld = value;
        }else if(DetectorPeak(value,aValueOld)){
            timeOfLastPeak = timeOfThisPeak;
            timeOfThisPeak = System.currentTimeMillis();
            differenceOfTime = timeOfThisPeak - timeOfLastPeak;
            //Log.d("StepService","时间差是"+differenceOfTime);
            if(peakOfWave >= 11.28f && peakOfWave <= 17.86f){
                if(differenceOfTime >= 300&&differenceOfTime <= 800){
                    //Toast.makeText(MyApplication.getContext(),"走路。。。",Toast.LENGTH_SHORT).show();
                    stepCounts++;
                    //Log.d("StepService","走路时显示步数为： "+ stepCounts);
                }
            }else
                if(peakOfWave >17.86f){
                    if(differenceOfTime >= 200&&differenceOfTime <= 500){
                        stepCounts++;
                        //Log.d("StepService","跑步时显示步数为： "+ stepCounts);
                        //Toast.makeText(MyApplication.getContext(),"跑步。。。",Toast.LENGTH_SHORT).show();
                    }
                }
        }
        aValueOld = value;
    }
    public boolean DetectorPeak(float newValue,float oldValue ){
        lastStatus = isDirectionUp;
        if (newValue >= oldValue) {
            isDirectionUp = true;
            continueUpCount++;
        } else {
            continueUpFormerCount = continueUpCount;
            continueUpCount = 0;
            isDirectionUp = false;
        }
        if (!isDirectionUp && lastStatus && (continueUpFormerCount >= 3)) {
            peakOfWave = oldValue;
           // Log.d("StepService","peakOfWave = "+peakOfWave);
            return true;
        } else if (!lastStatus && isDirectionUp) {
            valleyOfWave = oldValue;
           // Log.d("StepService","valleyOfWave = "+valleyOfWave);
            return false;
        }else {
            return false;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    public int getStepCounts(){
        return stepCounts;
    }
}

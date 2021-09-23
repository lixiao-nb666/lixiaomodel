package com.lixiao.build.util.sensor;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.appliction.BaseApplication;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/12/15 0015 9:58
 */
 class SensorUtil {
    private final String tag=getClass().getSimpleName()+">>>>";
    private SensorListen sensorListen;
    private SensorManager sm;
    private SensorEventListener sensorEventListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (Sensor.TYPE_ACCELEROMETER != event.sensor.getType()) {
                return;
            }
            if(!setUiRot()){
                return;
            }
            float[] values = event.values;
            float ax = values[0];
            float ay = values[1];
            double g = Math.sqrt(ax * ax + ay * ay);

            double cos = ay / g;
            if (cos > 1) {
                cos = 1;
            } else if (cos < -1) {
                cos = -1;
            }
            double rad = Math.acos(cos);
            if (ax < 0) {
                rad = 2 * Math.PI - rad;
            }
            double uiRad = Math.PI / 2 * uiRot;
            rad -= uiRad;
            //------------------自己改
            float needRad=getRotation(rad);
            sensorListen.getNowRotation(needRad);
//            gsView.setRotation();
//		      gsView.setRotation(rad);
        }


        public float getRotation(double rad) {
            float degrees = (float) (180 * rad / Math.PI);
            return degrees;
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private  int uiRot;
    private Activity activity;
    private static SensorUtil sensorUtil;
   private   SensorUtil( ){
        sm = (SensorManager) BaseApplication.getContext().getSystemService(Activity.SENSOR_SERVICE);
    }

    public static SensorUtil getInstance(){
       if(null==sensorUtil){
           synchronized (SensorUtil.class){
               if(null==sensorUtil){
                   sensorUtil=new SensorUtil();
               }
           }
       }
       return sensorUtil;
    }

    public void setData(Activity activity,SensorListen sensorListen){
       this.activity=activity;
        this.sensorListen=sensorListen;
    }

    private boolean setUiRot(){
        if(null==activity||activity.isFinishing()){
            return false;
        }
        uiRot = activity.getWindowManager().getDefaultDisplay().getRotation();
        return true;
    }


    public void setSystemListen(){
        sm.registerListener(sensorEventListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopSystemListen(){
        sm.unregisterListener(sensorEventListener);
    }



}

package com.lixiao.build.mybase.activity.util;

import android.app.Activity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


public class ActivityManager {
    private final String tag = "lixiaoActivityManager>>>"; // LOG信息
    private List<Activity> list= new ArrayList<>();
    private static ActivityManager activityManager;

    private ActivityManager() {

    }

    // 单例模式得到退出APP的实例
    public static ActivityManager getInstance() {
        if (activityManager == null) {
            activityManager = new ActivityManager();
        }
        return activityManager;
    }

    //关闭这个类，最好在APPLICTION里面写
    public void close(){
        list.clear();
        activityManager=null;
    }

    public void clearActivity(){
        list.clear();
    }


    // 得到所有进行过，并且没有退出活动的实例
    public List<Activity> getlist() {
        return list;
    }

    /**
     * 检测某一个活动类是否还在运行
     * @param activityClass
     * @return
     */
    public boolean checkActivityIsStart(Class activityClass){
        String activityClassName=activityClass.getName();
        if(TextUtils.isEmpty(activityClassName)){
            return false;
        }
        Activity activity;
        for(int i=0;i<list.size();i++){
           activity=list.get(i);
            if(activity!=null&&activityClassName.equals(activity.getClass().getName())&&!activity.isFinishing()){
                return true;
            }
        }
        return false;
    }


    /**
     * 检测某一个活动类是否还在运行
     * @param activityClass
     * @return
     */
    public int checkActivityIsStartNumb(Class activityClass){
        String activityClassName=activityClass.getName();
        if(TextUtils.isEmpty(activityClassName)){
            return 0;
        }
        Activity activity;
        int numb=0;
        for(int i=0;i<list.size();i++){
            activity=list.get(i);
            if(activity!=null&&activityClassName.equals(activity.getClass().getName())&&!activity.isFinishing()){
                numb++;
            }
        }
        return numb;
    }

    // 添加活动实例
    public void add(Activity activity) {

        list.add(activity);
    }



    // 删除活动实例
    public void delete(Activity activity) {

        list.remove(activity);
    }

    // 关闭所有活动，退出APP
    public void finishAllActivity() {
        for (int i = 0; i < list.size(); i++) {
            Activity activity = list.get(i);
            if (activity!=null&&activity.isFinishing() == false) {
                activity.finish();
            }
        }
        list.clear();
    }


    public void finishOtherActivity(String className){
        if(TextUtils.isEmpty(className)){
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Activity activity = list.get(i);
            if (activity!=null&&activity.isFinishing() == false&&!activity.getClass().getName().equals(className)) {
                activity.finish();
            }
        }

    }





}

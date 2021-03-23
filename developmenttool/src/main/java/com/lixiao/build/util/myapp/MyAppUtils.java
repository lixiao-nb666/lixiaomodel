package com.lixiao.build.util.myapp;

import android.app.ActivityManager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.lixiao.build.mybase.appliction.BaseApplication;

import java.lang.reflect.Field;
import java.util.List;

public class MyAppUtils {
  
    /** 
     * 获取应用程序名称 
     */  
    public static synchronized String getAppName() {
        try {  
            PackageManager packageManager = BaseApplication.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    BaseApplication.getContext().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;  
            return BaseApplication.getContext().getResources().getString(labelRes);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * [获取应用程序版本名称信息]
     * @return 当前应用的版本名称 
     */  
    public static synchronized String getVersionName() {
        try {  
            PackageManager packageManager = BaseApplication.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    BaseApplication.getContext().getPackageName(), 0);
            return packageInfo.versionName;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
  
    /** 
     * [获取应用程序版本名称信息]
     * @return 当前应用的版本名称 
     */  
    public static synchronized int getVersionCode() {
        try {  
            PackageManager packageManager = BaseApplication.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    BaseApplication.getContext().getPackageName(), 0);
            return packageInfo.versionCode;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return 0;  
    }  
  
  
    /** 
     * [获取应用程序版本名称信息]
     * @return 当前应用的版本名称 
     */  
    public static synchronized String getPackageName() {
        try {  
            PackageManager packageManager = BaseApplication.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    BaseApplication.getContext().getPackageName(), 0);
            return packageInfo.packageName;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return getLollipopRecentTask();
    }

    private static String lastPackName;
    public static synchronized String getLastPackgeName(){
        if(!TextUtils.isEmpty(lastPackName)) {
            return lastPackName;
        }
        String packName=getPackageName();
        try {

            if(!TextUtils.isEmpty(packName)){
                int index=packName.lastIndexOf(".");
                if(index!=-1){
                    lastPackName=packName.substring(index+1);
                    return lastPackName;
                }
            }

        }catch (Exception e){}

        return packName;
    }


    public static String getLollipopRecentTask() {
        final int PROCESS_STATE_TOP = 2;
        try {
            //通过反射获取私有成员变量processState，稍后需要判断该变量的值
            Field processStateField = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
            List<ActivityManager.RunningAppProcessInfo> processes = ((ActivityManager) BaseApplication.getContext().getSystemService(
                    Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo process : processes) {
                //判断进程是否为前台进程
                if (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    int state = processStateField.getInt(process);
                    //processState值为2
                    if (state == PROCESS_STATE_TOP) {
                        String[] packname = process.pkgList;
                        return packname[0];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
  
  
    /** 
     * 获取图标 bitmap
     */  
    public static synchronized Bitmap getBitmap() {
        PackageManager packageManager = null;  
        ApplicationInfo applicationInfo = null;
        try {  
            packageManager = BaseApplication.getContext().getApplicationContext()
                    .getPackageManager();  
            applicationInfo = packageManager.getApplicationInfo(
                    BaseApplication.getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {  
            applicationInfo = null;  
        }  
        Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap bm = bd.getBitmap();  
        return bm;  
    }



  
}  
 
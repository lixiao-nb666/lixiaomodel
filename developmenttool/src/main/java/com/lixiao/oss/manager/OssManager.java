package com.lixiao.oss.manager;

import android.content.Context;
import com.lixiao.oss.service.OssServiceDao;

public class OssManager {
    /**
     * 需要的权限
     * <uses-permission android:name="android.permission.INTERNET" />
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     * <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
     * <p>
     * 依赖的项目
     * compile 'com.aliyun.dpa:oss-android-sdk:+'
     *
     * 使用之前必须使用OssConfig.setBean();
     *
     */


    private static OssManager ossManager;
    private OssServiceDao ossServiceDao;


    private OssManager(Context context) {
        ossServiceDao=new OssServiceDao(context);
        ossServiceDao.startService();
    }





    public static OssManager getInstance(Context context) {
        if (ossManager == null) {
            synchronized (OssManager.class) {
                if (ossManager == null) {
                    ossManager = new OssManager(context.getApplicationContext());
                }
            }
        }
        return ossManager;
    }

    public void close(){
        if(ossServiceDao!=null){
            ossServiceDao.stopService();
        }
    }



}

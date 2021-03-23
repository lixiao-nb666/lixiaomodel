package com.lixiao.build.util.systemapp.share;

import android.text.TextUtils;

import com.lixiao.build.mybase.share.BaseShare;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/15 0015 15:49
 */
public class AppInfoShare extends BaseShare {
    private static AppInfoShare appInfoShare;
    private AppInfoShare(){
        super();
    }

    public static AppInfoShare getInstance(){
        if(null==appInfoShare){
            synchronized (AppInfoShare.class){
                if(null==appInfoShare){
                    appInfoShare=new AppInfoShare();
                }
            }
        }
        return appInfoShare;
    }


    final String appIndex = "appIndex";

    public void putAppIndex(String pagName,int index) {
        if(TextUtils.isEmpty(pagName)){
            return;
        }

        putString(pagName+appIndex, index+"");
    }

    public int getAppIndex(String pagName) {
        try {
            if(!TextUtils.isEmpty(pagName)){
                String shareStr = getString(pagName+appIndex);
                return Integer.valueOf(shareStr);
            }

        } catch (Exception e) {

        }
        return -1;
    }

}

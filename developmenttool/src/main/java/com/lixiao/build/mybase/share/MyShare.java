package com.lixiao.build.mybase.share;

import android.text.TextUtils;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/15 0015 15:49
 */
public class MyShare extends BaseShare {
    private static MyShare myShare;
    private MyShare(){
        super();
    }

    public static MyShare getInstance(){
        if(null==myShare){
            synchronized (MyShare.class){
                if(null==myShare){
                    myShare=new MyShare();
                }
            }
        }
        return myShare;
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

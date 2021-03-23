package com.lixiao.build.util.sensor;

import android.app.Activity;
import android.text.TextUtils;
import com.lixiao.build.mybase.share.MyShare;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/1/25 0025 15:42
 */
public class SensorUseUtil {
    private final String tag=getClass().getSimpleName()+">>>>";
    private static SensorUseUtil sensorUseUtil;
    private SensorUseType type;
    private SensorUseType defType=SensorUseType.AUTO;

    private SensorUseUtil(){
        String str= MyShare.getInstance().getString(tag);
        try {
            if(!TextUtils.isEmpty(str)){
                int index=Integer.valueOf(str);
                type=SensorUseType.values()[index];
            }
        }catch (Exception e){}
        if(null==type){
            type=defType;
        }

    }

    public static SensorUseUtil getInstance(){
        if(null==sensorUseUtil){
            synchronized (SensorUseUtil.class){
                if(null==sensorUseUtil){
                    sensorUseUtil=new SensorUseUtil();
                }
            }
        }
        return sensorUseUtil;
    }

    public void setType(SensorUseType setType){
        if(null==setType){
            return;
        }
        type=setType;
        MyShare.getInstance().putString(tag,type.ordinal()+"");
    }


    public SensorUseType getType() {
        if(null==type){
            type=defType;
        }
        return type;
    }

    public void setActivity(Activity activity, SensorListen sensorListen){
        SensorUtil.getInstance().setData(activity,sensorListen);
    }

    private boolean isShow;
    public void show(){
        isShow=true;
        switch (getType()){
            case AUTO:
            case LANDSPACE:
            case PORTRAIT:
                SensorUtil.getInstance().setSystemListen();
                break;
            default:
                break;
        }

    }

    public void pause(){
        isShow=false;
        SensorUtil.getInstance().stopSystemListen();
    }
}

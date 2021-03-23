package com.lixiao.build.util.screenbrightness;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.lixiao.build.mybase.appliction.BaseApplication;
import com.lixiao.build.mybase.share.MyShare;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/11/2 0002 17:06
 */
public class ScreenBrightnessUtil {
    private final String tag=getClass().getName()+">>>>";
    private static ScreenBrightnessUtil screenBrightnessUtil;
    private Context context;

    private ScreenBrightnessUtil(){
        this.context= BaseApplication.getContext();
    }

    public static ScreenBrightnessUtil getInstance(){
        if(null==screenBrightnessUtil){
            synchronized (ScreenBrightnessUtil.class){
                if(null==screenBrightnessUtil){
                    screenBrightnessUtil=new ScreenBrightnessUtil();
                }
            }
        }
        return screenBrightnessUtil;
    }




    /**
     * 获得当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0  为手动调节屏幕亮度
     */
    public int getScreenMode(){
        int screenMode=0;
        try{
            screenMode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        }
        catch (Exception localException){

        }
        return screenMode;
    }

    /**
     * 获得当前屏幕亮度值  0--255
     */
    public int getScreenBrightness(){
        int screenBrightness=255;
        try{
            screenBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception localException){
        }
        return screenBrightness;
    }



    public int getScreenBrightnessIsPercent(){
        int screenBrightness=getScreenBrightness();
        int showNumb=screenBrightness*100/255;
        String shareStr= MyShare.getInstance().getString(tag);
        if(!TextUtils.isEmpty(shareStr)){
            int share=Integer.valueOf(shareStr);
            int cha= Math.abs(share - showNumb);
            if(cha<17){
                showNumb=share;
            }
        }
        return showNumb;
    }

    /**
     * 设置当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0  为手动调节屏幕亮度
     */
    public void setScreenMode(int paramInt){
        try{
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
        }catch (Exception localException){
            localException.printStackTrace();
        }
    }
    /**
     * 设置当前屏幕亮度值  0--255
     */
    public void saveScreenBrightness(int paramInt){
        try{
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, paramInt);
        } catch (Exception localException){
            localException.printStackTrace();
        }
    }

    public void saveScreenBrightnessByPercent(int percent){
        int paramInt=255*percent/100;
        MyShare.getInstance().putString(tag,percent+"");
        saveScreenBrightness(paramInt);
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     */
    public void setScreenBrightness(Activity activity,int paramInt){
        Window localWindow = activity.getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        float f = paramInt / 255.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     */
    public void setScreenBrightnessByPercent(Activity activity,int percent){
        int paramInt=255*percent/100;
        setScreenBrightness(activity,paramInt);
    }

}

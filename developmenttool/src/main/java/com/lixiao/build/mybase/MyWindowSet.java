package com.lixiao.build.mybase;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by lixiao on 2017/6/7 0007.
 */
public class MyWindowSet {
    private Activity activity;
    private Window window;

    public MyWindowSet(Activity activity) {
        this.activity = activity;
        window = activity.getWindow();
    }


    /**
     * 设置竖
     */
    public void setScreenPortrait() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 设置横屏
     */
    public void setScreenLandscape() {

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    public void setScreenConfig(int screenConfig) {

        activity.setRequestedOrientation(screenConfig);
    }

    public boolean screenIsLandscape() {
        int screenStatu = activity.getResources().getConfiguration().orientation;

        switch (screenStatu) {
            case 1:
                return false;
            case 0:
                return true;
            case 2:
                return true;
        }
        return false;
    }


    /**
     * ACTIONBAR
     */
    public void setNoActionBar() {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    /**
     * 全屏模式
     */
    public void setScreenFull() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置窗体屏幕长亮
     */

    public void setKeepScreenOn() {
        window.setFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 设置窗体背景模糊
     */
    public void setBackgroundBlur() {
        window.setFlags(
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }

    /**
     * 设置沉浸式状态栏
     * 判断当前SDK版本号，如果是4.4以上，就是支持沉浸式状态栏的
     * 默认全屏幕模式,可改变
     */
    public void setFlatStyleColoredBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 隐藏底部虚拟按键
     */
    public void hideBottomNavigation(View view) {
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


    public boolean bottomNavigtionIsShow() {
        boolean hasNavigationBar = false;
        Resources rs = activity.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }


    public int getBottomNavigationHeigh() {
        int vh = 0;
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    /**
     * 获得状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }




}

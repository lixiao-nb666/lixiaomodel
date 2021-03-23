package com.lixiao.build.mybase;

import android.text.TextUtils;
import android.widget.Toast;

import com.lixiao.build.mybase.appliction.BaseApplication;


/**
 * Created by lixiao
 */

public class ToastUtil {


    public static void showToast(String text) {
        Toast mToast =Toast.makeText(BaseApplication.getContext(), text.trim(), Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showToast(String text, int duration) {
        Toast  mToast = Toast.makeText(BaseApplication.getContext(), text.trim(), duration);
        mToast.show();
    }

    public static void showToast(String text,int gravity,int x,int y) {
        Toast mToast =Toast.makeText(BaseApplication.getContext(), text.trim(), Toast.LENGTH_SHORT);
        mToast.setGravity(gravity,x,y);
        mToast.show();
    }

    public static void showToast(String text,int duration,int gravity,int x,int y) {
        Toast mToast =Toast.makeText(BaseApplication.getContext(), text.trim(), duration);
        mToast.setGravity(gravity,x,y);
        mToast.show();
    }


}

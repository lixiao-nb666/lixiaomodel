package com.newbee.lixiaomodel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/4/25 0025 18:07
 */
public class View2 extends View {
    public View2(Context context) {
        super(context);
        init();
    }

    public View2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public View2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public View2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        GradientDrawable grad = new
                GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, new int[]{Color.argb(255, 0, 0, 0),Color.argb(20, 0, 0, 0),});
        setBackground(grad);
    }
}

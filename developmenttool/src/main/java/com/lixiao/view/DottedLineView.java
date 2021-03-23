package com.lixiao.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.lixiao.developmenttool.R;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/7/14 0014 9:31
 */
public class DottedLineView extends View {

    private PathEffect  pe= new DashPathEffect(new float[]{10, 10, 10, 10}, 10);//虚线的样式
    private int w,h;

    public DottedLineView(Context context) {
        super(context);
    }

    public DottedLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DottedLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DottedLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w=w;
        this.h=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(w!=0||h!=0){
            Path path=new Path();
            path.moveTo(0+8,0);
            path.lineTo(w-8,h);
            Paint   paint=new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setDither(true);
            paint.setColor(getResources().getColor(R.color.black));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(1);
            paint.setPathEffect(pe);
            canvas.drawPath(path,paint);

        }

    }
}

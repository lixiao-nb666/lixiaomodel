package com.lixiao.view.viewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.lixiao.developmenttool.R;
import com.lixiao.view.reflected.ReflectedUtil;

import java.util.Map;

public class ReflectedImageView extends ImageView {
    public ReflectedImageView(Context context) {
        super(context);
    }

    public ReflectedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReflectedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ReflectedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Runnable readRunnable= new Runnable() {
        @Override
        public void run() {
            if(null==bitmap||bitmap.isRecycled()){
                return;
            }
//            setImageBitmap(ReflectedUtil.get.createReflectedImages(bitmap));
        }
    };

    private Bitmap bitmap;
    public void setShowBitMap(Bitmap bitmap){
        this.bitmap=bitmap;
        post(readRunnable);
    }


}

package com.nrmyw.launcher.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lixiao.view.reflected.ReflectedUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class MyGlide {
    private static MyGlide myGlide;

    private MyGlide() {

    }

    public static MyGlide getInstance() {
        if (myGlide == null) {
            synchronized (MyGlide.class) {
                if (myGlide == null) {
                    myGlide = new MyGlide();
                }
            }
        }
        return myGlide;
    }

    RoundedBitmapDrawable circularBitmapDrawable;

    public void setRoundedBitMap(final Context context, final ImageView imageView, int url) {
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(context).load(url).asBitmap().dontAnimate().override(imageView.getLayoutParams().width,
                imageView.getLayoutParams().height).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setBackground(circularBitmapDrawable);
            }
        });
    }

    public void setRoundedBitMap(final Context context, final ImageView imageView, String url) {
//        Glide.with(context).load(R.drawable.model_me).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(context).load(url).asBitmap().dontAnimate().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


//    public void setBitMap(final Context context, final ImageView imageView, final String url) {
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        Glide.with(context).load(url).into(imageView);
//    }

//    public void setBitMap(final Context context, final ImageView imageView, final int url) {
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        Glide.with(context).load(url).into(imageView);
//    }

//    public void setBitMap(final Context context, final ImageView imageView, final String url, int errorIm) {
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        Glide.with(context).load(url).error(errorIm).into(imageView);
//    }

    public void setBitMap(final Context context, final ImageView imageView, final String url) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(url).into(imageView);
    }


    public void setBitMapUsWatermark(final Context context, final ImageView imageView, final String url, final int waterMarkRs) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bm = Bitmap.createScaledBitmap(resource, 150, 150, true);
                Bitmap waterMark = ((BitmapDrawable) context.getResources().getDrawable(waterMarkRs)).getBitmap();
                Bitmap useBM = Watermark(bm, waterMark, 100);
                imageView.setImageBitmap(useBM);
            }
        }); //方法中设置asBitmap可以设置回调类型
    }

//    public void setBitMapUsRefleted(final Context context, final ImageView imageView, final Drawable drawable) {
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//
//        Glide.with(context).load(drawable.).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                Bitmap bm =    ReflectedUtil.getInstance().createReflectedImages(resource);
//                imageView.setImageBitmap(bm);
//            }
//        }); //方法中设置asBitmap可以设置回调类型
//    }

//    public void setBitMapUsWatermark(final Context context, final ImageView imageView, final String url, final int waterMarkRs) {
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        Glide.with(context).
//        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                Bitmap bm = Bitmap.createScaledBitmap(resource, 150, 150, true);
//                Bitmap waterMark = ((BitmapDrawable) context.getResources().getDrawable(waterMarkRs)).getBitmap();
//                Bitmap useBM = Watermark(bm, waterMark, 100);
//                imageView.setImageBitmap(useBM);
//            }
//        }); //方法中设置asBitmap可以设置回调类型
//    }


    /* 水印
     * @param src 添加水印的图
     * @param watermark 水印图
     * @param alpha 水印的透明度
     * @return
     */
    public static Bitmap Watermark(Bitmap src, Bitmap watermark, int alpha) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        Paint paint = new Paint();
        paint.setAlpha(alpha);
        paint.setAntiAlias(true);
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src, 0, 0, null);
        cv.drawBitmap(watermark, 0, h / 2, paint);
        cv.save();
        cv.restore();
        return newb;
    }


    public void setBitMap(final Context context, final ImageView imageView, final int url) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(url).into(imageView);
    }

    public void setBitMap(final Context context, final ImageView imageView, final int url,ImageView.ScaleType scaleType) {
        imageView.setScaleType(scaleType);
        Glide.with(context).load(url).into(imageView);
    }

//    public void setBitMap(final Context context, final ImageView imageView, final int url, int errorIm) {
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        Glide.with(context).load(url).error(errorIm).into(imageView);
//    }

    public void setCenterCropBitMap(final Context context, final ImageView imageView, final String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(url).transform(new CenterCrop(context), new GlideRoundTransform(context, 3)).into(imageView);
    }

    public void setCenterCropBitMap(final Context context, final ImageView imageView, final int url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(url).transform(new CenterCrop(context), new GlideRoundTransform(context, 3)).into(imageView);
    }

    public void setCenterCropBitMap(final Context context, final ImageView imageView, final String url, int errorIma) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(url).transform(new CenterCrop(context), new GlideRoundTransform(context, 3)).error(errorIma).into(imageView);
    }

    public void setCenterCropBitMap(final Context context, final ImageView imageView, final int url, int errorIma) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(url).transform(new CenterCrop(context), new GlideRoundTransform(context, 3)).error(errorIma).into(imageView);
    }


    public void getBitmap(final Context context, final String url, final GetBitmapImp getBitmapImp) {
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bm = Bitmap.createScaledBitmap(resource, 150, 150, true);
                if (getBitmapImp != null) {
                    getBitmapImp.getBitmap(bm);
                }
            }
        }); //方法中设置asBitmap可以设置回调类型
    }


    public boolean bitmapToFile(Bitmap bitmap, String fileUrl) {
        if (bitmap == null) {
            return false;
        }
        FileOutputStream fileOutStream = null;
        try {
            //通过相关方法生成一个Bitmap类型的对象
            File file = new File(fileUrl);
            fileOutStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream); // 把位图输出到指定的文件中
            fileOutStream.flush();
            fileOutStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public interface GetBitmapImp {
        public void getBitmap(Bitmap bitmap);
    }
}

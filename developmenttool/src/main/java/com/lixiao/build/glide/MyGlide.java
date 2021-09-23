package com.lixiao.build.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * Created by lixiaodiedie
 *
 * ImageView.ScaleType
 *
 * MATRIX   matrix表示原图从ImageView的左上角开始绘制，如果原图大于ImageView，那么多余的部分则剪裁掉，如果原图小于ImageView，那么对原图不做任何处理
 *FIT_XY(1)    fitXY的目标是填充整个ImageView，为了完成这个目标，它需要对图片进行一些缩放操作，在缩放的过程中，它不会按照原图的比例来缩放
 *FIT_START(2),     将图片按比例缩放至View的宽度或者高度（取宽和高的最小值），然后居上或者居左显示（与前面缩放至宽还是高有关）
 *FIT_CENTER(3),     fitCenter和fitStart基本一样，唯一不同的是fitCenter将图片按比例缩放之后是居中显示，
 *FIT_END(4),  fitEnd和fitStart也基本一样，唯一不同的是fitEnd将图片按比例缩放之后是居右或者居下显示
 *CENTER(5),   center表示将原图按照原来的大小居中显示，如果原图的大小超过了ImageView的大小，那么剪裁掉多余部分，只显示中间一部分图像
 *CENTER_CROP(6),    centerCrop的目标是将ImageView填充满，故按比例缩放原图，使得可以将ImageView填充满，同时将多余的宽或者高剪裁掉
 *CENTER_INSIDE(7);     centerInside的目标是将原图完整的显示出来，故按比例缩放原图，使得ImageView可以将原图完整显示
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

    public void setImageScale(ImageView imageView,ImageView.ScaleType scaleType){
        if(imageView.getScaleType()!= scaleType){
            imageView.setScaleType(scaleType);
        }
    }


    public void setRoundedBitMap(final Context context, final ImageView imageView, int url) {
        Glide.with(context).load(url).asBitmap().dontAnimate().override(imageView.getLayoutParams().width,
                imageView.getLayoutParams().height).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                RoundedBitmapDrawable circularBitmapDrawable;
                circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setBackgroundDrawable(circularBitmapDrawable);
            }
        });
    }

    public void setRoundedBitMap(final Context context, final ImageView imageView, String url) {
        Glide.with(context).load(url).asBitmap().dontAnimate().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable;
                circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public void setRoundedBitMap(Context context,final ImageView imageView, Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(context).load(bytes).asBitmap().dontAnimate().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable;
                circularBitmapDrawable = RoundedBitmapDrawableFactory.create(imageView.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
        Glide.with(context).load(bytes).into(imageView);
    }

    public void setBitMap(Context context,final ImageView imageView, final int url) {
        Glide.with(context).load(url).into(imageView);
    }


    public void setBitMap(Context context,final ImageView imageView, final String url) {
        Glide.with(context).load(url).into(imageView);
    }

    public void setBitMap(Context context,final ImageView imageView, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(context).load(bytes).into(imageView);
    }

    public void setBitMapNoCache( Context context,final ImageView imageView, final String url) {
        Glide.with(context).load(url).diskCacheStrategy( DiskCacheStrategy.NONE ).skipMemoryCache(false). signature(new StringSignature(UUID.randomUUID().toString())).into(imageView);
  }

    public void setBitMapNoCache(Context context, final ImageView imageView, final int url) {
        Glide.with(context).load(url).diskCacheStrategy( DiskCacheStrategy.NONE ).skipMemoryCache(false). signature(new StringSignature(UUID.randomUUID().toString())).into(imageView);
    }

    public void setBitMapNoCache(Context context, final ImageView imageView, final Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(context).load(bytes).diskCacheStrategy( DiskCacheStrategy.NONE ).skipMemoryCache(false). signature(new StringSignature(UUID.randomUUID().toString())).into(imageView);
    }

    public void setBitMapUsWatermark(final Context context, final ImageView imageView, final String url, final int waterMarkRs) {
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bm = Bitmap.createScaledBitmap(resource, 150, 150, true);
                Bitmap waterMark = ((BitmapDrawable)context.getResources().getDrawable(waterMarkRs)).getBitmap();
                Bitmap useBM = Watermark(bm, waterMark, 100);
                imageView.setImageBitmap(useBM);
            }
        }); //方法中设置asBitmap可以设置回调类型
    }

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







    public void setCenterCropBitMap( Context context,final ImageView imageView, final String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(url).transform(new CenterCrop(context), new GlideRoundTransform(context, 3)).into(imageView);
    }

    public void setCenterCropBitMap( Context context,final ImageView imageView, final int url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(url).transform(new CenterCrop(context), new GlideRoundTransform(context, 3)).into(imageView);
    }

    public void setCenterCropBitMap(Context context,final ImageView imageView, final String url, int errorIma) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(url).transform(new CenterCrop(context), new GlideRoundTransform(context, 3)).error(errorIma).into(imageView);
    }

    public void setCenterCropBitMap(Context context, final ImageView imageView, final int url, int errorIma) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(url).transform(new CenterCrop(context), new GlideRoundTransform(context, 3)).error(errorIma).into(imageView);
    }




    public void getBitmap(Context context,final String url, final GetBitmapImp getBitmapImp) {
//        .skipMemoryCache(false). signature(new StringSignature(UUID.randomUUID().toString())
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

    public void getBitmap(final Context context, final String url, final GetBitmapImp getBitmapImp, final int needW, final int needH) {
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bm = Bitmap.createScaledBitmap(resource, needW, needH, true);
                if (getBitmapImp != null) {
                    getBitmapImp.getBitmap(bm);
                }
            }
        }); //方法中设置asBitmap可以设置回调类型
    }

    public void getBitmap(Context context,final int url, final GetBitmapImp getBitmapImp) {
//        .skipMemoryCache(false). signature(new StringSignature(UUID.randomUUID().toString())
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

    public void getBitmap(final Context context, final int url, final GetBitmapImp getBitmapImp, final int needW, final int needH) {
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bm = Bitmap.createScaledBitmap(resource, needW, needH, true);
                if (getBitmapImp != null) {
                    getBitmapImp.getBitmap(bm);
                }
            }
        }); //方法中设置asBitmap可以设置回调类型
    }
    public void getBitmapNoCache(final Context context, final String url, final GetBitmapImp getBitmapImp, final int needW, final int needH) {
        Glide.with(context).load(url).asBitmap().skipMemoryCache(false).signature(new StringSignature(UUID.randomUUID().toString())).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bm = Bitmap.createScaledBitmap(resource, needW, needH, true);
                if (getBitmapImp != null) {
                    getBitmapImp.getBitmap(bm);
                }
            }
        });




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

package com.lixiao.view.reflected;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;

public class ReflectedUtil {
    private static ReflectedUtil reflectedUtil;
    private ReflectedUtil(){}

    public static ReflectedUtil getInstance(){
        if(null==reflectedUtil){
            synchronized (ReflectedUtil.class){
                if(null==reflectedUtil){
                    reflectedUtil=new ReflectedUtil();
                }
            }
        }

        return reflectedUtil;
    }


    /**
     * 创建倒影效果
     */
    public  static Bitmap createReflectedImages(Bitmap originalImage) {
        // 倒影图和原图之间的距离
        final int reflectionGap = 4;
        final int Height = 200;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        float scale = Height / (float) height;
        // 创建矩阵对象
        Matrix sMatrix = new Matrix();
        // 指定一个角度以0,0为坐标进行旋转
        // matrix.setRotate(30);

        /**
         * 缩放图片动作
         * 第一个参数是x轴的缩放比例，而第二个参数是y轴的缩放比例
         */
        sMatrix.postScale(scale, scale);
        /**
         * Bitmap.createBitmap(Bitmap source, int x, int y, int width, int height,Matrix m, boolean filter)
         * 图片裁剪，可用这个方法：
         *         Bitmap source：要从中截图的原始位图
         *         int x:  起始x坐标
         *         int y:  起始y坐标
         *         int width：  要截的图的宽度
         *         int height：要截的图的高度
         *      boolean filter:参数为true可以进行滤波处理，有助于改善新图像质量;flase时，计算机不做过滤处理。
         *
         */
        Bitmap miniBitmap = Bitmap.createBitmap(originalImage, 0, 0, originalImage.getWidth(),
                originalImage.getHeight(), sMatrix, true);
        //Bitmap内存回收
        originalImage.recycle();


        int mwidth = miniBitmap.getWidth();
        int mheight = miniBitmap.getHeight();
        Matrix matrix = new Matrix();
        // 指定矩阵(x轴不变，y轴相反)
        matrix.preScale(1, -1);
        // 将矩阵应用到该原图之中，返回一个宽度不变，高度为原图1/2的倒影位图
        Bitmap reflectionImage = Bitmap.createBitmap(miniBitmap, 0, mheight / 2, mwidth, mheight / 2, matrix,
                false);
        // 创建一个宽度不变，高度为原图+倒影图高度的位图
        Bitmap bitmapWithReflection = Bitmap.createBitmap(mwidth, (mheight + mheight / 2), Bitmap.Config.ARGB_8888);
        // 将上面创建的位图初始化到画布
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(miniBitmap, 0, 0, null);

        Paint paint = new Paint();
        canvas.drawRect(0, mheight, mwidth, mheight + reflectionGap, paint);
        canvas.drawBitmap(reflectionImage, 0, mheight + reflectionGap, null);
        paint = new Paint();
        /**
         * 线性渐变
         *     参数一:为渐变起初点坐标x位置，
         *     参数二:为y轴位置，
         *     参数三和四:分辨对应渐变终点， 最后参数为平铺方式，
         *     这里设置为镜像Gradient是基于Shader类，所以我们通过Paint的setShader方法来设置这个渐变
         */
        LinearGradient shader = new LinearGradient(0, miniBitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        // 设置阴影
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // 用已经定义好的画笔构建一个矩形阴影渐变效果
        canvas.drawRect(0, mheight, mwidth, bitmapWithReflection.getHeight() + reflectionGap, paint); // ���Ƶ�Ӱ����ӰЧ��

//            // 创建一个ImageView用来显示已经画好的bitmapWithReflection
//            ImageView imageView = new ImageView(getContext());
//            imageView.setImageBitmap(bitmapWithReflection);
//            // 设置imageView大小 ，也就是最终显示的图片大小
////            imageView.setLayoutParams(
////                    new GalleryFlow.LayoutParams((int) (width * scale), (int) (mheight * 3 / 2.0 + reflectionGap)));
//            imageView.setLayoutParams(
//                    new GalleryFlow.LayoutParams(600, 600));
//            imageView.setScaleType(ScaleType.MATRIX);
        return bitmapWithReflection;
    }


}

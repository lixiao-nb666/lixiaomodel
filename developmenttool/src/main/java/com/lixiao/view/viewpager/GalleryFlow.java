package com.lixiao.view.viewpager;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryFlow extends Gallery {
    // 相机类,用来做类3D效果处理,比如z轴方向上的平移,绕y轴的旋转等
    private Camera mCamera = new Camera();
    // 最大转动角度,是图片绕y轴最大旋转角度,也就是屏幕最边上那两张图片的旋转角度
    private int mMaxRotationAngle = 45;
    // 最大缩放值,是图片在z轴平移的距离,视觉上看起来就是放大缩小的效果
    private int mMaxZoom = -120;
    // 半径值
    private int mCoveflowCenter;

    public GalleryFlow(Context context) {
        super(context);
        /** 设置为true时，允许多子类进行静态转换:
         *    也就是说把这个属性设成true的时候每次viewGroup(看Gallery的源码就可以看到它是从ViewGroup间
         *    接继承过来的)在重新画它的child的时候都会促发getChildStaticTransformation这个函数,所以我
         *    们只需要在这个函数里面去加上旋转和放大的操作就可以了
         **/
        this.setStaticTransformationsEnabled(true);
    }

    public GalleryFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setStaticTransformationsEnabled(true);
    }

    public GalleryFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setStaticTransformationsEnabled(true);
    }

    public int getMaxRotationAngle() {
        return mMaxRotationAngle;
    }

    public void setMaxRotationAngle(int maxRotationAngle) {
        mMaxRotationAngle = maxRotationAngle;
    }

    public int getMaxZoom() {
        return mMaxZoom;
    }

    public void setMaxZoom(int maxZoom) {
        mMaxZoom = maxZoom;
    }


    //获取GalleryView的中心位置center
    private int getCenterOfCoverflow() {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }

    //获取子View的位置
    private static int getCenterOfView(View view) {
        return view.getLeft() + view.getWidth() / 2;
    }


    /**
     * 在当前GalleryView的大小发生改变是被系统调用
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCoveflowCenter = getCenterOfCoverflow();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 控制gallery中每个图片的旋转(重写的gallery中方法)
     */
    @Override
    protected boolean getChildStaticTransformation(View child, Transformation trans) {
        //取得当前子view的半径值
        final int childCenter = getCenterOfView(child);
        System.out.println("childCenter：" + childCenter);

        final int childWidth = child.getWidth();

        //旋转角度
        int rotationAngle = 0;
        //重置转换状态
        trans.clear();
        /**设置转换类型
         *    TYPE_IDENTITY
         *    TYPE_ALPHA
         *    TYPE_MATRIX
         *    TYPE_BOTH
         */
        trans.setTransformationType(Transformation.TYPE_BOTH);

        //如果图片位于中心位置不需要进行旋转
        if (childCenter == mCoveflowCenter) {
            transformImageBitmap((ImageView) child, trans, 0);
        } else {
            //根据图片在gallery中的位置来计算图片的旋转角度
            rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);
            System.out.println("rotationAngle:" + rotationAngle);
            //如果旋转角度绝对值大于最大旋转角度返回 (-mMaxRotationAngle  或    mMaxRotationAngle )
            if (Math.abs(rotationAngle) > mMaxRotationAngle) {
                rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle : mMaxRotationAngle;
            }
            transformImageBitmap((ImageView) child, trans, rotationAngle);
        }

        return true;
    }

    /**
     * 旋转、缩放图片
     *
     * @param child         子View
     * @param trans         变换,Transformation 中包含一个矩阵和 alpha 值，矩阵是用来做平移、旋转和缩放动画的，
     *                      通过Transformation来设置子控件的canvas。
     * @param rotationAngle 旋转角度
     *                      <p>
     *                      备注：Transformation
     *                      Transformation记录了仿射矩阵Matrix,动画每触发一次，会对原来的矩阵做一次运算,
     *                      View的Bitmap与这个矩阵相乘就可以实现相应的操作(旋转、平移、缩放等)。
     *                      Transformation类封装了矩阵和alpha值,它有两个重要的成员,一是mMatrix,二是mAlpha(控制透明度)。
     */
    private void transformImageBitmap(ImageView child, Transformation trans, int rotationAngle) {

        //对效果进行保存
        mCamera.save();

        final Matrix imageMatrix = trans.getMatrix();
        //图片高度
        final int imageHeight = child.getLayoutParams().height;
        //图片宽度
        final int imageWidth = child.getLayoutParams().width;
        //返回旋转角度的绝对值
        final int rotation = Math.abs(rotationAngle);

        /**
         * translate(float x, float y, float z)
         * 三个参数x,y,z分别为在手机屏幕坐标系x,y,z三个轴上的位移，这三个参数为正：表示沿着相应坐标系正方向移动
         *
         *  在Z轴上负向移动camera的视角,实际效果为放大图片。
         *  在Y轴上移动，则图片上下移动; X轴上对应图片左右移动。
         */
        mCamera.translate(0.0f, 0.0f, -20.0f);

        // As the angle of the view gets less, zoom in
        if (rotation < mMaxRotationAngle) {
            float zoomAmount = (float) (mMaxZoom + (rotation * 1.0));
            mCamera.translate(0.0f, 0.0f, zoomAmount);
        }

        // rotateY:在Y轴上旋转,沿着Y轴图片往里翻转,对应图片竖向向里翻转。
        // rotateX:在X轴上旋转,沿着X轴图片往里翻转,则对应图片横向向里翻转。
        mCamera.rotateY(rotationAngle);

        mCamera.getMatrix(imageMatrix);
        /**
         * preTranslate(float dx, float dy)
         * postTranslate(float dx, float dy)
         *     注意他们参数是平移的距离,而不是平移目的地的坐标
         */
        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
        //恢复相机状态
        mCamera.restore();
    }
}


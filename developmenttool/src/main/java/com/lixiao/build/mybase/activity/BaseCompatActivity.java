package com.lixiao.build.mybase.activity;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.MyWindowSet;
import com.lixiao.build.mybase.activity.util.ActivityManager;
import com.lixiao.build.mybase.activity.util.ActivityUtil;


/**
 * @author 黎潇
 * @ClassName: 自定义BASEACITIVITY
 * @Description:哥就是这么拽
 * @date 2015-11-26 11:00:03
 */
public abstract class BaseCompatActivity extends AppCompatActivity {
    /**
     * 获得这个活动的当前日志标识
     */
    protected String tag = getClass().getName() + ">>>";;
    /**
     * 当前活动的context
     */
    protected Context context;
    /**
     * 当前活动的窗口设置
     */
    protected MyWindowSet myWindowSet;
    /**
     * 当前活动的VIEW视图
     */
    protected View view;



    //直接返回一个窗口的ID就完事了
    public abstract int getViewLayoutRsId();

    //初始化VIEW
    public abstract void initView();

    //初始化数据
    public abstract void initData();

    //初始化控制器
    public abstract void initControl();

    //退出的时候调用的
    public abstract void closeActivity();

    public abstract void viewIsShow();

    public abstract void viewIsPause();

    public  void viewInBackground(){

    };

    public  void viewReShow(){

    };

    //设置这个活动横竖屏
    public abstract void changeConfig();



    @Override
    public void setContentView(int layoutResID) {
        if(layoutResID==0){
            view=new View(context);
        }else {
            view = LayoutInflater.from(this).inflate(layoutResID, null);
        }
        setContentView(view);
    }


    protected Handler basehandler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActivityManager.getInstance().add(this);
        if (myWindowSet == null) {
            myWindowSet = new MyWindowSet(this);
        }
        if (context == null)
            context = this;
        setContentView(getViewLayoutRsId());
        initView();
        initData();
        initControl();
        myWindowSet.setKeepScreenOn();
        if(needFullScreen()){
            myWindowSet.setScreenFull();
        }
        if(needHideBottomNavigation()){
            myWindowSet.hideBottomNavigation(view);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        LG.i(tag,"onCreate>>>");

    }

    @Override
    protected void onStart() {
        super.onStart();
        LG.d(tag, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LG.d(tag, "onResume()");
        isReSume = true;
        viewIsShow();
    }

    public boolean isReSume = false;

    @Override
    protected void onPause() {
        super.onPause();
        LG.d(tag, "onPause()");
        isReSume = false;
        viewIsPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LG.d(tag, "onStop()");
        viewInBackground();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LG.d(tag, "onDestroy()");
        closeActivity();
        ActivityManager.getInstance().delete(this);
        basehandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LG.d(tag, "onRestart()");
        viewReShow();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//		LG.d(tag, "onNewIntent()");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LG.d(tag, "onConfigurationChanged()");
        changeConfig();
    }

    // 打印弹窗消息print
    public void showToast(String messge) {
        Toast.makeText(this, messge, Toast.LENGTH_SHORT).show();
    }

    // 直接跳转活动
    public void toActivity(Class toClass) {
        ActivityUtil.toActivity(this,toClass);
    }

    // 带数据跳转活动
    public void toActivity(Class toClass, String gsonString) {
        ActivityUtil.toActivity(this,toClass,gsonString);
    }

    //获得传的数据
    public String getIntentString() {
        return ActivityUtil.getIntentString(getIntent());
    }

    public String getRsString(int strRs){
       return getResources().getString(strRs);
    }

    public int getRsColor(int rsColor){
        return getResources().getColor(rsColor);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        LG.i(tag,"====needChangeScreen:changeConfig()222");
        needSaveData(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        LG.i(tag,"====needChangeScreen:changeConfig()111");
        super.onRestoreInstanceState(savedInstanceState);

        needGetSaveData(savedInstanceState);
    }

    protected void needSaveData(Bundle outState){

    }

    protected void needGetSaveData(Bundle savedInstanceState){

    }


    public boolean needFullScreen(){
        return false;
    };

    public boolean needHideBottomNavigation(){
        return false;
    };


    public boolean needSlideBack(){
        return false;
    };

    //手指上下滑动时的最小速度
    private final int YSPEED_MIN = 1000;

    //手指向右滑动时的最小距离
    private final int XDISTANCE_MIN = 50;

    //手指向上滑或下滑时的最小距离
    private  final int YDISTANCE_MIN = 100;

    //记录手指按下时的横坐标。
    private float xDown;

    //记录手指按下时的纵坐标。
    private float yDown;

    //记录手指移动时的横坐标。
    private float xMove;

    //记录手指移动时的纵坐标。
    private float yMove;

    //用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(needSlideBack()){
            createVelocityTracker(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    xMove = event.getRawX();
                    yMove = event.getRawY();
                    //滑动的距离
                    int distanceX = (int) (xMove - xDown);
                    int distanceY = (int) (yMove - yDown);
                    //获取顺时速度
                    int ySpeed = getScrollVelocity();
                    //关闭Activity需满足以下条件：
                    //1.x轴滑动的距离>XDISTANCE_MIN
                    //2.y轴滑动的距离在YDISTANCE_MIN范围内
                    //3.y轴上（即上下滑动的速度）<XSPEED_MIN，如果大于，则认为用户意图是在上下滑动而非左滑结束Activity
                    if (distanceX > XDISTANCE_MIN && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN) && ySpeed < YSPEED_MIN) {
                        finish();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    recycleVelocityTracker();
                    break;
                default:
                    break;
            }
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * 创建VelocityTracker对象，并将触摸界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getYVelocity();
        return Math.abs(velocity);
    }
}

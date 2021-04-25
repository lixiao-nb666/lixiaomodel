package com.lixiao.build.mybase.service.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.internal.$Gson$Preconditions;
import com.lixiao.build.mybase.service.floatwindow.util.BaseFloatViewXAndYUtil;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/1/18 0018 10:04
 */
public abstract class BaseFloatView {
    protected final String tag=getClass().getSimpleName()+">>>>";
    protected Context context;
    protected WindowManager windowManager;
    protected  WindowManager.LayoutParams layoutParams;
    private Listen listen;
    protected View view;

    /**
     * base use: WindowManager.LayoutParams.MATCH_PARENT
     * @return
     */
    protected View getView(){ return view;}
    protected int getW(){return 0;}
    protected int getH(){return 0;}
    protected int getScrollX(){return 0;}
    protected int getScrollY(){return 0;}
    protected boolean needUseShareXAndY(){return false;}

    protected String getShareKey(){return getClass().getSimpleName()+"_";}

    /**
     *  base use: Gravity.LEFT|Gravity.BOTTOM
     * @return
     */
    protected  int getGravity(){return  0;}

    public BaseFloatView(Context context, WindowManager windowManager, Listen listen,int viewId){
        this.context=context;
        this.windowManager=windowManager;
        this.listen=listen;
        view= View.inflate(context, viewId,null);
        init();
    }

    public BaseFloatView(Context context, WindowManager windowManager, Listen listen,View view){
        this.context=context;
        this.windowManager=windowManager;
        this.listen=listen;
        this.view=view;
        init();
    }

    public void init(){
        layoutParams= new WindowManager.LayoutParams();
        layoutParams.format = PixelFormat.TRANSLUCENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//不耽误其他事件
        if(getW()==0){
            layoutParams.width =WindowManager.LayoutParams.WRAP_CONTENT;
        }else {
            layoutParams.width =getW();
        }
        if(getH()==0){
            layoutParams.height =WindowManager.LayoutParams.WRAP_CONTENT;
        }else {
            layoutParams.height =getH();
        }
        if(needUseShareXAndY()){
            layoutParams.x = BaseFloatViewXAndYUtil.getInstance().getShareX(getShareKey());
            layoutParams.y = BaseFloatViewXAndYUtil.getInstance().getShareY(getShareKey());
        }else {
            layoutParams.x = getScrollX();//窗口位置的偏移量
            layoutParams.y = getScrollY();
        }
        layoutParams.gravity =getGravity();
        windowManager.addView(view,layoutParams);
        this.listen.initView(view,windowManager,layoutParams);
    }

    public void updateViewIndex(int scroolX,int scroolY){
        if(null!=windowManager&&null!=layoutParams&&null!=view){
            layoutParams.x=scroolX;
            layoutParams.y=scroolY;
            putShareXAndY(scroolX,scroolY);
            windowManager.updateViewLayout(view,layoutParams);
        }
    }

    public void putShareXAndY(int scroolX,int scroolY){
        BaseFloatViewXAndYUtil.getInstance().putShare(getShareKey(),scroolX,scroolY);
    }


    public void close(){
        windowManager.removeView(view);
    }

    public interface Listen{
        public void initView(View view, WindowManager windowManager, WindowManager.LayoutParams layoutParams);
    }
}

package com.lixiao.build.mybase.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public abstract class BasePopupWindow {
    protected BasePoputWindowListen baseList;
    private PopupWindow popupWindow;
    private View view;
    private int w,h;
    protected abstract Context getContext();
    protected abstract int bindView();
    protected abstract void initView(View view);
    protected abstract boolean clickOutHide();
    protected abstract void closeNeedDo();



    private void initPopupWindow(Object... objects){
        if(null==popupWindow){
            popupWindow = new PopupWindow(getContext());
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            view = LayoutInflater.from(getContext()).inflate(bindView(), null);
            initView(view);
            popupWindow.setContentView(view);
            popupWindow.setOutsideTouchable(clickOutHide());
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        //如果焦点不在popupWindow上，且点击了外面，不再往下dispatch事件：
                        //不做任何响应,不 dismiss popupWindow
                        hide();
                        return true;
                    }
                    //否则default，往下dispatch事件:关掉popupWindow，
                    return false;
                }
            });

        }
        nowIsShow(objects);
    }

    public void nowIsShow(Object... objects){

    }


    public void nowIsHide(){

    }



    public View getView(){
        return view;
    }

    public void getWAndH(){
        try {
            view.post(new Runnable() {
                @Override
                public void run() {
                    w=view.getMeasuredWidth();
                    h=view.getMeasuredHeight();
                    if(null!=baseList){
                        baseList.getWAndH(w,h);
                    }
                }
            });
        }catch (Exception e){

        }

    }





    public void showByDown(View showByView,int x,int y,Object... objects){
        initPopupWindow(objects);
        popupWindow.showAsDropDown  (showByView,x,y);
    }

    public void showByCenter(View showByView,int x,int y,Object... objects){
        initPopupWindow(objects);
        popupWindow.showAtLocation  (showByView,Gravity.CENTER,x,y);
    }


    public void showByTop(View showByView,int x,int y,Object... objects){
        initPopupWindow(objects);
        popupWindow.showAtLocation  (showByView,Gravity.TOP,x,y);
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public void hide(){
        if(null!=popupWindow){
            popupWindow.dismiss();
            nowIsHide();
        }

    }

    public void close(){
        if(null!=popupWindow){
            popupWindow.dismiss();
            popupWindow=null;
        }
        closeNeedDo();
    }

}

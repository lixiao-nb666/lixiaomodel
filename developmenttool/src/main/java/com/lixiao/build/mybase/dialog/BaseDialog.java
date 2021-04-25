package com.lixiao.build.mybase.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lixiao.developmenttool.R;

public abstract class BaseDialog {
    protected final String tag=getClass().getSimpleName()+">>>>";
    private AlertDialog alertDialog;
    protected abstract int bindView();
    protected abstract void initView(View view);
    protected abstract Context getContext();
    protected abstract void closeNeedDo();
    protected void showNeedDo(Object... objects){

    };

    public void show(Object... objects){
        if(null==alertDialog){
            AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
            View view= LayoutInflater.from(getContext()).inflate(bindView(),null);
            initView(view);
            builder.setView(view);
            alertDialog=builder.create();

        }
        alertDialog.show();
        showNeedDo(objects);
    };

    public void hide(){
        if(null!=alertDialog){
            alertDialog.hide();
        }
    };

    public void cancel(){
        if(null!=alertDialog){
            alertDialog.cancel();
            alertDialog=null;
        }
    };

    public void close(){
        if(null!=alertDialog){
            alertDialog.cancel();
            alertDialog=null;
        }
        closeNeedDo();
    };



}

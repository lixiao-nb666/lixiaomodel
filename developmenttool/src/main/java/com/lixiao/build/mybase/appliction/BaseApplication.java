package com.lixiao.build.mybase.appliction;

import android.app.Application;
import android.content.Context;

import com.lixiao.build.mybase.sqlite.event.SqlListenSubject;


public abstract class BaseApplication extends Application {


    protected final String tag=getClass().getName()+">>>>>>>";
    protected abstract void init();
    protected abstract void needClear(String str);
    protected abstract void close();


    private static BaseApplication application;

    private static Context context;

    public static BaseApplication getApplication() {
        return application;
    }

    public static Context getContext() {
        return context;
    }

    public static String getRsString(int strId){
        return context.getString(strId);
    }



    @Override
    public void onCreate() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        super.onCreate();
        // 程序创建的时候执行
        application=this;
        context=getApplicationContext();
        MyApplicationFile.getInstance();
        init();

    }



    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        close();
        super.onTerminate();


    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行

        super.onLowMemory();
        needClear(" is lowMenory");
    }

    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行（回收内存）
        // HOME键退出应用程序、长按MENU键，打开Recent TASK都会执
        super.onTrimMemory(level);
        needClear("system auto clear level :"+level);
    }






}

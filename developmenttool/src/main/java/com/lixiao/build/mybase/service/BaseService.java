package com.lixiao.build.mybase.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public abstract class BaseService extends Service {
    protected String tag=getClass().getName()+">>>>";
    private IBinder baseBinder=new BaseServiceBinder();

    //初始化，只会调用一次
    public abstract void init();
    //开始，每次启动都回调用这里
    public abstract void start(Intent intent, int flags, int startId);
    //结束关闭
    public abstract void close();


    @Override
    public IBinder onBind(Intent intent) {
        return baseBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        start(intent,flags,startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        close();
        super.onDestroy();

    }

    public class BaseServiceBinder extends Binder {

        public BaseService getService(){
            return BaseService.this;
        }
    }
}

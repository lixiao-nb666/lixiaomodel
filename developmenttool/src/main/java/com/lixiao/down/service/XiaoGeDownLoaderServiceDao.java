package com.lixiao.down.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by xiefuning on 2017/5/11.
 * about:
 */

public class XiaoGeDownLoaderServiceDao {

    private ServiceConnection sc;
    private XiaoGeDownLoaderService ss;
    private Context context;

    public XiaoGeDownLoaderServiceDao(Context context) {
        this.context = context.getApplicationContext();
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                XiaoGeDownLoaderService.ServiceBinder serviceBinder = (XiaoGeDownLoaderService.ServiceBinder) service;
                ss = serviceBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }


    public void startService() {
        try {
            Intent intent = new Intent(context, XiaoGeDownLoaderService.class);
            context.getApplicationContext().bindService(intent, sc, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {

        }
    }


    public void stopService() {
        try {
            context.unbindService(sc);
        } catch (Exception e) {
        }
    }
}

package com.lixiao.oss.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by xiefuning on 2017/5/11.
 * about:
 */

public class OssServiceDao {

    private ServiceConnection sc;
    private OssService sv;
    private Context context;

    public OssServiceDao(Context context) {
        this.context = context.getApplicationContext();
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                OssService.ServiceBinder serviceBinder = (OssService.ServiceBinder) iBinder;
                sv = serviceBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
    }


    public void startService() {
        try {
            Intent intent = new Intent(context, OssService.class);
//            context.startService(intent);
            Log.i("-----", "GetScreenBitMapServiceDao服务启动成功");
            context.bindService(intent, sc, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            Log.i("-----", "服务启动失败");
        }
    }


    public void stopService() {
        try {
            context.unbindService(sc);
        } catch (Exception e) {
        }
    }


}

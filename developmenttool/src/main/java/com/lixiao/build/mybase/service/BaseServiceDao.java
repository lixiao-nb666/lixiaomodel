package com.lixiao.build.mybase.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.lixiao.build.mybase.LG;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/1/18 0018 9:50
 */
public abstract class BaseServiceDao {
    private final String tag=getClass().getSimpleName()+">>>>";
    private Context context;
    private ServiceConnection sc;
    private com.lixiao.build.mybase.service.BaseService ss;
    protected abstract Class getSsCls();

    public BaseServiceDao(Context context) {
        this.context = context.getApplicationContext();

        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                com.lixiao.build.mybase.service.BaseService.BaseServiceBinder serviceBinder = ( BaseService.BaseServiceBinder) service;
                ss = serviceBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
    }

    private String packageStr;
    public void setPackage(String packageStr){
        this.packageStr=packageStr;
    }

    private String actionStr;
    public void setActionStr(String actionStr){
        this.actionStr=actionStr;
    }


    public void startServiceIsBind() {
        try {
            LG.i(tag,"startServiceIsBind()");
            Intent intent = new Intent(context, getSsCls());
            context.getApplicationContext().bindService(intent, sc, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            LG.i(tag,"startServiceIsBind()err:"+e.toString());
        }
    }




    public void stopServiceIsBind() {
        try {
            LG.i(tag,"stopServiceIsBind()");
            context.unbindService(sc);
        } catch (Exception e) {
            LG.i(tag,"stopServiceIsBind()err:"+e.toString());
        }
    }


    public void startService() {
        try {
            LG.i(tag,"startService()");
            Intent intent = new Intent(context, getSsCls());
            context.getApplicationContext().bindService(intent, sc, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            LG.i(tag,"startService()err:"+e.toString());
        }
    }

    public void stopService() {
        try {
            LG.i(tag,"stopService()");
            context.stopService(new Intent(context,getSsCls()));
        } catch (Exception e) {
            LG.i(tag,"stopService()err:"+e.toString());
        }
    }

}

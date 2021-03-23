package com.lixiao.down.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.lixiao.down.bean.ResultXiaoGeDownNeedInfoBean;
import com.lixiao.down.bean.XiaoGeDownNeedInfoBean;
import com.lixiao.down.event.XiaoGeDownEvent;
import com.lixiao.down.event.XiaoGeDownEventObserver;
import com.lixiao.down.event.XiaoGeDownEventType;
import com.lixiao.down.service.util.XiaoGeDownLoaderServiceUtil;



/**
 * Created by xiefuning on 2017/5/10.
 * about:
 */

public class XiaoGeDownLoaderService extends Service {
    private IBinder iBinder = new XiaoGeDownLoaderService.ServiceBinder();
    private String tag = getClass().getName() + ">>>>>>";
    private volatile XiaoGeDownLoaderServiceUtil xiaoGeDownLoaderServiceUtil = new XiaoGeDownLoaderServiceUtil();
    private XiaoGeDownEventObserver xiaoGeDownEventObserver = new XiaoGeDownEventObserver() {
        @Override
        public void update(XiaoGeDownEventType eventBs, Object object) {
            try {
                Log.i(tag, "-------shoudao zhilingle:" + eventBs + "-------" + object);
                switch (eventBs) {
                    case DOWN_FILE:
                        xiaoGeDownLoaderServiceUtil.add((XiaoGeDownNeedInfoBean) object);
                        break;
                    case DOWN_FILES:
                        xiaoGeDownLoaderServiceUtil.add((ResultXiaoGeDownNeedInfoBean) object);
                        break;
                    case REMOVE_DOWN:
                        xiaoGeDownLoaderServiceUtil.cancel((String) object);
                        break;
                    case REMOVE_ALL:
                        xiaoGeDownLoaderServiceUtil.cancelAll();
                        break;
                }
            } catch (Exception e) {

            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(tag, "------onCreate");
        XiaoGeDownEvent.getInstance().addObserver(xiaoGeDownEventObserver);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        XiaoGeDownEvent.getInstance().removeObserver(xiaoGeDownEventObserver);
        xiaoGeDownLoaderServiceUtil.close();
    }


    public class ServiceBinder extends Binder {
        public XiaoGeDownLoaderService getService() {
            return XiaoGeDownLoaderService.this;
        }
    }


}

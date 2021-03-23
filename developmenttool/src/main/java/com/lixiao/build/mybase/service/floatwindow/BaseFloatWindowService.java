package com.lixiao.build.mybase.service.floatwindow;

import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;

import com.lixiao.build.mybase.service.BaseService;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/1/14 0014 9:51
 */
public abstract class BaseFloatWindowService extends BaseService {
    private Context context;
    private WindowManager windowManager;
    private BaseFloatView baseFloatView;
    protected abstract void init(WindowManager windowManager);
    protected abstract BaseFloatView getBaseFloatView();



    @Override
    public void init() {
        context=getBaseContext();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        init(windowManager);
        baseFloatView=getBaseFloatView();
    }

    @Override
    public void start(Intent intent, int flags, int startId) {

    }

    @Override
    public void close() {
        if(null!=baseFloatView){
            baseFloatView.close();
        }
    }
}

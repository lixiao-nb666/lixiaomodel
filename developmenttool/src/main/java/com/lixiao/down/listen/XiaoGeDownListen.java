package com.lixiao.down.listen;



import com.lixiao.down.bean.XiaoGeDownNeedInfoBean;

import java.util.ArrayList;
import java.util.List;

public class XiaoGeDownListen implements XiaoGeDownListenSubject {
    private static XiaoGeDownListen baseSubscriptionSubject;
    private List<XiaoGeDownListenObserver> observerList = new ArrayList<>();

    public static XiaoGeDownListen getInstance() {
        if (null == baseSubscriptionSubject) {
            synchronized (XiaoGeDownListen.class) {
                if (null == baseSubscriptionSubject) {
                    baseSubscriptionSubject = new XiaoGeDownListen();
                }
            }
        }
        return baseSubscriptionSubject;
    }

    @Override
    public void addObserver(XiaoGeDownListenObserver baseObserver) {
        observerList.add(baseObserver);
    }

    @Override
    public void removeObserver(XiaoGeDownListenObserver baseObserver) {
        observerList.remove(baseObserver);
    }

    @Override
    public void close() {
        observerList.clear();
    }

    @Override
    public void ready(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
        for (XiaoGeDownListenObserver observer : observerList) {
            observer.ready(xiaoGeDownNeedInfoBean);
        }
    }

    @Override
    public void err(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, String err) {
        for (XiaoGeDownListenObserver observer : observerList) {
            observer.err(xiaoGeDownNeedInfoBean,err);
        }
    }

    @Override
    public void statuStr(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, String statuStr) {
        for (XiaoGeDownListenObserver observer : observerList) {
            observer.statuStr(xiaoGeDownNeedInfoBean,statuStr);
        }
    }

    @Override
    public void start(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
        for (XiaoGeDownListenObserver observer : observerList) {
            observer.start(xiaoGeDownNeedInfoBean);
        }
    }

    @Override
    public void downProgress(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, float progress, String downloadSpeed, long needTime) {
        for (XiaoGeDownListenObserver observer : observerList) {
            observer.downProgress(xiaoGeDownNeedInfoBean,progress,downloadSpeed,needTime);
        }
    }

    @Override
    public void downOver(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
        for (XiaoGeDownListenObserver observer : observerList) {
            observer.downOver(xiaoGeDownNeedInfoBean);
        }
    }

    @Override
    public void onPause(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
        for (XiaoGeDownListenObserver observer : observerList) {
            observer.onPause(xiaoGeDownNeedInfoBean);
        }
    }

    @Override
    public void onCancel(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
        for (XiaoGeDownListenObserver observer : observerList) {
            observer.onCancel(xiaoGeDownNeedInfoBean);
        }
    }



}

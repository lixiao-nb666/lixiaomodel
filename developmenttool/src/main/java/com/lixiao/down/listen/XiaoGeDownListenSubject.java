package com.lixiao.down.listen;


import com.lixiao.down.bean.XiaoGeDownNeedInfoBean;


public interface XiaoGeDownListenSubject {
    public void addObserver(XiaoGeDownListenObserver baseObserver);

    public void removeObserver(XiaoGeDownListenObserver baseObserver);

    public void close();

    public void ready(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean);

    public void err(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, String err);

    public void statuStr(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, String statuStr);

    public void start(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean);

    public void downProgress(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, float progress, String downloadSpeed, long needTime);

    public void downOver(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean);

    public void onPause(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean);

    public void onCancel(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean);
}

package com.lixiao.down.listen;


import com.lixiao.down.bean.XiaoGeDownNeedInfoBean;


public interface XiaoGeDownListenObserver {

    public void ready(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean);

    public void start(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean);

    public void downProgress(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, float progress, String downloadSpeed, long needTime);


    public void downOver(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean);

    public void onPause(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean);

    public void onCancel(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean);


    public void statuStr(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, String statuStr);

    public void err(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, String err);
}

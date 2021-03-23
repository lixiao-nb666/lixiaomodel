package com.lixiao.down.service.util;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.lixiao.down.bean.ResultXiaoGeDownNeedInfoBean;
import com.lixiao.down.bean.XiaoGeDownNeedInfoBean;
import com.lixiao.down.config.XiaoGeDownLoaderConfig;
import com.lixiao.down.listen.XiaoGeDownListen;
import com.lixiao.http.okdown.OkDownSingleThread;
import com.lixiao.http.okdown.OkDownSingleThreadListen;

import java.util.HashMap;
import java.util.Map;


public class XiaoGeDownLoaderServiceUtil {
    private final String tag = getClass().getName() + ">>>>";
    private OkDownSingleThreadListen okDownSingleThreadListen = new OkDownSingleThreadListen() {
        @Override
        public void canRemove(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
            try {
                nowStartThreadMap.remove(xiaoGeDownNeedInfoBean.getDownUrl());
                checkToStart();
            } catch (Exception e) {
            }
        }

        @Override
        public void downErr(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
            try {
                xiaoGeDownNeedInfoBean.setDownErrNumb(xiaoGeDownNeedInfoBean.getDownErrNumb() + 1);
                if (xiaoGeDownNeedInfoBean.getDownErrNumb() < XiaoGeDownLoaderConfig.err_redown_numb) {
                    needDownList.add(xiaoGeDownNeedInfoBean);
                }
                nowStartThreadMap.remove(xiaoGeDownNeedInfoBean.getDownUrl());
                checkToStart();
            } catch (Exception e) {
            }
        }
    };
    //需要下载的信息集合
    private volatile ResultXiaoGeDownNeedInfoBean needDownList = new ResultXiaoGeDownNeedInfoBean();
    //正在下载的信息集合
    private volatile Map<String, OkDownSingleThread> nowStartThreadMap = new HashMap<>();

    public void close() {
        try {
            for (String k : nowStartThreadMap.keySet()) {
                nowStartThreadMap.get(k).cancel();
            }
            nowStartThreadMap.clear();
            needDownList.getDownNeedInfoBeanList().clear();
        } catch (Exception e) {
        }
    }


    public void add(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
        try {
            checkToAdd(xiaoGeDownNeedInfoBean);
            checkToStart();
        } catch (Exception e) {
        }
    }

    public void add(ResultXiaoGeDownNeedInfoBean resultXiaoGeDownNeedInfoBean) {
        try {
            for (XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean : resultXiaoGeDownNeedInfoBean.getDownNeedInfoBeanList()) {
                checkToAdd(xiaoGeDownNeedInfoBean);
            }
            checkToStart();
        } catch (Exception e) {
        }
    }

    public void cancel(String downUrl) {
        try {
            if (TextUtils.isEmpty(downUrl)) {
                return;
            }
            OkDownSingleThread okDownSingleThread = nowStartThreadMap.get(downUrl);
            if (null != okDownSingleThread) {
                okDownSingleThread.cancel();
            }
            nowStartThreadMap.remove(downUrl);
            for (int i = 0; i < needDownList.getDownNeedInfoBeanList().size(); i++) {
                XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean = needDownList.getDownNeedInfoBeanList().get(i);
                if (downUrl.equals(xiaoGeDownNeedInfoBean.getDownUrl())) {
                    return;
                }
            }
        } catch (Exception e) {
        }
    }

    public void cancelAll() {
        try {
            for (String k : nowStartThreadMap.keySet()) {
                nowStartThreadMap.get(k).cancel();
            }
            nowStartThreadMap.clear();
            needDownList.getDownNeedInfoBeanList().clear();
        } catch (Exception e) {
        }
    }

    private void checkToAdd(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
        if (null == xiaoGeDownNeedInfoBean || TextUtils.isEmpty(xiaoGeDownNeedInfoBean.getDownUrl())) {
            return;
        }
        //设置失败了0次
        xiaoGeDownNeedInfoBean.setDownErrNumb(0);
        needDownList.add(xiaoGeDownNeedInfoBean);
        //发送一个通知，准备这个内容了
        XiaoGeDownListen.getInstance().ready(xiaoGeDownNeedInfoBean);
    }

    private synchronized void checkToStart() {
        try {
            Log.i(tag, "--------------xianzai haiyou jige xuyao xiazai:" + needDownList.getDownNeedInfoBeanList().size() + "-----" + nowStartThreadMap.size());
            if (XiaoGeDownLoaderConfig.can_synchronous_down) {
                //可以同步去下载
                //启动成功到大于等于就可以了
                for (XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean : needDownList.getDownNeedInfoBeanList()) {
                    startDown(xiaoGeDownNeedInfoBean);
                }
            } else {
                //不可以同步下载
                if (nowStartThreadMap.size() >= 1) {
                    //不能同步下载，下载的数量已经大于1了，就直接返回咯
                    return;
                } else {
                    //只要能启动成功一个就返回了
                    for (int i = 0; i < needDownList.getDownNeedInfoBeanList().size(); i++) {
                        XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean = needDownList.getDownNeedInfoBeanList().get(i);
                        if (startDown(xiaoGeDownNeedInfoBean)) {
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    private boolean startDown(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
        try {
            OkDownSingleThread okDownSingleThread = new OkDownSingleThread(xiaoGeDownNeedInfoBean, okDownSingleThreadListen);
            okDownSingleThread.start();
            nowStartThreadMap.put(xiaoGeDownNeedInfoBean.getDownUrl(), okDownSingleThread);
            needDownList.getDownNeedInfoBeanList().remove(xiaoGeDownNeedInfoBean);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


}

package com.lixiao.oss.down.bean;

import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;

import java.io.Serializable;

public class OssDownInfoBean implements Serializable {
    private OSSAsyncTask task;
    private int errNumb;

    public OssDownInfoBean(OSSAsyncTask task) {
        this.task = task;
        this.errNumb=1;
    }

    public OSSAsyncTask getTask() {
        return task;
    }

    public void setTask(OSSAsyncTask task) {
        this.task = task;
    }

    public int getErrNumb() {
        return errNumb;
    }

    public void setErrNumb(int errNumb) {
        this.errNumb = errNumb;
    }

    @Override
    public String toString() {
        return "OssDownInfoBean{" +
                "task=" + task +
                ", errNumb=" + errNumb +
                '}';
    }
}

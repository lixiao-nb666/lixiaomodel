package com.lixiao.http.okhttp;

import java.io.IOException;

import okhttp3.Request;

/**
     * 数据回调接口
     */
    public interface DataCallBack {
        void requestFailure(String request, IOException e, int netbs);

        void requestSuccess(String result, int netbs) throws Exception;
    }
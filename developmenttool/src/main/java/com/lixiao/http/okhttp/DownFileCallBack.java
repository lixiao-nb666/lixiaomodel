package com.lixiao.http.okhttp;

import okhttp3.Request;

public interface DownFileCallBack {
        void downFileOk(String fileName, String filePath, String url);

        void downFileErr(Request request, String fileName, String filePath, String url, Exception e);

        void downFileProgress(String fileName, String filePath, String url, int progress);

    }
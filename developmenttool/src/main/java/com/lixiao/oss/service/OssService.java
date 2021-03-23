package com.lixiao.oss.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.lixiao.build.mybase.LG;
import com.lixiao.oss.config.OssConfig;
import com.lixiao.oss.down.OssDownUtil;
import com.lixiao.oss.event.OssServiceEventObserver;
import com.lixiao.oss.event.OssServiceEventSubscriptionSubject;
import com.lixiao.oss.upload.OssUploadUtil;


public class OssService extends Service {
    private IBinder iBinder = new OssService.ServiceBinder();
    private final String tag = getClass().getName() + ">>>>";
    private OSS oss;
    private OssUploadUtil ossUploadUtil = new OssUploadUtil();
    private OssDownUtil ossDownUtil=new OssDownUtil();
    private OssServiceEventObserver ossServiceEventObserver = new OssServiceEventObserver() {
        @Override
        public void upload(String fileName, String localFilePath) {
            LG.i(tag,"upload:"+fileName+"--------"+localFilePath);
            ossUploadUtil.upload(oss, fileName, localFilePath);
        }

        @Override
        public void cacelUpload(String localFilePath) {
            ossUploadUtil.canCel(localFilePath);
        }

        @Override
        public void down(String ossDownUrl, String fileName, String localFilePath) {

            ossDownUtil.downFile(false,oss,ossDownUrl,fileName,localFilePath);
        }

        @Override
        public void cacelDown(String ossDownUrl) {
            ossDownUtil.canCel(ossDownUrl);
        }

    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(tag, "----对象返回了");
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(tag, "启动了几次1111111111");
        OssServiceEventSubscriptionSubject.getInstence().attach(ossServiceEventObserver);

        initOss();
    }

    private void initOss() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OssConfig.getInstance().getBean().getAccessKeyId(), OssConfig.getInstance().getBean().getAccessKeySecret());
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                oss = new OSSClient(getApplicationContext(), OssConfig.getInstance().getBean().getEndpoint(), credentialProvider, conf);
                OSSLog.enableLog();
            }
        }).start();

    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        OssServiceEventSubscriptionSubject.getInstence().detach(ossServiceEventObserver);
        ossUploadUtil.close();
        ossDownUtil.close();
    }


    public class ServiceBinder extends Binder {
        public OssService getService() {
            return OssService.this;
        }
    }


}

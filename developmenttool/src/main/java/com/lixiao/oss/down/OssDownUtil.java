package com.lixiao.oss.down;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.lixiao.build.mybase.LG;
import com.lixiao.oss.config.OssConfig;
import com.lixiao.oss.listen.OssServiceDownListenSubscriptionSubject;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class OssDownUtil {

    private final String tag = getClass().getName() + ">>>>";

    private Map<String, OSSAsyncTask> taskMap = new HashMap<>();


    public void close() {
        for (OSSAsyncTask task : taskMap.values()) {
            if (task != null) {
                task.cancel();
            }
        }
        taskMap.clear();
        LG.i(tag, "lixiaochakanxiaossdown:关闭了");
    }

    /**
     *
     * @param isReDown  是否重新下载的
     * @param ossUrl
     * @param fileName
     * @param downFilePath
     */
    public void downFile(final boolean isReDown, final OSS oss, final String ossUrl, final String fileName, final String downFilePath) {
        if (TextUtils.isEmpty(ossUrl)) {
            OssServiceDownListenSubscriptionSubject.getInstence().err(fileName, downFilePath, "oss上面的文件地址为空");
        }
        if (taskMap.get(ossUrl) != null) {
            return;
        }

        String needOssUrl = ossUrl;
        GetObjectRequest get = new GetObjectRequest(OssConfig.getInstance().getBean().getBucketName(), needOssUrl);


//设置下载进度回调
        get.setProgressListener(new OSSProgressCallback<GetObjectRequest>() {
            @Override
            public void onProgress(GetObjectRequest request, long currentSize, long totalSize) {
                int progress = (int) (currentSize * 100 / totalSize);
                OssServiceDownListenSubscriptionSubject.getInstence().progress(fileName, downFilePath, progress, 0);

            }
        });
        OSSAsyncTask task = oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {

                /**
                 * 下载完成了的话就被删掉了，因为有时候下载一半，中断也会调这里
                 */
                // 请求成功
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = result.getObjectContent();
                    File file = new File(downFilePath);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    OssServiceDownListenSubscriptionSubject.getInstence().downOver(fileName, downFilePath);


                } catch (Exception e) {

                    if(isReDown){
                        File downFile = new File(downFilePath);
                        if (downFile.exists()) {
                            downFile.delete();
                        }
                    }else {
                        downFile(true,oss,ossUrl,fileName,downFilePath);
                    }
                    OssServiceDownListenSubscriptionSubject.getInstence().err(fileName, downFilePath, e.getMessage());
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                taskMap.remove(ossUrl);

            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                LG.i(tag, "lixiaochakanxiaossdown:下载错误了" + serviceException.getRawMessage());
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                if(isReDown){
                    File downFile = new File(downFilePath);
                    if (downFile.exists()) {
                        downFile.delete();
                    }
                }else {
                    downFile(true,oss,ossUrl,fileName,downFilePath);
                }
                OssServiceDownListenSubscriptionSubject.getInstence().err(fileName, downFilePath, serviceException.getRawMessage().toString());
                taskMap.remove(ossUrl);
            }
        });
        OssServiceDownListenSubscriptionSubject.getInstence().statuStr(fileName, downFilePath, "开始下载了");
        taskMap.put(ossUrl, task);

    }

    public void canCel(String fileLoadPath) {

        OSSAsyncTask task = taskMap.get(fileLoadPath);
        if (task != null) {
         task.cancel();
        }
        taskMap.remove(fileLoadPath);
    }


}

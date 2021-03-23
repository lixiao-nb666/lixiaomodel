package com.lixiao.oss.upload;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.lixiao.build.mybase.LG;
import com.lixiao.oss.config.OssConfig;
import com.lixiao.oss.listen.OssServiceUploadListenSubscriptionSubject;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class OssUploadUtil {
    private final String tag = getClass().getName() + ">>>>";

    private Map<String, OSSAsyncTask> taskMap = new HashMap<>();

    public void close() {
        taskMap.clear();
    }

    public void upload(OSS oss, final String uploadToOssPath, final String uploadFilePath) {
        if (oss == null) {
            OssServiceUploadListenSubscriptionSubject.getInstence().uploadErr(uploadFilePath, "OSS没有初始化");
            return;
        }
        if (TextUtils.isEmpty(uploadToOssPath)) {
            OssServiceUploadListenSubscriptionSubject.getInstence().uploadErr(uploadFilePath, "上传保存的云地址为空，不准上传");
            return;
        }
        if (TextUtils.isEmpty(uploadFilePath)) {
            OssServiceUploadListenSubscriptionSubject.getInstence().uploadErr(uploadFilePath, "上传的文件本地路径为空");
            return;
        }
        if(!new File(uploadFilePath).exists()){
            OssServiceUploadListenSubscriptionSubject.getInstence().uploadErr(uploadFilePath, "该文件在本地不存在");
            return;
        }
        if (TextUtils.isEmpty(OssConfig.getInstance().getBean().getOssUploadPath())) {
            OssServiceUploadListenSubscriptionSubject.getInstence().uploadErr(uploadFilePath, "本机没有上传服务器的信息，不能上传");
            return;
        }

        if (taskMap.keySet().contains(uploadFilePath)) {
            return;
        }


        String objectKey =OssConfig.getInstance().getBean().getOssUploadPath() + uploadToOssPath;

        LG.i(tag,"upload1111:"+objectKey+"--------"+uploadFilePath);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(OssConfig.getInstance().getBean().getBucketName(), objectKey, uploadFilePath);
// 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                int progress = (int) (currentSize * 100 / totalSize);
                LG.i(tag,"upload1111:"+progress);
                OssServiceUploadListenSubscriptionSubject.getInstence().uploadProgress(uploadFilePath, progress);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LG.i(tag,"upload1111:onSuccess");
                OssServiceUploadListenSubscriptionSubject.getInstence().uploadOk(uploadFilePath,uploadToOssPath);

                taskMap.remove(uploadFilePath);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                LG.i(tag,"upload1111:onErr:"+"errCode:" + serviceException.getErrorCode() + "errMsg:" + serviceException.getRawMessage());
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();

                    OssServiceUploadListenSubscriptionSubject.getInstence().uploadErr(uploadFilePath, "errCode:" + serviceException.getErrorCode() + "errMsg:" + serviceException.getRawMessage());
                }
                taskMap.remove(uploadFilePath);
//                if (serviceException != null) {
//                      // 服务异常
//                    Log.e("ErrorCode", serviceException.getErrorCode());
//                    Log.e("RequestId", serviceException.getRequestId());
//                    Log.e("HostId", serviceException.getHostId());
//                    Log.e("RawMessage", serviceException.getRawMessage());
//               }
            }
        });
        taskMap.put(uploadFilePath, task);
// task.cancel(); // 可以取消任务
// task.waitUntilFinished(); // 可以等待任务完成
    }


    public void canCel(String fileLoadPath) {
        OSSAsyncTask task = taskMap.get(fileLoadPath);
        if (task != null) {
            task.cancel();
        }
        taskMap.remove(fileLoadPath);
    }

}

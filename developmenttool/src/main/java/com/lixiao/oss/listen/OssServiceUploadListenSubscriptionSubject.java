package com.lixiao.oss.listen;

import java.util.ArrayList;
import java.util.List;

public class OssServiceUploadListenSubscriptionSubject implements OssServiceUploadListenSubject {

    private List<OssServiceUploadListenObserver> observers;
    private static OssServiceUploadListenSubscriptionSubject subscriptionSubject;

    private OssServiceUploadListenSubscriptionSubject() {
        observers = new ArrayList<>();
    }

    public static OssServiceUploadListenSubscriptionSubject getInstence() {
        if (subscriptionSubject == null) {
            synchronized (OssServiceUploadListenSubscriptionSubject.class) {
                if (subscriptionSubject == null)
                    subscriptionSubject = new OssServiceUploadListenSubscriptionSubject();
            }
        }
        return subscriptionSubject;

    }

    @Override
    public void attach(OssServiceUploadListenObserver observer) {

        observers.add(observer);

    }

    @Override
    public void detach(OssServiceUploadListenObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void uploadOk(String uploadFilePath,String fileName) {
        for(OssServiceUploadListenObserver observer:observers){
            observer.uploadOk(uploadFilePath,fileName);
        }
    }

    @Override
    public void uploadProgress(String uploadFilePath, int progress) {
        for(OssServiceUploadListenObserver observer:observers){
            observer.uploadProgress(uploadFilePath,progress);
        }
    }

    @Override
    public void uploadErr(String uploadFilePath, String errStr) {
        for(OssServiceUploadListenObserver observer:observers){
            observer.uploadErr(uploadFilePath,errStr);
        }
    }



}

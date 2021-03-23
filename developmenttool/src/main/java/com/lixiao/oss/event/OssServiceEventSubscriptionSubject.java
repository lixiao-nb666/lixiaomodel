package com.lixiao.oss.event;

import java.util.ArrayList;
import java.util.List;

public class OssServiceEventSubscriptionSubject implements OssServiceEventSubject {

    private List<OssServiceEventObserver> observers;
    private static OssServiceEventSubscriptionSubject subscriptionSubject;

    private OssServiceEventSubscriptionSubject() {
        observers = new ArrayList<>();
    }

    public static OssServiceEventSubscriptionSubject getInstence() {
        if (subscriptionSubject == null) {
            synchronized (OssServiceEventSubscriptionSubject.class) {
                if (subscriptionSubject == null)
                    subscriptionSubject = new OssServiceEventSubscriptionSubject();
            }
        }
        return subscriptionSubject;
    }

    @Override
    public void attach(OssServiceEventObserver observer) {

        observers.add(observer);

    }

    @Override
    public void detach(OssServiceEventObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void upload(String fileName, String localFilePath) {
        for(OssServiceEventObserver observer:observers){
            observer.upload(fileName,localFilePath);
        }
    }

    @Override
    public void cacelUpload(String localFilePath) {
        for(OssServiceEventObserver observer:observers){
            observer.cacelUpload(localFilePath);
        }
    }

    @Override
    public void down(String ossDownUrl, String fileName, String localFilePath) {
        for(OssServiceEventObserver observer:observers){
            observer.down(ossDownUrl,fileName,localFilePath);
        }
    }

    @Override
    public void cacelDown(String downOssUrl) {
        for(OssServiceEventObserver observer:observers){
            observer.cacelDown(downOssUrl);
        }
    }




}

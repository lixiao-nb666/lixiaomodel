package com.lixiao.oss.listen;

import java.util.ArrayList;
import java.util.List;

public class OssServiceDownListenSubscriptionSubject implements OssServiceDownListenSubject {

    private List<OssServiceDownListenObserver> observers;
    private static OssServiceDownListenSubscriptionSubject subscriptionSubject;

    private OssServiceDownListenSubscriptionSubject() {
        observers = new ArrayList<>();
    }

    public static OssServiceDownListenSubscriptionSubject getInstence() {
        if (subscriptionSubject == null) {
            synchronized (OssServiceDownListenSubscriptionSubject.class) {
                if (subscriptionSubject == null)
                    subscriptionSubject = new OssServiceDownListenSubscriptionSubject();
            }
        }
        return subscriptionSubject;

    }


    @Override
    public void attach(OssServiceDownListenObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(OssServiceDownListenObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void statuStr(String fileName, String filePath, String statuStr) {
        for(OssServiceDownListenObserver observer:observers){
            observer.statuStr(fileName,filePath,statuStr);
        }
    }

    @Override
    public void err(String fileName, String filePath, String errStr) {
        for(OssServiceDownListenObserver observer:observers){
            observer.err(fileName,filePath,errStr);
        }
    }

    @Override
    public void downOver(String fileName, String filePath) {
        for(OssServiceDownListenObserver observer:observers){
            observer.downOver(fileName,filePath);
        }
    }

    @Override
    public void progress(String fileName, String filePath, int progress, float downloadSpeed) {
        for(OssServiceDownListenObserver observer:observers){
            observer.progress(fileName,filePath,progress,downloadSpeed);
        }
    }


}

package com.lixiao.build.util.systemapp.observer;

import java.util.ArrayList;
import java.util.List;

public class PackageManagerSubscriptionSubject implements PackageManagerSubject {
    private static PackageManagerSubscriptionSubject baseSubscriptionSubject;
    private List<PackageManagerObserver> observerList = new ArrayList<>();

    private PackageManagerSubscriptionSubject() {
    }

    public static PackageManagerSubscriptionSubject getInstance() {
        if (null == baseSubscriptionSubject) {
            synchronized (PackageManagerSubscriptionSubject.class) {
                if (null == baseSubscriptionSubject) {
                    baseSubscriptionSubject = new PackageManagerSubscriptionSubject();
                }
            }
        }
        return baseSubscriptionSubject;
    }

    @Override
    public void addObserver(PackageManagerObserver baseObserver) {
        observerList.add(baseObserver);
    }

    @Override
    public void removeObserver(PackageManagerObserver baseObserver) {
        observerList.remove(baseObserver);
    }

    @Override
    public void close() {
        observerList.clear();
    }


    @Override
    public void update(PackageManagerType eventBs, Object object) {
        for (PackageManagerObserver observer : observerList) {
            observer.update(eventBs, object);
        }
    }
}

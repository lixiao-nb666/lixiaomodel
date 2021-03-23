package com.lixiao.down.event;

import java.util.ArrayList;
import java.util.List;

public class XiaoGeDownEvent implements XiaoGeDownEventSubject {
    private static XiaoGeDownEvent baseSubscriptionSubject;
    private List<XiaoGeDownEventObserver> observerList = new ArrayList<>();

    public static XiaoGeDownEvent getInstance() {
        if (null == baseSubscriptionSubject) {
            synchronized (XiaoGeDownEvent.class) {
                if (null == baseSubscriptionSubject) {
                    baseSubscriptionSubject = new XiaoGeDownEvent();
                }
            }
        }
        return baseSubscriptionSubject;
    }

    @Override
    public void addObserver(XiaoGeDownEventObserver baseObserver) {
        observerList.add(baseObserver);
    }


    @Override
    public void removeObserver(XiaoGeDownEventObserver baseObserver) {
        observerList.remove(baseObserver);
    }

    @Override
    public void close() {
        observerList.clear();
    }


    @Override
    public void update(XiaoGeDownEventType eventBs, Object object) {
        for (XiaoGeDownEventObserver observer : observerList) {
            observer.update(eventBs, object);
        }
    }
}

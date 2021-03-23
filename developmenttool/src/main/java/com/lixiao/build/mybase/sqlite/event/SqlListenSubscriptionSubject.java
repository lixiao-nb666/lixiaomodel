package com.lixiao.build.mybase.sqlite.event;

import java.util.ArrayList;
import java.util.List;

public class SqlListenSubscriptionSubject implements SqlListenSubject {

    private List<SqlListenObserver> observers;
    private static SqlListenSubscriptionSubject subscriptionSubject;

    private SqlListenSubscriptionSubject() {
        observers = new ArrayList<>();
    }

    public static SqlListenSubscriptionSubject getInstence() {
        if (subscriptionSubject == null) {
            synchronized (SqlListenSubscriptionSubject.class) {
                if (subscriptionSubject == null)
                    subscriptionSubject = new SqlListenSubscriptionSubject();
            }
        }
        return subscriptionSubject;
    }

    @Override
    public void attach(SqlListenObserver observer) {

        observers.add(observer);

    }

    @Override
    public void detach(SqlListenObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void add(Class cls, Object object, boolean isOk) {
        for(SqlListenObserver observer:observers){
            observer.add( cls,  object, isOk);
        }
    }

    @Override
    public void update(Class cls, Object object, boolean isOk) {
        for(SqlListenObserver observer:observers){
            observer.update( cls,  object, isOk);
        }
    }

    @Override
    public void remove(Class cls, String key, String vue, boolean isOk) {
        for(SqlListenObserver observer:observers){
            observer.remove( cls,key,vue, isOk);
        }
    }

    @Override
    public void err(String errStr) {
        for(SqlListenObserver observer:observers){
            observer.err(errStr);
        }
    }


}

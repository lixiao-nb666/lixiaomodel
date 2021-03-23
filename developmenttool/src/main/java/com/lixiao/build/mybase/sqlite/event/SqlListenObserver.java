package com.lixiao.build.mybase.sqlite.event;


/**
 * Created by xiefuning on 2017/5/12.
 * about:
 */

public interface SqlListenObserver {

    public void add(Class cls,Object object,boolean isOk);

    public void remove(Class cls,String key,String vue,boolean isOk);

    public void update(Class cls,Object object,boolean isOk);

    public void err(String errStr);

}

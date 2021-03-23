package com.lixiao.build.mybase.sqlite.event;


public interface SqlListenSubject {
    /**
     * 增加订阅者
     *
     * @param observer
     */
    public void attach(SqlListenObserver observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    public void detach(SqlListenObserver observer);

    public void add(Class cls,Object object,boolean isOk);

    public void update(Class cls,Object object,boolean isOk);

    public void remove(Class cls,String key,String vue,boolean isOk);

    public void err(String errStr);
}

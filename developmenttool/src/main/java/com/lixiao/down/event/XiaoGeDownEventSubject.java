package com.lixiao.down.event;

public interface XiaoGeDownEventSubject {
    public void addObserver(XiaoGeDownEventObserver baseObserver);

    public void removeObserver(XiaoGeDownEventObserver baseObserver);

    public void close();

    public void update(XiaoGeDownEventType eventBs, Object object);
}

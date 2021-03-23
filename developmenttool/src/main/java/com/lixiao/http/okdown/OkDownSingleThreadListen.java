package com.lixiao.http.okdown;


import com.lixiao.down.bean.XiaoGeDownNeedInfoBean;

public interface OkDownSingleThreadListen {

    public void canRemove(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean);

    public void downErr(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean);
}

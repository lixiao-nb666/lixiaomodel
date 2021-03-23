package com.newbee.lixiaomodel;

import com.lixiao.build.mybase.appliction.BaseApplication;
import com.lixiao.down.config.XiaoGeDownLoaderConfig;
import com.lixiao.down.manager.XiaoGeDownLoaderManager;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/2/1 0001 11:03
 */
public class MyApplication extends BaseApplication {
    @Override
    protected void init() {
        XiaoGeDownLoaderConfig.needDownEncoded=false;
        XiaoGeDownLoaderManager.getInstance().startService(getContext());
//        OssConfig.getInstance().setBean(MyOss.getOssConfigBean());
//        OssManager.getInstance(getContext());
    }

    @Override
    protected void needClear(String str) {
    }

    @Override
    protected void close() {
        XiaoGeDownLoaderManager.getInstance().stopService();
//        OssManager.getInstance(getContext()).close();
    }
}

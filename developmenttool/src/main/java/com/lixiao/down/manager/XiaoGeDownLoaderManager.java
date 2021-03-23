package com.lixiao.down.manager;

import android.content.Context;

import com.lixiao.down.config.XiaoGeDownLoaderConfig;
import com.lixiao.down.service.XiaoGeDownLoaderServiceDao;


public class XiaoGeDownLoaderManager {
    private final String tag = getClass().getName() + ">>>>";
    private static XiaoGeDownLoaderManager xiaoGeDownLoaderManager;

    private XiaoGeDownLoaderManager() {
    }

    public static XiaoGeDownLoaderManager getInstance() {
        if (null == xiaoGeDownLoaderManager) {
            synchronized (XiaoGeDownLoaderManager.class) {
                if (null == xiaoGeDownLoaderManager) {
                    xiaoGeDownLoaderManager = new XiaoGeDownLoaderManager();
                }
            }
        }
        return xiaoGeDownLoaderManager;
    }

    public void setConfig(boolean can_synchhronous_down, boolean downFileCheckExists, String defParentFilePath) {
        XiaoGeDownLoaderConfig.can_synchronous_down = can_synchhronous_down;
        XiaoGeDownLoaderConfig.downCheckFileExist = downFileCheckExists;
        XiaoGeDownLoaderConfig.defParentFilePath = defParentFilePath;
    }


    private XiaoGeDownLoaderServiceDao serviceDao;

    public void startService(Context context) {
        serviceDao = new XiaoGeDownLoaderServiceDao(context.getApplicationContext());
        serviceDao.startService();
    }

    public void stopService() {
        if (null != serviceDao) {
            serviceDao.stopService();
        }
    }


}

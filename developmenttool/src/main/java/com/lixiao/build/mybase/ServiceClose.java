package com.lixiao.build.mybase;

import android.app.Service;

import java.util.ArrayList;
import java.util.List;

public class  ServiceClose {

    private static List<Service> list;
    private static ServiceClose activityClose;

    private ServiceClose() {
        list = new ArrayList<Service>();
    }

    // 单例模式得到退出APP的实例
    public static ServiceClose getActivityClose() {
        if (activityClose == null) {
            activityClose = new ServiceClose();
        }
        return activityClose;
    }

    // 得到所有进行过，并且没有退出活动的实例
    public List<Service> getlist() {
        if (list == null) {
            activityClose = new ServiceClose();
        }
        return list;
    }

    // 添加所有活动实例
    public void add(Service service) {
        list.add(service);

    }

    // 删除活动实例
    public void delete(Service service) {
        LG.i("退出了一个活动>>>>", service);
        list.remove(service);
    }

    // 关闭所有的服务
    public void closeAll() {
        if (list != null && list.size() > 0)
            for (int i = 0; i < list.size(); i++) {
                Service service = list.get(i);
                service.onDestroy();
            }
    }


}

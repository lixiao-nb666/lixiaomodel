package com.lixiao.build.mybase.activity.welcome.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/2/20 0020 16:58
 */
public class WelcomeInfoBean implements Serializable {
    public  List<String> permissionList;


    public List<String> getPermissionList() {
        if(null==permissionList){
            permissionList=new ArrayList<>();
        }
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public String toString() {
        return "WelcomeInfoBean{" +
                "permissionList=" + permissionList +
                '}';
    }
}

package com.lixiao.build.mybase.activity.welcome.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/2/20 0020 16:58
 */
public class WelcomeInfoBean implements Serializable {
    public  List<String> permissionList;
    public long showTime;
    public  int backGroundRsId;
    public int iconRsId;

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

    public long getShowTime() {
        return showTime;
    }

    public void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    public int getBackGroundRsId() {
        return backGroundRsId;
    }

    public void setBackGroundRsId(int backGroundRsId) {
        this.backGroundRsId = backGroundRsId;
    }

    public int getIconRsId() {
        return iconRsId;
    }

    public void setIconRsId(int iconRsId) {
        this.iconRsId = iconRsId;
    }

    @Override
    public String toString() {
        return "WelcomeInfoBean{" +
                "permissionList=" + permissionList +
                ", showTime=" + showTime +
                ", backGroundRsId=" + backGroundRsId +
                ", iconRsId=" + iconRsId +
                '}';
    }
}

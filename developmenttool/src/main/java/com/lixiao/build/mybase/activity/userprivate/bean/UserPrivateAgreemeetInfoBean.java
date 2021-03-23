package com.lixiao.build.mybase.activity.userprivate.bean;

import java.io.Serializable;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/2/22 0022 10:29
 */
public class UserPrivateAgreemeetInfoBean implements Serializable {
    private String appName;
    private String comPanyName;
    private String time;

    public String getComPanyName() {
        return comPanyName;
    }

    public void setComPanyName(String comPanyName) {
        this.comPanyName = comPanyName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "UserPrivateAgreemeetInfoBean{" +
                "comPanyName='" + comPanyName + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

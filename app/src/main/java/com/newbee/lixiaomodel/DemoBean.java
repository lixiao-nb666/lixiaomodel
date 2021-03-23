package com.newbee.lixiaomodel;

import java.io.Serializable;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/18 0018 14:38
 */
public class DemoBean implements Serializable {
    private long id;
    private String demoStr;
    private String demoStr1;
    private int demoInt;
    private long demoLong;
    private boolean demoBoolean;
    private boolean isDemo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDemoStr() {
        return demoStr;
    }

    public void setDemoStr(String demoStr) {
        this.demoStr = demoStr;
    }

    public String getDemoStr1() {
        return demoStr1;
    }

    public void setDemoStr1(String demoStr1) {
        this.demoStr1 = demoStr1;
    }

    public int getDemoInt() {
        return demoInt;
    }

    public void setDemoInt(int demoInt) {
        this.demoInt = demoInt;
    }

    public long getDemoLong() {
        return demoLong;
    }

    public void setDemoLong(long demoLong) {
        this.demoLong = demoLong;
    }

    public boolean isDemoBoolean() {
        return demoBoolean;
    }

    public void setDemoBoolean(boolean demoBoolean) {
        this.demoBoolean = demoBoolean;
    }

    public boolean isDemo() {
        return isDemo;
    }

    public void setDemo(boolean demo) {
        isDemo = demo;
    }


    @Override
    public String toString() {
        return "DemoBean{" +
                "id='" + id + '\'' +
                ", demoStr='" + demoStr + '\'' +
                ", demoStr1='" + demoStr1 + '\'' +
                ", demoInt=" + demoInt +
                ", demoLong=" + demoLong +
                ", demoBoolean=" + demoBoolean +
                ", isDemo=" + isDemo +
                '}';
    }
}

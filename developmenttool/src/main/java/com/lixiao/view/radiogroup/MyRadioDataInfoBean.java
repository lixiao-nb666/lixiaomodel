package com.lixiao.view.radiogroup;

import java.io.Serializable;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/24 0024 11:44
 */
public class MyRadioDataInfoBean implements Serializable {
    private String type;
    private String showText;
    private int bgRs;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShowText() {
        return showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public int getBgRs() {
        return bgRs;
    }

    public void setBgRs(int bgRs) {
        this.bgRs = bgRs;
    }

    @Override
    public String toString() {
        return "MyRadioDataInfoBean{" +
                "type='" + type + '\'' +
                ", showText='" + showText + '\'' +
                ", bgRs=" + bgRs +
                '}';
    }
}

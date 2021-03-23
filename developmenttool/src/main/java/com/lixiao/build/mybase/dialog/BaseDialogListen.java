package com.lixiao.build.mybase.dialog;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/9/30 0030 14:15
 */
public interface BaseDialogListen {
    public void getWAndH(int w, int h);

    public void event(String eventType, Object... objects);
}

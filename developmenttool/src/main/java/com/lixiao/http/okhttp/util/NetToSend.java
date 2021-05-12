package com.lixiao.http.okhttp.util;


import android.text.TextUtils;


import com.lixiao.build.gson.MyGson;
import com.lixiao.http.okhttp.DataCallBack;
import com.lixiao.http.okhttp.OkHttpManager;
import com.lixiao.http.okhttp.bean.Send;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiefuning on 2017/4/12.
 * about:
 */

public class NetToSend {
    private static NetToSend send;


    private NetToSend() {

    }

    public static NetToSend getInstance() {
        if (send == null) {
            send = new NetToSend();
        }
        return send;
    }

    private Send getSendBean(String sendStr, String object) {
        return new Send(sendStr, object);
    }

    private Map<String, String> getMap(Send send) {
        Map<String, String> map = new HashMap<>();
        map.put("send", MyGson.getInstance().toGsonStr(send));
        return map;
    }

    public void setSendToRequest(String url, Send send, DataCallBack callBack, int netbs) {
        OkHttpManager.postAsync(url, getMap(send), callBack, netbs);
    }

    public void setSendToRequest(String url,String ss, String gs, DataCallBack callBack, int netbs) {
        OkHttpManager.postAsync(url, getMap(new Send(ss,gs)), callBack, netbs);
    }

    /**
     * 得到该设备的
     */
    public void getTime(DataCallBack callBack, int netbs) {
        Send send = new Send();
        OkHttpManager.postAsync("http://www.bjtime.cn", getMap(send), callBack, netbs);
    }



}

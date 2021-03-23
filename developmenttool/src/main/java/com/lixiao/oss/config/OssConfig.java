package com.lixiao.oss.config;


import android.text.TextUtils;

import com.lixiao.build.gson.MyGson;
import com.lixiao.build.mybase.share.MyShare;
import com.lixiao.oss.bean.OssConfigBean;

public class OssConfig {
    private final String tag=getClass().getSimpleName()+">>>>";
    private static OssConfig ossConfig;
    private OssConfigBean bean;
    private OssConfig(){}

    public static OssConfig getInstance(){
        if(null==ossConfig){
            synchronized (OssConfig.class){
                if(null==ossConfig){
                    ossConfig=new OssConfig();
                }
            }
        }
        return ossConfig;
    }

    public OssConfigBean getBean() {
        if(null==bean){
            String shareStr= MyShare.getInstance().getString(tag);
            if(!TextUtils.isEmpty(shareStr)){
                bean= MyGson.getInstance().fromJson(shareStr,OssConfigBean.class);
            }
        }
        return bean;
    }

    public void setBean(OssConfigBean bean) {
        this.bean = bean;
        if(null!=this.bean){
            MyShare.getInstance().putString(tag,MyGson.getInstance().toGsonStr(this.bean));
        }
    }



}

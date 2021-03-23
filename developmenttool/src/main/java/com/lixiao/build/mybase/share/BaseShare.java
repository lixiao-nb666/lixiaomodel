package com.lixiao.build.mybase.share;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.lixiao.build.mybase.appliction.BaseApplication;


public class BaseShare {
    private final String tag=getClass().getSimpleName();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public BaseShare() {
        setSPXml();
    }



    // 创建共享存储
    private void setSPXml() {
        sharedPreferences = BaseApplication.getContext().getSharedPreferences("xiaoge_sp_xml_"+tag,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putString(String K, String V) {
        editor.putString(K, V);
        editor.commit();
    }

    // 取出共享文件的String
    public String getString(String k) {
        String data = sharedPreferences.getString(k, "");
        return data;
    }

    // 取出共享文件的String
    public void clear() {
        editor.clear();
        editor.commit();
    }



}

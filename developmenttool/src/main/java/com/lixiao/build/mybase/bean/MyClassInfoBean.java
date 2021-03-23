package com.lixiao.build.mybase.bean;

import android.text.TextUtils;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.util.ReflectUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class MyClassInfoBean implements Serializable {
    public Map<String, Method> getMethodMap = new HashMap<>();
    public Map<String, Method> setMethodMap = new HashMap<>();
    public Map<String, Field> fieldMap = new HashMap<>();
    public String[] fNameS;

    public MyClassInfoBean(Class cc) {
        Field[] ff = cc.getDeclaredFields();
        for (Field field : ff) {

            fieldMap.put(field.getName(), field);
//            LG.i("shenmegui","------kankanjutilo："+field.getType()+"---------"+field.getName());
        }
        Method[] mm = cc.getDeclaredMethods();
        for (Method m : mm) {
            String mName = m.getName();
            for (Field f : ff) {
                String fName = f.getName();
                if (!TextUtils.isEmpty(fName)) {
                    if (ReflectUtil.checkMethodIsSetByField(mName, fName)) {
                        // 这就是SET方法
                        setMethodMap.put(fName, m);
                        LG.i("shenmegui","------kankanjutilo："+fName+"---------"+m.getName());
                        break;
                    } else if (ReflectUtil.checkMethodIsGetByField(mName, fName)) {
                        // 这就是GET方法
                        getMethodMap.put(fName, m);
                        break;
                    }
                }
            }
        }
        fNameS=new String[getMethodMap.size()];
        int i=0;
        for(String getStr:getMethodMap.keySet()){
            fNameS[i]=getStr;
            i++;
        }

    }


}

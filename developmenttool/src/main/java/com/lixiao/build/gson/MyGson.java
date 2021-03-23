package com.lixiao.build.gson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lixiao.build.mybase.LG;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyGson {
    private static MyGson myGson;
    private Gson gson;

    private MyGson() {
        gson = new Gson();
    }

    public static MyGson getInstance() {
        if (myGson == null) {
            synchronized (MyGson.class) {
                if (myGson == null) {
                    myGson = new MyGson();
                }
            }
        }
        return myGson;
    }

    public String toGsonStr(Object object) {
        return gson.toJson(object);
    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        try {

            return gson.fromJson(json, classOfT);
        } catch (Exception e) {
            LG.i("cakanjiexibaocuo", e.toString());
            return null;
        }

    }


    public <T> T fromJson(String json, Type type) {
        try {

            return gson.fromJson(json,type);
        } catch (Exception e) {
            LG.i("cakanjiexibaocuo", e.toString());
            return null;
        }

    }

    public Gson getGson(){

        return gson;
    }


    public <T> List<T> getObjectList(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            JsonArray arry = new JsonParser().parse(jsonString)
                    .getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

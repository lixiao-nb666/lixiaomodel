package com.lixiao.build.mybase.popupwindow.util;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;

import com.lixiao.build.mybase.popupwindow.BasePopupWindow;

import java.util.HashMap;
import java.util.Map;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/7/15 0015 10:15
 */
public class PopupManagerUtil {

    private Listen listen;
    private Map<String, BasePopupWindow> dialogMap=new HashMap<>();

    public PopupManagerUtil(Listen listen){
        this.listen=listen;
    }


    public void create(String popupType){
        if(TextUtils.isEmpty(popupType)){
            listen.err("create popup but type == null");
            return;
        }
        BasePopupWindow basePopup=dialogMap.get(popupType);
        if(null==basePopup){
            basePopup=listen.createPopupWindow(popupType);
            if(null==popupType){
                return;
            }
            dialogMap.put(popupType,basePopup);
        }

    }

    public void showByCenter(String popupType, View view, int x, int y,Object... objects){
        if(TextUtils.isEmpty(popupType)){
            listen.err("show popup but type == null");
            return;
        }
        BasePopupWindow basePopupWindow=dialogMap.get(popupType);
        if(null==basePopupWindow){
            basePopupWindow=listen.createPopupWindow(popupType);
            if(null==basePopupWindow){
                return;
            }
            dialogMap.put(popupType,basePopupWindow);
        }
        basePopupWindow.showByCenter(view,x,y,objects);
    }

    public void showByTop(String popupType, View view, int x, int y,Object... objects){
        if(TextUtils.isEmpty(popupType)){
            listen.err("show popup but type == null");
            return;
        }
        BasePopupWindow basePopupWindow=dialogMap.get(popupType);
        if(null==basePopupWindow){
            basePopupWindow=listen.createPopupWindow(popupType);
            if(null==basePopupWindow){
                return;
            }
            dialogMap.put(popupType,basePopupWindow);
        }
        basePopupWindow.showByTop(view,x,y,objects);
    }

    public void showByDown(String popupType, View view, int x, int y,Object... objects){
        if(TextUtils.isEmpty(popupType)){
            listen.err("show popup but type == null");
            return;
        }
        BasePopupWindow basePopupWindow=dialogMap.get(popupType);
        if(null==basePopupWindow){
            basePopupWindow=listen.createPopupWindow(popupType);
            if(null==basePopupWindow){
                return;
            }
            dialogMap.put(popupType,basePopupWindow);
        }
        basePopupWindow.showByDown(view,x,y,objects);
    }

    public void hide(String popupType){
        if(TextUtils.isEmpty(popupType)){
            listen.err("hide popup but type == null");
            return;
        }
        BasePopupWindow basePopup=dialogMap.get(popupType);
        if(null!=basePopup){
            basePopup.hide();
        }
    }


    public void cancel(String popupType){
        if(TextUtils.isEmpty(popupType)){
            listen.err("cancel popup but type == null");
            return;
        }
        BasePopupWindow basePopup=dialogMap.get(popupType);
        if(null!=basePopup){
            basePopup.close();;
            basePopup=null;
        }
    }




    public void close(){
        for(BasePopupWindow basePopup:dialogMap.values()){
            if(null!=basePopup){
                basePopup.close();
                basePopup=null;
            }
        }
        dialogMap.clear();

    }


    public interface Listen{
        public  void err(String err);

        public BasePopupWindow createPopupWindow(String popupType);

    }

}

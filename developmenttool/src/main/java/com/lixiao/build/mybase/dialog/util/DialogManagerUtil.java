package com.lixiao.build.mybase.dialog.util;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.lixiao.build.mybase.dialog.BaseDialog;

import java.util.HashMap;
import java.util.Map;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/7/15 0015 10:15
 */
public class DialogManagerUtil {

    private Listen listen;
    private Map<String, BaseDialog> dialogMap=new HashMap<>();

    public DialogManagerUtil(Listen listen){
        this.listen=listen;
    }


    public void create(String dialogType){
        if(TextUtils.isEmpty(dialogType)){
            listen.err("create dialog but dialogtype == null");
            return;
        }
        BaseDialog baseDialog=dialogMap.get(dialogType);
        if(null==baseDialog){
            baseDialog=listen.createDialog(dialogType);
            if(null==baseDialog){
                return;
            }
            dialogMap.put(dialogType,baseDialog);
        }

    }

    public void show(String dialogType,Object... objects){
        if(TextUtils.isEmpty(dialogType)){
            listen.err("show dialog but dialogtype == null");
            return;
        }
        BaseDialog baseDialog=dialogMap.get(dialogType);
        if(null==baseDialog){
            baseDialog=listen.createDialog(dialogType);
            if(null==baseDialog){
                return;
            }
            dialogMap.put(dialogType,baseDialog);
        }
        baseDialog.show(objects);
    }

    public void hide(String dialogType){
        if(TextUtils.isEmpty(dialogType)){
            listen.err("hide dialog but dialogtype == null");
            return;
        }
        BaseDialog baseDialog=dialogMap.get(dialogType);
        if(null!=baseDialog){
            baseDialog.hide();
        }
    }


    public void cancel(String dialogType){
        if(TextUtils.isEmpty(dialogType)){
            listen.err("canceldialog but dialogtype == null");
            return;
        }
        BaseDialog baseDialog=dialogMap.get(dialogType);
        if(null!=baseDialog){
            baseDialog.cancel();
            baseDialog=null;
        }
    }




    public void close(){
        for(BaseDialog baseDialog:dialogMap.values()){
            if(null!=baseDialog){
                baseDialog.close();
                baseDialog=null;
            }
        }
        dialogMap.clear();

    }


    public interface Listen{
        public  void err(String err);

        public BaseDialog createDialog(String dialogType);

    }

}

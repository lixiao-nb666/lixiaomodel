package com.lixiao.build.util.systemapp.bean;

import android.text.TextUtils;

import com.lixiao.build.mybase.share.MyShare;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultSystemAppInfoBean implements Serializable {
    private List<SystemAppInfoBean> appList;

    public ResultSystemAppInfoBean() {
        appList = new ArrayList<>();
    }

    public List<SystemAppInfoBean> getAppList() {
        return appList;
    }

    public void setAppList(List<SystemAppInfoBean> appList) {
        this.appList = appList;
    }

    @Override
    public String toString() {
        return "ResultSystemAppInfoBean{" +
                "appList=" + appList +
                '}';
    }

    public boolean add(SystemAppInfoBean appInfoBean) {
        if (null == appInfoBean || TextUtils.isEmpty(appInfoBean.getPakeageName())) {
            return false;
        }
        if (null == appList) {
            appList = new ArrayList<>();
        }
        boolean indexIsExist=false;
        int index= MyShare.getInstance().getAppIndex(appInfoBean.getPakeageName());
        if(index==-1){
            indexIsExist=true;
        }
        int maxIndex=-1;
        for (SystemAppInfoBean app : appList) {
            if (null != app && appInfoBean.getPakeageName().equals(app.getPakeageName())) {
                return false;
            }
            if(app.getIndex()>maxIndex){
                maxIndex=app.getIndex();
            }
            if(index==app.getIndex()){
                indexIsExist=true;
            }
        }
        if(indexIsExist){
            index=maxIndex+1;
        }
        appInfoBean.setIndex(index);
        appList.add(appInfoBean);
        return true;
    }

    public boolean remove(SystemAppInfoBean appInfoBean) {
        if (null == appInfoBean || TextUtils.isEmpty(appInfoBean.getPakeageName())) {
            return false;
        }
        if (null == appList) {
            return false;
        }
        return appList.remove(appInfoBean);
    }

    public boolean replace(SystemAppInfoBean appInfoBean, SystemAppInfoBean updateAppInfoBean) {

        if (null == appInfoBean || TextUtils.isEmpty(appInfoBean.getPakeageName()) || null == updateAppInfoBean || TextUtils.isEmpty(updateAppInfoBean.getPakeageName())) {
            return false;
        }

        if (null == appList) {
            return false;
        }
        int index = appList.indexOf(appInfoBean);

        if (index != -1) {

            for (SystemAppInfoBean app : appList) {
                if (null != app && updateAppInfoBean.getPakeageName().equals(app.getPakeageName())) {

                    return false;
                }
            }
            appList.remove(appInfoBean);
            appList.add(index, updateAppInfoBean);
            return true;
        }
        return false;
    }




    public void sort(){
        Collections.sort(appList, new Comparator<SystemAppInfoBean>() {
            @Override
            public int compare(SystemAppInfoBean o1, SystemAppInfoBean o2) {
                if(o1.getIndex()>o2.getIndex()){
                    return 1;
                }else {
                    return -1;
                }
            }
        });
    }





    public SystemAppInfoBean getAppByAdapterPosition(int position){
        try {

            for(SystemAppInfoBean app:appList){
                int index=app.getIndex();
                if(position==index){
                    return app;
                }
            }
        }catch (Exception e){
        }
        return null;
    }

    public SystemAppInfoBean getAppByPkgName(String pkgName){
        try {
            if(TextUtils.isEmpty(pkgName)){
                return null;
            }
            for(SystemAppInfoBean app:appList){
                if(pkgName.equals(app.getPakeageName())){
                    return app;
                }
            }
        }catch (Exception e){
        }
        return null;
    }
}

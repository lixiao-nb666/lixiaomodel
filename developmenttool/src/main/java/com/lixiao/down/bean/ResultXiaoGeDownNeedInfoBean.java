package com.lixiao.down.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultXiaoGeDownNeedInfoBean implements Serializable {
    private List<XiaoGeDownNeedInfoBean> downNeedInfoBeanList;

    public ResultXiaoGeDownNeedInfoBean() {
        downNeedInfoBeanList=new ArrayList<>();
    }

    public List<XiaoGeDownNeedInfoBean> getDownNeedInfoBeanList() {

        return downNeedInfoBeanList;
    }

    public void setDownNeedInfoBeanList(List<XiaoGeDownNeedInfoBean> downNeedInfoBeanList) {
        this.downNeedInfoBeanList = downNeedInfoBeanList;
    }

    @Override
    public String toString() {
        return "ResultXiaoGeDownNeedInfoBean{" +
                "downNeedInfoBeanList=" + downNeedInfoBeanList +
                '}';
    }

    public void add(XiaoGeDownNeedInfoBean downNeedInfoBean){
        if(null==downNeedInfoBeanList){
            downNeedInfoBeanList=new ArrayList<>();
        }
        downNeedInfoBeanList.add(downNeedInfoBean);
    }
}

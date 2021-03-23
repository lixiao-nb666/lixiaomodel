package com.lixiao.build.glide;



/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class NetDataModel implements IDataModel {
    private String dataModelUrl;

    public NetDataModel(String dataModelUrl) {
        this.dataModelUrl = dataModelUrl;
    }

    @Override
    public String buildDataModelUrl(int width, int height) {
        return String.format("%s?imageView2/1/w/%d/h/%d/format/jpg", dataModelUrl, width, height);
    }
}

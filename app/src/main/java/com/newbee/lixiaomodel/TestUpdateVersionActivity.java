package com.newbee.lixiaomodel;

import android.os.Build;
import android.os.RecoverySystem;
import android.view.View;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.activity.update.BaseVersionUpdateActivity;
import com.lixiao.build.mybase.activity.update.bean.VersionBean;
import com.lixiao.build.mybase.activity.update.bean.VersionQueBean;

import com.lixiao.build.util.phone.PhoneUtil;
import com.nrmyw.launcher.R;

import java.io.File;
import java.io.IOException;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/5/10 0010 20:19
 */
public class TestUpdateVersionActivity extends BaseVersionUpdateActivity {
    @Override
    public String getVersionShowName() {
        return "inno-cn system update!";
    }

    @Override
    public VersionQueBean getVersionUpdateInfo() {

        VersionQueBean versionQueBean=new VersionQueBean();

        String str=getCheckVersionStr();
        String appName="cvte_device_ota_update";
        if(str.contains("SMART_DISPLAY")){
            appName="cvte_device_ota_update";
        }else if(str.contains("cvte_s905d3_hs")){
            appName="cvte_device_hs_s905_ota_update";
        }else{
            int index=str.indexOf("_");
            if(index!=-1){
                appName=str.substring(0,index);
            }else {
                appName=str;
            }
        }
        versionQueBean.setAppName(appName);

        return versionQueBean;
    }

    @Override
    public String getQueVersionUrl() {
        return "http://version.inno-cn.cn/version/query";
    }

    @Override
    public String getCheckVersionStr() {
//        PhoneUtil.getInstance().getDeviceInfo();
        //显示屏参数
        String display = Build.DISPLAY;
        LG.i(tag, "显示屏参数：" + display);
        //唯一编号
        String fingderprint = Build.FINGERPRINT;
        LG.i(tag, "唯一编号：" + fingderprint);
        return display;
    }

    @Override
    public void netQueOk(VersionBean versionBean) {
        updateVersionInfoTV.setText("有新版本，可更新!\n("+getCheckVersionStr()+">>"+versionBean.getVersionCode()+")");
        nowUpdateBT.setVisibility(View.VISIBLE);
        regetBT.setVisibility(View.GONE);
    }

    @Override
    public void netQueErr(String errStr) {
        updateVersionInfoTV.setText("当前版本号："+getCheckVersionStr()+"\nnet err:"+errStr);
        nowUpdateBT.setVisibility(View.GONE);
        regetBT.setVisibility(View.VISIBLE);
    }

    @Override
    public void downOverNeedDo(final String localPath) {

            basehandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                    downProgressTV.setText("start update system!");
                    RecoverySystem.installPackage(MyApplication.getContext(), new File(localPath));
                    }catch (Exception e){}
                }
            });



    }

    @Override
    public int getBGRsId() {
        return R.drawable.bg_inno_cn_update_version;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}

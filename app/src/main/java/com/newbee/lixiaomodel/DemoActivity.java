package com.newbee.lixiaomodel;

import android.Manifest;

import com.lixiao.build.mybase.activity.welcome.BaseWelcomeActivity;
import com.lixiao.build.mybase.activity.welcome.bean.WelcomeInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/1/14 0014 19:07
 */
public class DemoActivity extends BaseWelcomeActivity {


    @Override
    public int getWelcomeLayoutId() {
        return R.layout.activity_welcemo;
    }

    @Override
    public void initWelcomeView() {

    }

    @Override
    public void initWelcomeData() {

    }

    @Override
    public void initWelcomeControl() {

    }

    @Override
    public WelcomeInfoBean getWelcomeInfoBean() {
        WelcomeInfoBean welcomeInfoBean=new WelcomeInfoBean();
//        welcomeInfoBean.setBackGroundRsId(R.color.red);
//        welcomeInfoBean.setIconRsId(R.drawable.lixiao);
        List<String> permissions = new ArrayList<>();
//        permissions.add(Manifest.permission.READ_CONTACTS);
        permissions.add(Manifest.permission.READ_PHONE_STATE);
//        permissions.add(Manifest.permission.READ_CALENDAR);
        permissions.add(Manifest.permission.CAMERA);
//        permissions.add(Manifest.permission.BODY_SENSORS);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.RECORD_AUDIO);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        welcomeInfoBean.setPermissionList(permissions);
        return welcomeInfoBean;
    }

    @Override
    public void userNoPermission() {

    }

    @Override
    public void userGetAllPermission() {
        TestSql.getInstance().add();;
        TestSql.getInstance().que11();
//        toActivity(TestAidlActivity.class);
        showToast("获得所有权限成功");

    }


}

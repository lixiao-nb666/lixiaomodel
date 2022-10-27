package com.lixiao.build.mybase.activity.launcher;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.activity.BaseCompatActivity;
import com.lixiao.build.util.phone.PhoneUtil;
import com.lixiao.build.util.systemapp.PackageManagerUtil;
import com.lixiao.build.util.systemapp.StartOtherApkUtil;
import com.lixiao.build.util.systemapp.adapter.SystemAppsListAdapter;
import com.lixiao.build.util.systemapp.bean.ResultSystemAppInfoBean;
import com.lixiao.build.util.systemapp.bean.SystemAppInfoBean;
import com.lixiao.build.util.systemapp.observer.PackageManagerObserver;
import com.lixiao.build.util.systemapp.observer.PackageManagerSubscriptionSubject;
import com.lixiao.build.util.systemapp.observer.PackageManagerType;
import com.lixiao.build.util.systemapp.pm.PackageUtils;
import com.lixiao.build.util.systemapp.uninstall.UninstallApkUtil;
import com.lixiao.developmenttool.R;
import com.lixiao.view.GalleryAdapter;

import com.lixiao.view.viewpager.GalleryFlow;

import java.util.List;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/7/8 0008 15:22
 */
public  class Base3DLauncherActivity extends BaseCompatActivity {

    public  String getTitleName(){
        return PhoneUtil.getInstance().getAppName();
    }
    public  boolean needAutoUninstall(){
        return false;
    }
    public boolean needGetClick(){
        return false;
    }
    public boolean needGetOnLongClick(){
        return false;
    }
    public void nowClick(SystemAppInfoBean appInfoBean){

    }

    public void nowLongClick(SystemAppInfoBean appInfoBean){

    }

    public int getDefBg(){
        return  R.drawable.bj;
    }

    private String titleName;

    private RecyclerView appsGV;
    private List<SystemAppInfoBean> apps;
    private GalleryAdapter appsListAdapter;

    private PackageManagerObserver packageManagerObserver=new PackageManagerObserver() {
        @Override
        public void update(PackageManagerType eventBs, Object object) {
            switch (eventBs){
                case GET_SYSTEM_APPS:
                    Message message=new Message();
                    message.what=init_list;
                    message.obj=object;
                    viewHandler.sendMessageDelayed(message,1);
                    break;
                case ERR:
                    break;
            }
        }
    };

    private Handler viewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case init_list:
                    ResultSystemAppInfoBean resultSystemAppInfoBean= (ResultSystemAppInfoBean) msg.obj;
                    apps = resultSystemAppInfoBean.getAppList();
                    appsListAdapter = new GalleryAdapter(context,apps);
                    //设置布局管理器
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Base3DLauncherActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    appsGV.setLayoutManager(linearLayoutManager);

                    appsGV.setAdapter(appsListAdapter);

                    appsGV.setLayoutManager(linearLayoutManager);

                    appsGV.setItemAnimator(new DefaultItemAnimator());
                    break;
                case reset_list:
//                    appsListAdapter.setData(apps);
                    appsListAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private final int init_list = 1;
    private final int reset_list = 2;

    @Override
    public int getViewLayoutRsId() {
        return R.layout.spj_system_3d_apps_layout;
    }



    @Override
    public void initView() {

        appsGV = view.findViewById(R.id.gv_apps);

    }

    @Override
    public void initData() {
//        if (myWindowSet.screenIsLandscape()) {
//            appsGV.setNumColumns(8);
//        } else {
//            appsGV.setNumColumns(5);
//        }
        String titleStr=getIntentString();
        if(TextUtils.isEmpty(titleStr)){
            titleStr=getTitleName();
        }
        titleName =titleStr+getString(R.string.launcher_str);
        int bgRs=getDefBg();
        if(bgRs!=0){

        }

    }

    @Override
    public void initControl() {


        PackageManagerSubscriptionSubject.getInstance().addObserver(packageManagerObserver);
        PackageManagerUtil.getInstance().toGetSystemApps();
    }

    @Override
    public void closeActivity() {
        PackageManagerSubscriptionSubject.getInstance().removeObserver(packageManagerObserver);
    }

    @Override
    public void viewIsShow() {


    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {
//        if (myWindowSet.screenIsLandscape()) {
//            appsGV.setNumColumns(8);
//        } else {
//            appsGV.setNumColumns(5);
//        }
        Message message=new Message();
        message.what=reset_list;
        viewHandler.sendMessageDelayed(message,1);
    }





}

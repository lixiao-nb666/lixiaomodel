package com.lixiao.build.util.systemapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.appliction.BaseApplication;
import com.lixiao.build.util.phone.PhoneUtil;
import com.lixiao.build.util.systemapp.bean.ResultSystemAppInfoBean;
import com.lixiao.build.util.systemapp.bean.SystemAppInfoBean;
import com.lixiao.build.util.systemapp.observer.PackageManagerSubscriptionSubject;
import com.lixiao.build.util.systemapp.observer.PackageManagerType;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackageManagerUtil {
    private static PackageManagerUtil managerUtil;
    private PackageManager manager;


    private PackageManagerUtil() {
        manager = BaseApplication.getContext().getPackageManager();

    }

    public static PackageManagerUtil getInstance() {
        if (null == managerUtil) {
            synchronized (PackageManagerUtil.class) {
                if (null == managerUtil) {
                    managerUtil = new PackageManagerUtil();
                }
            }
        }
        return managerUtil;
    }

//    public String AppgetName(String packageName){
//
//       // return manager.getApplicta;
//    }

    public Drawable getIcon(String packageName) {
        try {

            return manager.getApplicationIcon(packageName);
        } catch (Exception e) {
            return null;
        }

    }

    public void toGetSystemAppsCanUseCache(){
        if(null==resultSystemAppInfoBean||null==resultSystemAppInfoBean.getAppList()||resultSystemAppInfoBean.getAppList().size()==0){
            toGetSystemApps();
        }else {
            PackageManagerSubscriptionSubject.getInstance().update(PackageManagerType.GET_SYSTEM_APPS, resultSystemAppInfoBean);
        }

    }



    private ResultSystemAppInfoBean resultSystemAppInfoBean;
    public void toGetSystemApps() {
        new Thread(new Runnable() {
            @Override
            public void run() {



                resultSystemAppInfoBean= new ResultSystemAppInfoBean();
                List<ResolveInfo> resolveInfoList = getResolveInfos();
                if (resolveInfoList == null || resolveInfoList.size() == 0) {
                    PackageManagerSubscriptionSubject.getInstance().update(PackageManagerType.ERR, "system app list is null!");
                    return;
                }
                String appName = PhoneUtil.getInstance().getAppName();
                for (int i = 0; i < resolveInfoList.size(); i++) {
                    ResolveInfo resolveInfo = resolveInfoList.get(i);
                    try {
                        String pkg = resolveInfo.activityInfo.packageName;
                        String cls = resolveInfo.activityInfo.name;
                        ApplicationInfo applicationInfo = manager.getPackageInfo(pkg, i).applicationInfo;
                        String name = applicationInfo.loadLabel(manager).toString();
                        if (TextUtils.isEmpty(name) || name.equals(appName)) {
                            continue;
                        }
                        LG.i("lixiao", "look icon :" + resolveInfo.getIconResource() + "-------" + resolveInfo.icon);
                        SystemAppInfoBean app = new SystemAppInfoBean();
                        app.setName(name);
                        app.setPakeageName(pkg);
                        app.setIndexActivityClass(cls);
                        app.setIconRs(resolveInfo.getIconResource());
                        resultSystemAppInfoBean.add(app);

                    } catch (Exception e) {
                    }
                }



                PackageManagerSubscriptionSubject.getInstance().update(PackageManagerType.GET_SYSTEM_APPS, resultSystemAppInfoBean);

            }
        }).start();


    }





    private List<ResolveInfo> getResolveInfos() {



        List<ResolveInfo> appList = null;
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        appList = manager.queryIntentActivities(intent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(manager));
        return appList;
    }

    public boolean checkAppIsInstalled(String packageName) {

        try {
            return manager.getApplicationIcon(packageName) != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

    }
}

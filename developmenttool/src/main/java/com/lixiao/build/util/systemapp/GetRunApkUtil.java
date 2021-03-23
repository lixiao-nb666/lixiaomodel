package com.lixiao.build.util.systemapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.lixiao.build.mybase.appliction.BaseApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GetRunApkUtil {


    public class AppInfo {
        String name;
        String packagerName;
        int pid;
        Drawable icon;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackagerName() {
            return packagerName;
        }

        public void setPackagerName(String packagerName) {
            this.packagerName = packagerName;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }


        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }
    }

    public List<AppInfo> getAppInfos(Activity activity) {
        List<AppInfo> appInfos = new ArrayList<>();
        // 获取正在运行的进程
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        // 查询所有已经安装的应用程序
        PackageManager packageManager = activity.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            String[] pkgList = appProcess.pkgList;
            for (String packageName : pkgList) {
                for (PackageInfo packageInfo : packageInfos) {
                    if (TextUtils.equals(packageInfo.packageName, packageName)) {
                        if (TextUtils.equals(packageName, activity.getPackageName())) {
                            // 如果是本应用程序，则不要添加。
                            break;
                        }
                        // 该已安装的应用程序和正在后台运行的这个应用程序是同一个
                        AppInfo appInfo = new AppInfo();
                        // 获得应用名
                        appInfo.setName(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString());
                        // 获得应用包名
                        appInfo.setPackagerName(packageName);
                        appInfo.setIcon(packageInfo.applicationInfo.loadIcon(packageManager));
                        appInfo.setPid(appProcess.pid);
                        appInfos.add(appInfo);
                        break;
                    }
                }
            }
        }
        // 排序
        Collections.sort(appInfos, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo lhs, AppInfo rhs) {
                // TODO Auto-generated method stub
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        return appInfos;
    }


    public boolean isTopPage(String packageName) {
        try {
            ActivityManager activityManager = (ActivityManager) BaseApplication.getContext()
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (tasksInfo.size() > 0) {
                // 应用程序位于堆栈的顶层
                if (packageName.equals(tasksInfo.get(0).topActivity
                        .getPackageName())) {
                    return true;
                }
            }
        }catch (Exception e){}
        return false;
    }

    public boolean isTopActivity(String packageName,String className) {
        try {
            ActivityManager activityManager = (ActivityManager) BaseApplication.getContext()
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (tasksInfo.size() > 0) {
                // 应用程序位于堆栈的顶层
                if (packageName.equals(tasksInfo.get(0).topActivity
                        .getPackageName())&&className.equals(tasksInfo.get(0).topActivity.getClassName())) {
                    return true;
                }
            }
        }catch (Exception e){}
        return false;
    }

}

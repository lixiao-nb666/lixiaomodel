package com.lixiao.build.util.systemapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.appliction.BaseApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class StartOtherApkUtil {
    private static StartOtherApkUtil startOtherApkUtil;
    private StartOtherApkUtil(){

    }

    public static StartOtherApkUtil getInstance(){
        if(null==startOtherApkUtil){
            synchronized (StartOtherApkUtil.class){
                if(null==startOtherApkUtil){
                    startOtherApkUtil=new StartOtherApkUtil();
                }
            }
        }
        return startOtherApkUtil;
    }


    public boolean doStartApk(Context context, String pkgName, String activityClassName) {
        try {
//            ComponentName componetName = new ComponentName(
//                    //app2的包名
//                    pkgName,
//                    //你要启动的界面
//                    activityClassName);
//            Intent intent = new Intent();
//            //传递参数
//            Bundle bundle = new Bundle();
//            intent.putExtras(bundle);
//            intent.setComponent(componetName);
//            intent.setAction("android.intent.action.VIEW");
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


//            Intent intent = new Intent(
//                    Intent.ACTION_DELETE, Uri.fromParts("package", pkgName, activityClassName));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(pkgName, activityClassName);
            intent.setComponent(cn);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            LG.i("fjdskfjz", e.toString());
            return false;
        }
    }


    public boolean checkAppIsInstalled(String packageName) {
        PackageManager pm = BaseApplication.getContext().getApplicationContext().getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

    }

    public void closeOtherApk(final String packageName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process process = Runtime.getRuntime().exec("su");
                    OutputStream out = process.getOutputStream();
                    String cmd = "am force-stop " + packageName + " \n";
                    out.write(cmd.getBytes());
                    out.flush();
                    out.close();
                    InputStream fis = process.getInputStream();
                    //用一个读输出流类去读
                    InputStreamReader isr = new InputStreamReader(fis);
                    //用缓冲器读行
                    BufferedReader br = new BufferedReader(isr);
                    String line = null;
                    //直到读完为止 目的就是要阻塞当前的线程到命令结束的时间
                    while ((line = br.readLine()) != null) {
                        LG.i("closeOtherApk", line);
                    }
                    process = null;
                } catch (IOException e) {
                }
            }
        }).start();


    }


    public  void doStartApplicationWithPackageName(Context context,String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo =context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);
        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }




}

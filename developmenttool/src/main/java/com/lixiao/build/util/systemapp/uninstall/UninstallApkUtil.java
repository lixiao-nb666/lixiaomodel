package com.lixiao.build.util.systemapp.uninstall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.lixiao.build.util.systemapp.install.imp.InstallImp;
import com.lixiao.build.util.systemapp.install.thread.PmInstallThread;
import com.lixiao.build.util.systemapp.uninstall.imp.UnInstallImp;
import com.lixiao.build.util.systemapp.uninstall.thread.PmUnistallThread;

import java.io.File;

public class UninstallApkUtil {

    private static String tag = "InstallApk>>>";
    private static UninstallApkUtil uninstallApkUtil;
    private UnInstallImp unInstallImp=new UnInstallImp() {
        @Override
        public void uninstallOk(String apkPag) {

        }

        @Override
        public void uninstallFalse(String apkPag) {

        }
    };
    private UninstallApkUtil(){
    }

    public static UninstallApkUtil getInstance(){
        if(null==uninstallApkUtil){
            synchronized (UninstallApkUtil.class){
                if(null==uninstallApkUtil){
                    uninstallApkUtil=new UninstallApkUtil();
                }
            }
        }
        return uninstallApkUtil;
    }

    public void uninstall(String apkPag){
        new PmUnistallThread(apkPag,unInstallImp).start();

    }



    public void uninstallBySystem(Context context,String packageName){
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
                packageURI);
        context.startActivity(uninstallIntent);
    }
}

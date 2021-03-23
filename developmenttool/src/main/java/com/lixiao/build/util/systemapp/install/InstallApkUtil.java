package com.lixiao.build.util.systemapp.install;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.lixiao.build.util.systemapp.install.imp.InstallImp;
import com.lixiao.build.util.systemapp.install.thread.PmInstallThread;

import java.io.File;

public class InstallApkUtil {

    private static String tag = "InstallApk>>>";
    private static InstallApkUtil installApkUtil;
    private InstallApkUtil(){
    }

    public static InstallApkUtil getInstance(){
        if(null==installApkUtil){
            synchronized (InstallApkUtil.class){
                if(null==installApkUtil){
                    installApkUtil=new InstallApkUtil();
                }
            }
        }
        return installApkUtil;
    }

    public void autoInstall(final Context context, final String filePath){
        InstallImp installImp=new InstallImp() {

            @Override
            public void installIsOk(String fileParh, String installMouth) {

            }

            @Override
            public void installIsFalse(String fileParh, String installMouth) {
                installByFilePath(context,filePath);
            }
        };
        compulsoryInstall(context,filePath,installImp,false);
    }

    /**
     * 安装
     *
     * @param context 接收外部传进来的context
     */
    public void installByFilePath(Context context, String filePath) {
        // 核心是下面几句代码-+
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(filePath)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     * @param apkPath 要安装的apk文件的路径
     * @return 安装成功返回true，安装失败返回false。
     */
    public void compulsoryInstall(final Context context, final String apkPath, InstallImp installImp, boolean needRemove) {
        new PmInstallThread(context,apkPath,needRemove,installImp).start();
    }

}

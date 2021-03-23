package com.lixiao.build.util.systemapp.uninstall.thread;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.TextView;

import com.lixiao.build.mybase.appliction.BaseApplication;
import com.lixiao.build.util.systemapp.install.imp.InstallImp;
import com.lixiao.build.util.systemapp.install.thread.PmInstallThread;
import com.lixiao.build.util.systemapp.uninstall.UninstallApkUtil;
import com.lixiao.build.util.systemapp.uninstall.imp.UnInstallImp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/1 0001 14:30
 */
public class PmUnistallThread extends Thread{
    private String apkPag;
    private UnInstallImp unInstallImp;

    public PmUnistallThread(String apkPag,UnInstallImp unInstallImp){
        this.apkPag=apkPag;
        this.unInstallImp=unInstallImp;
    }

    @Override
    public void run() {
        super.run();
        if(TextUtils.isEmpty(apkPag)){
            unInstallImp.uninstallFalse(apkPag);
            return;
        }
        boolean b=pmUninstall();
        if(b){
            unInstallImp.uninstallOk(apkPag);
        }else {
            unInstallImp.uninstallFalse(apkPag);
        }
    }

//    //静默卸载
//    private void uninstallSlient() {
//        String cmd = "pm uninstall "+ apkPag;
//        Process process = null;
//        DataOutputStream os = null;
//        BufferedReader successResult = null;
//        BufferedReader errorResult = null;
//        StringBuilder successMsg = null;
//        StringBuilder errorMsg = null;
//        try {
//            //卸载也需要root权限
//            process =Runtime.getRuntime().exec("su");
//            os = new DataOutputStream(process.getOutputStream());
//            os.write(cmd.getBytes());
//            os.writeBytes("\n");
//            os.writeBytes("exit\n");
//            os.flush();
//            //执行命令
//            process.waitFor();
//            //获取返回结果
//            successMsg = new StringBuilder();
//            errorMsg = new StringBuilder();
//            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            String s;
//            while ((s =successResult.readLine()) != null) {
//                successMsg.append(s);
//            }
//            while ((s = errorResult.readLine())!= null) {
//                errorMsg.append(s);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                if (process != null) {
//                    process.destroy();
//                }
//                if (successResult != null) {
//                    successResult.close();
//                }
//                if (errorResult != null) {
//                    errorResult.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }


    private static final String SILENT_UNINSTALL_CMD = "pm uninstall ";
    public boolean pmUninstall(){
        String uninstallCmd = SILENT_UNINSTALL_CMD + apkPag;
        int result = -1;
        DataOutputStream dos = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(process.getOutputStream());
            dos.writeBytes(uninstallCmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            process.waitFor();
            result = process.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(dos != null) {
                    dos.close();
                }
                if(process != null){
                    process.destroy();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(result==0){
            return true;
        }else {
            return false;
        }
    }

//    private void silentDelete(String packageName) {
//        PackageManager pm = BaseApplication.getContext().getPackageManager();
//        try {
//            PackageInfo packageInfo = pm.getPackageInfo(packageName,
//                    PackageManager.GET_UNINSTALLED_PACKAGES);
//            if (packageInfo == null) {
//                return;
//            }
//            String appName = packageInfo.applicationInfo.loadLabel(pm)
//                    .toString();
//            pm.deletePackage(packageName, new PackageDeleteObserver(appName), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            String text = String.format(getString(R.string.package_not_exist),
//                    packageName);
//            sendMessage(text);
//            Log.e(TAG, "silentRemove NameNotFoundException = " + e.getMessage());
//        }
//        mH.sendEmptyMessageDelayed(HANDLER_KILL_SELF, 3000);


}

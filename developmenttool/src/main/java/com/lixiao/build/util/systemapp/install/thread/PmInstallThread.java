package com.lixiao.build.util.systemapp.install.thread;

import android.content.Context;

import android.util.Log;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.util.systemapp.install.imp.InstallImp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/11/2 0002 11:12
 */
public class PmInstallThread extends Thread{
    private final String tag=getClass().getName()+">>>>";
    private String apkPath;
    private boolean needRemove;
    private InstallImp installImp;
    private Context context;
    public PmInstallThread(Context context,String filePath, boolean needRemove, InstallImp installImp){
        this.apkPath=filePath;
        this.needRemove=needRemove;
        this.installImp=installImp;
        this.context=context;

    }

    @Override
    public void run() {
        super.run();
        boolean installIsok=doSuInstall();
        if(!installIsok){
            installIsok=pmInstallSilent(apkPath);
        }
        if(installIsok){
            installImp.installIsOk(apkPath,"install");
            if(needRemove){
                File removeFile=new File(apkPath);
                removeFile.delete();
            }
        }else {
            installImp.installIsFalse(apkPath,"install");
        }

    }



    private boolean doSuInstall(){
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请root权限，并获得实例
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行pm install命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(
                    process.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            Log.d("TAG", "install msg is " + msg);
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            if (!msg.contains("Failure")) {
                result = true;
            }else {
                result=false;
            }
        } catch (Exception e) {
                result=false;

        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                Log.e("TAG", e.getMessage(), e);
            }
        }
        return result;
    }





    /**
     * 静默安装
     * @param filePath
     * @return 0 means normal, 1 means file not exist, 2 means other exception error
     */
    private boolean pmInstallSilent(String filePath) {
        boolean result;
        File file = new File(filePath);
        if (filePath == null || filePath.length() == 0 || file == null || file.length() <= 0 || !file.exists() || !file.isFile()) {
            return false;
        }
        String[] args = {"pm", "install", "-r", filePath};
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        try {
            process = processBuilder.start();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }
            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        if (successMsg.toString().contains("Success") || successMsg.toString().contains("success")) {
            result = true;
        } else {
            result = false;
        }
        Log.d("test-test", "successMsg:" + successMsg + ", ErrorMsg:" + errorMsg);
        return result;
    }



}

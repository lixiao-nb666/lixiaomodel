package com.lixiao.build.util.root;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/9/7 0007 11:50
 */
public class RootUtil {


    public static boolean is_root(){
        boolean res = false;
        try{
            if ((!new File("/system/bin/su").exists()) &&
                    (!new File("/system/xbin/su").exists())){
                res = false;
            }
            else {
                res = true;
            };
        }
        catch (Exception e) {

        }
        return res;
    }

    public static void getRoot(){
        try {
            Process process = Runtime.getRuntime().exec("su");
        }catch (Exception e){}

    }

    public static boolean doRootCmd(String cmd){
                boolean runOk;
                DataOutputStream dataOutputStream = null;
                BufferedReader errorStream = null;
                try {
                    // 申请root权限，并获得实例
                    Process process = Runtime.getRuntime().exec("su");
                    dataOutputStream = new DataOutputStream(process.getOutputStream());
                    // 执行pm install命令
                    String command = cmd+ "\n";
                    dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
                    dataOutputStream.flush();
                    dataOutputStream.writeBytes("exit\n");
                    dataOutputStream.flush();
                    process.waitFor();
                    errorStream = new BufferedReader(new InputStreamReader(
                            process.getErrorStream()));
                    runOk=true;

                    // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功

                } catch (Exception e) {
                    runOk=false;
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
        return runOk;
    }
}

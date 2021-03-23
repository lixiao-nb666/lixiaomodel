package com.lixiao.down.config;

import android.os.Environment;
import android.text.TextUtils;

import com.lixiao.build.util.phone.PhoneUtil;
import com.lixiao.build.util.systemapp.PackageManagerUtil;
import com.lixiao.build.util.systemapp.pm.PackageUtils;

import java.io.File;

public class XiaoGeDownLoaderConfig {

    public static boolean needDownEncoded=true;

    /**
     * 同步下载文件的数量
     */
    public static boolean can_synchronous_down =false;
//    /**
//     * 最大的下载数量
//      */
//    public static int max_down_numb=3;
    /**
     * 失败,重新下载的次数
     */
    public static int err_redown_numb=10;

    /**
     * 默认的URL请求头
     */
    public static String defHeadUrl;
    /**
     * 默认的文件夹地址
     */
    public static String defParentFilePath;
    /**
     * 默认的下载地址
     */
    private final static String finalDefParentFilePath = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "lixiaodown";// 总文件夹

    public static String getFinalDefParentFilePath(){
        String appName=PhoneUtil.getInstance().getAppName();
        if(TextUtils.isEmpty(appName)){
            return finalDefParentFilePath;
        }else {
            return finalDefParentFilePath+ File.separator+appName;
        }
    }

    /**
     * 下载的时候是否检测文件存在
     */
    public static boolean downCheckFileExist = true;
}

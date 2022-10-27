package com.nrmyw.launcher.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.lixiao.build.zxing.util.ZXingUtils;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/9/7 0007 15:16
 */
public class UrlToFilePathUtil {



    public static String uriToFilePath(Intent intent, Context context){

        String  filePath="";
        try {
            filePath=getFilePathUseStringExtra(intent);
            if(!TextUtils.isEmpty(filePath)){
                return filePath;
            }
            String[] proj = {MediaStore.Images.Media.DATA};
            // 获取选中图片的路径
            Cursor cursor = context.getContentResolver().query(intent.getData(),
                    proj, null, null, null);
            if(null!=cursor){
                if (cursor.moveToFirst()) {
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    filePath= cursor.getString(column_index);
                }
                cursor.close();
            }
            if(!TextUtils.isEmpty(filePath)){
                return filePath;
            }
            filePath = ZXingUtils.getPath(context,
                    intent.getData());
        }catch (Exception e){

        }
        return filePath;
    }


    private static String getFilePathUseStringExtra(Intent intent){
        try {
            return   intent.getStringExtra("filepath");
        }catch (Exception e){
            return "";
        }

    }



}

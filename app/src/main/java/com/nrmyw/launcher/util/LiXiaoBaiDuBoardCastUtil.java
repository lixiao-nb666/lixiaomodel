package com.nrmyw.launcher.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/7/8 0008 14:22
 */
public class LiXiaoBaiDuBoardCastUtil {

    public static void sendNewBeeBaiDuTtsStr(Context context, String string) {

        Log.i("lixiao","kankanshifou:"+string);
        Intent intent = new Intent("com.newbee.baidumodel.tts.boardcast");
        intent.setComponent(new ComponentName("com.newbee.baidumodel", "com.newbee.baidumodel.BaiDuTtsReceiver"));
        intent.putExtra("tts_str", string);
        context.sendBroadcast(intent);
    }
}
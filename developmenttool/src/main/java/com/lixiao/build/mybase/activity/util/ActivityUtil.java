package com.lixiao.build.mybase.activity.util;

import android.content.Context;
import android.content.Intent;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/22 0022 14:34
 */
public class ActivityUtil {

    public static void toActivity(Context context,Class toClass) {
        Intent intent = new Intent(context, toClass);
        context.startActivity(intent);
    }


    // 带数据跳转活动
    public static void toActivity(Context contex,Class toClass, String gsonString) {
        Intent intent = new Intent(contex, toClass);
        intent.putExtra("tb", gsonString);
        contex.startActivity(intent);
    }

    //获得传的数据
    public static String getIntentString(Intent intent) {
        return intent.getStringExtra("tb");
    }

}

package com.lixiao.build.util.text;

import android.text.TextUtils;

public class CheckTextUtil {

    public static boolean checkHeadIsHttp(String checkStr) {
        if (TextUtils.isEmpty(checkStr) || checkStr.length() < 4) {
            return false;
        }
        if ("http".equals(checkStr.substring(0, 4))) {
            return true;
        } else {
            return false;
        }


    }
}

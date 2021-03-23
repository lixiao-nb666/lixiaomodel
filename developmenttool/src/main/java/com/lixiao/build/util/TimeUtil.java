package com.lixiao.build.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");

    public static String getDateStr(long time) {
        try {
            Date date = new Date(time);
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }
}

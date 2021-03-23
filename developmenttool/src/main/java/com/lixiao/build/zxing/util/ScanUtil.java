package com.lixiao.build.zxing.util;

import android.app.Activity;
import android.content.Intent;

import com.lixiao.build.zxing.app.CaptureActivity;


/**
 * Created by Administrator on 2017/9/8 0008.
 */

public class ScanUtil {
    public static final int SCAN_REQUEST = 100;
    public static final int SCAN_RESULT = 200;
    public static final String SCAN_STR_BS = "SCAN_RESULT";

    public static void toScanActivity(Activity activity) {
        activity.startActivityForResult(new Intent(activity, CaptureActivity.class), SCAN_REQUEST);
    }

    //是照相机返回
    public static boolean isScanResult(int request, int result) {
        if (request == SCAN_REQUEST && result == SCAN_RESULT)
            return true;
        return false;
    }

    public static String getScanResultStr(Intent data) {
        return data.getStringExtra(SCAN_STR_BS);
    }

    public static void setScanResult(Activity activity, String resultStr) {
        Intent data = new Intent();
        data.putExtra(SCAN_STR_BS, resultStr);
        activity.setResult(SCAN_RESULT , data);
        activity.finish();
    }
}

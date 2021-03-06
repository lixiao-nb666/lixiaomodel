package com.lixiao.build.util.phone;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.Display;

import androidx.annotation.RequiresPermission;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.appliction.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xiefuning on 2017/4/13.
 * about:
 */

public class PhoneUtil {
    private final String tag = getClass().getName() + ">>>>";
    private TelephonyManager manager;
    private Context context;
    private PackageInfo packageInfo;
    private static PhoneUtil phoneUtil;

    private PhoneUtil() {
        this.context = BaseApplication.getContext();
        manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static PhoneUtil getInstance() {
        if (phoneUtil == null) {
            synchronized (PhoneUtil.class) {
                if (phoneUtil == null) {
                    phoneUtil = new PhoneUtil();
                }
            }
        }
        return phoneUtil;
    }


    public String getImei() {
        String imei = manager.getDeviceId();
        return TextUtils.isEmpty(imei) ? "" : imei;
    }

    public String getImsi() {
        String imsi = manager.getSubscriberId();
        return TextUtils.isEmpty(imsi) ? "" : imsi;
    }



//    public String getCid() {
//        String channelName = AnalyticsConfig.getChannel(content);
//        return channelName;
//    }


    public String loadFileAsString(String fileName) throws Exception {
        File file = new File(fileName);
        if (!file.exists())
            return "";
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    public String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    /**
     * VersionCode??????App????????????
     *
     * @return
     */
    public String getVersionCode() {
        if (packageInfo != null)
            return packageInfo.versionCode + "";
        return "";
    }
    //VersionCode??????App???????????????VersionName???????????????

    /**
     * VersionName???????????????
     *
     * @return
     */
    public String getVersionName() {
        if (packageInfo != null)
            return packageInfo.versionName;
        return "";
    }

    public String getAppName() {
        if (packageInfo != null) {
            //??????albelRes
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } else {
            return "";
        }

    }

    public String getCountryId() {
        String CountryID = manager.getSimCountryIso().toUpperCase();
        if (TextUtils.isEmpty(CountryID)) {
            if (CountryID.length() > 0)
                return CountryID;
        }
        return manager.getNetworkCountryIso();
    }

    public String getDeviceId() {
        return manager.getDeviceId();
    }

    public String getDeviceName() {
        return Build.MODEL;
    }

    public String getLang() {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }

    public String getSDK_INT() {
        return Build.VERSION.SDK_INT + "";
    }

    public String getOsVersion() {
        return Build.VERSION.RELEASE;
    }






    public String getRatio(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay(); //Activity#getWindowManager()
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        StringBuilder sb = new StringBuilder();
        sb.append(width);
        sb.append("*");
        sb.append(height);
        return sb.toString();
    }

    /**
     * ??????????????????
     */
    public boolean screenIsLand(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay(); //Activity#getWindowManager()
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (width > height) {
            return true;
        }
        return false;
    }

    public String getAndroidId() {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return TextUtils.isEmpty(androidId) ? "" : androidId;
    }



    public String[] getVersion() {
        String[] version = {"null", "null", "null", "null"};
        String str1 = "/proc/version";
        String str2;
        String[] arrayOfString;
        FileReader localFileReader = null;
        BufferedReader localBufferedReader = null;
        try {
            localFileReader = new FileReader(str1);
            localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            version[0] = arrayOfString[2];//KernelVersion

        } catch (IOException e) {
        } finally {
            try {
                if (localFileReader != null) {
                    localFileReader.close();
                }
                if (localBufferedReader != null) {
                    localBufferedReader.close();
                }
            } catch (Exception e) {
            }
        }
        version[1] = Build.VERSION.RELEASE;// firmware version
        version[2] = Build.MODEL;//model
        version[3] = Build.DISPLAY;//system version
        return version;
    }


    public String getSdTotalSize(Context context) {
        StatFs sf = new StatFs("/mnt/sdcard");
        long blockSize = sf.getBlockSize();
        long totalBlocks = sf.getBlockCount();
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    public String getSdAvailableSize(Context context) {
        StatFs sf = new StatFs("/mnt/sdcard");
        long blockSize = sf.getBlockSize();
        long availableBlocks = sf.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }

    public int readSystem() {
        try {
            StatFs sf = new StatFs("/mnt/sdcard");
            long blockSize = sf.getBlockSize();

            //SD????????????
            long totalBlocks = sf.getBlockCount();
            long sdCount = blockSize * totalBlocks;
            //SD??????????????????
            long availableBlocks = sf.getAvailableBlocks();
            long sdCanUse = blockSize * availableBlocks;
            int percent = (int) (sdCanUse * 100 / sdCount);
            return percent;
        } catch (Exception e) {
            return 0;
        }


    }

    public String[] getSdInfo(Context context) {
        String[] strs = new String[3];
        try {
            StatFs sf = new StatFs("/mnt/sdcard");
            long blockSize = sf.getBlockSize();

            //SD????????????
            long totalBlocks = sf.getBlockCount();
            long sdCount = blockSize * totalBlocks;
            //SD??????????????????
            long availableBlocks = sf.getAvailableBlocks();
            long sdCanUse = blockSize * availableBlocks;
            int percent = (int) (sdCanUse * 100 / sdCount);
            strs[0] = Formatter.formatFileSize(context, sdCount);
            strs[1] = Formatter.formatFileSize(context, sdCanUse);
            strs[2] = percent + "";
        } catch (Exception e) {
            strs[0] = "";
            strs[1] = "";
            strs[2] = "";
        }
        return strs;
    }


    public String getPhoneType() {
        String[] strs = getVersion();
        if (strs != null && strs.length > 0) {
            return strs[strs.length - 1];
        }
        return "";
    }

    // ??????????????????
    public void getDeviceInfo() {
        LG.i(tag, "????????????------------------------------begin");
        //??????
        String board = Build.BOARD;
        LG.i(tag, "?????????" + board);
        //???????????????
        String brand = Build.BRAND;
        LG.i(tag, "??????????????????" + brand);
        //????????????
        String device = Build.DEVICE;
        LG.i(tag, "??????????????????" + device);
        //???????????????
        String display = Build.DISPLAY;
        LG.i(tag, "??????????????????" + display);
        //????????????
        String fingderprint = Build.FINGERPRINT;
        LG.i(tag, "???????????????" + fingderprint);
        //???????????????
        String serial = Build.SERIAL;
        LG.i(tag, "??????????????????" + serial);
        //??????????????????
        String id = Build.ID;
        LG.i(tag, "?????????????????????" + id);
        //???????????????
        String manufacturer = Build.MANUFACTURER;
        LG.i(tag, "??????????????????" + manufacturer);
        //??????
        String model = Build.MODEL;
        LG.i(tag, "?????????" + model);
        //?????????
        String hardware = Build.HARDWARE;
        LG.i(tag, "????????????" + hardware);
        //???????????????
        String product = Build.PRODUCT;
        LG.i(tag, "??????????????????" + product);
        //??????build?????????
        String tags = Build.TAGS;
        LG.i(tag, "??????build????????????" + tags);
        //Builder??????
        String type = Build.TYPE;
        LG.i(tag, "Builder?????????" + type);
        //??????????????????
        String vcodename = Build.VERSION.CODENAME;
        LG.i(tag, "?????????????????????" + vcodename);
        //?????????????????????
        String vincremental = Build.VERSION.INCREMENTAL;
        LG.i(tag, "????????????????????????" + vincremental);
        //???????????????
        String vrelease = Build.VERSION.RELEASE;
        LG.i(tag, "??????????????????" + vrelease);
        //?????????
        int vsdkint = Build.VERSION.SDK_INT;
        LG.i(tag, "????????????" + vsdkint);
        //HOST???
        String host = Build.HOST;
        LG.i(tag, "HOST??????" + host);
        //User???
        String user = Build.USER;
        LG.i(tag, "User??????" + user);
        //????????????
        long time = Build.TIME;
        LG.i(tag, "???????????????" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(time)));
        //OS?????????
        String osVersion = System.getProperty("os.version");
        LG.i(tag, "OS????????????" + osVersion);
        // OS??????
        String osName = System.getProperty("os.name");
        LG.i(tag, "OS?????????" + osName);
        //OS??????
        String osArch = System.getProperty("os.arch");
        LG.i(tag, "OS?????????" + osArch);
        //home??????
        String osUserHome = System.getProperty("os.home");
        LG.i(tag, "home?????????" + osUserHome);
        //name??????
        String osUserName = System.getProperty("os.name");
        LG.i(tag, "name?????? ???" + osUserName);
        //dir??????
        String osUserDir = System.getProperty("os.dir");
        LG.i(tag, "dir?????????" + osUserDir);
        //??????
        String osUserTimeZone = System.getProperty("os.timezone");
        LG.i(tag, "?????????" + osUserTimeZone);
//        //?????????
//        String phoneNum = teleohonyManager.getLine1Number();
//        LG.i(tag,"????????????" + phoneNum);
//        //?????????????????????
//        String iccid = teleohonyManager.getSimSerialNumber();
//        LG.i(tag,"????????????????????????" + iccid);
//        //????????????
//        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        MyApplication1.getInstance().registerReceiver(receiver, filter);//??????BroadcastReceiver
//        //??????id
//        String mDeviceId = teleohonyManager.getDeviceId();`1
//        LG.i(tag,"??????id???" + mDeviceId);
//        getPhoneType();
//        getLocalMacAddress();
//        getLocalIpAddress();
//        getCpuInfo();
//        getTotalMemory();
//        getAvailMemory();
//        getWeithAndHeight();
        //TODO ??????wifi??????
    }




    /**
     * ??????WiFiManager??????mac??????
     *
     * @return
     */
    private String wifiGetMac2() {
        WifiManager wm = (WifiManager) BaseApplication.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();
        if (wi == null || wi.getMacAddress() == null) {
            return null;
        }
        if ("02:00:00:00:00:00".equals(wi.getMacAddress().trim())) {
            return null;
        } else {
            return wi.getMacAddress().trim();
        }
    }

    private String adbGetMac() {
        String macSerial = "";
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// ?????????
                    break;
                }
            }
        } catch (Exception ex) {
            macSerial = "";
        }
        return macSerial;
    }


    private String readFileGetMac() {
        try {
            return loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
        } catch (Exception e) {
            return "";
        }
    }


    public PhoneSystemInfoBean getPhoneSystemInfoBean() {
        PhoneSystemInfoBean phoneSystemInfoBean = new PhoneSystemInfoBean();
//                        LG.i(tag,"????????????------------------------------begin");
        //??????
        String board = Build.BOARD;
        phoneSystemInfoBean.setBoard(board);
//        LG.i(tag,"?????????" + board);
//        //???????????????
        String brand = Build.BRAND;
        phoneSystemInfoBean.setBrand(brand);
//        LG.i(tag,"??????????????????" + brand);
//        //????????????
//        String device = Build.DEVICE;
//        LG.i(tag,"??????????????????" + device);
//        //???????????????
        String display = Build.DISPLAY;
        phoneSystemInfoBean.setDisplayInfo(display);
//        LG.i(tag,"??????????????????" + display);
//        //????????????
        String fingderprint = Build.FINGERPRINT;
        phoneSystemInfoBean.setFingderprint(fingderprint);
//        LG.i(tag,"???????????????" + fingderprint);
//        //???????????????
        String serial = Build.SERIAL;
        phoneSystemInfoBean.setSerial(serial);
//        LG.i(tag,"??????????????????" + serial);
//        //??????????????????
//        String id = Build.ID;
//        LG.i(tag,"?????????????????????" + id);
//        //???????????????
        String manufacturer = Build.MANUFACTURER;
        phoneSystemInfoBean.setManufacturer(manufacturer);
//        LG.i(tag,"??????????????????" + manufacturer);
//        //??????
        String model = Build.MODEL;
        phoneSystemInfoBean.setSystemModel(model);
//        LG.i(tag,"?????????" + model);
//        //?????????
//        String hardware = Build.HARDWARE;

//        LG.i(tag,"????????????" + hardware);
//        //???????????????
        String product = Build.PRODUCT;
        phoneSystemInfoBean.setProductName(product);
//        LG.i(tag,"??????????????????" + product);
//        //??????build?????????
//        String tags = Build.TAGS;
//        LG.i(tag,"??????build????????????" + tags);
//        //Builder??????
//        String type = Build.TYPE;
//        LG.i(tag,"Builder?????????" + type);
//        //??????????????????
//        String vcodename = Build.VERSION.CODENAME;
//        LG.i(tag,"?????????????????????" + vcodename);
//        //?????????????????????
//        String vincremental = Build.VERSION.INCREMENTAL;
//        LG.i(tag,"????????????????????????" + vincremental);
//        //???????????????
//        String vrelease = Build.VERSION.RELEASE;
//        LG.i(tag,"??????????????????" + vrelease);
//        //?????????
        int vsdkint = Build.VERSION.SDK_INT;
        phoneSystemInfoBean.setVsdkint(vsdkint);
//        LG.i(tag,"????????????" + vsdkint);
//        //HOST???
//        String host = Build.HOST;
//        LG.i(tag,"HOST??????" + host);
//        //User???
//        String user = Build.USER;
//        LG.i(tag,"User??????" + user);
//        //????????????
//        long time = Build.TIME;
//        LG.i(tag,"???????????????" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(time)));
//        //OS?????????
//        String osVersion = System.getProperty("os.version");
//        LG.i(tag,"OS????????????" + osVersion);
//        // OS??????
//        String osName = System.getProperty("os.name");
//        LG.i(tag,"OS?????????" + osName);
//        //OS??????
//        String osArch = System.getProperty("os.arch");
//        LG.i(tag,"OS?????????" + osArch);
//        //home??????
//        String osUserHome = System.getProperty("os.home");
//        LG.i(tag,"home?????????" + osUserHome);
//        //name??????
//        String osUserName = System.getProperty("os.name");
//        LG.i(tag,"name?????? ???" + osUserName);
//        //dir??????
//        String osUserDir = System.getProperty("os.dir");
//        LG.i(tag,"dir?????????" + osUserDir);
//        //??????
//        String osUserTimeZone = System.getProperty("os.timezone");
//        LG.i(tag,"?????????" + osUserTimeZone);
        String svBuildName ="ChannelStr";
        phoneSystemInfoBean.setSvBuildName(svBuildName);
        String mac ="mac" ;
        phoneSystemInfoBean.setMac(mac);

        phoneSystemInfoBean.setReadSystem(PhoneUtil.getInstance().readSystem());
        phoneSystemInfoBean.setVersionStr(PhoneUtil.getInstance().getVersionName());
        LG.i(tag, "---kankanphonedevice:" + phoneSystemInfoBean);
        return phoneSystemInfoBean;
    }



}

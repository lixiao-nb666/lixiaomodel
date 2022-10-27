package com.nrmyw.launcher.app;

import android.os.Environment;
import java.io.File;


/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class MyAppFile {
    public static final String local_Path = Environment
            .getExternalStorageDirectory().getAbsolutePath();
    public static final String pack_name = "nrmyw";

    private static final String S_APK_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + pack_name;// 总文件夹

    public static String getSystemSApkPath(){
        return S_APK_PATH;
    }




    public static final String public_files = S_APK_PATH + File.separator
            + "public";// 公共文件
    public static final String private_files = S_APK_PATH + File.separator
            + "private";// 私有文件
    public static final String movies_files = S_APK_PATH + File.separator
            + "movies";// 电影
    public static final String LOCAL_FILE = S_APK_PATH + File.separator
            + "local";// 电影
    public static final String adver_files = S_APK_PATH + File.separator
            + "adver";// 广告
    public static final String pic_files = S_APK_PATH + File.separator + "pic";// 图片广告
    public static final String gif_files = S_APK_PATH + File.separator + "gif";// 图片广告
    public static final String doc_files = S_APK_PATH + File.separator + "doc";// 需求文档
    public static final String apks_files = S_APK_PATH + File.separator
            + "apks";// apk
    public static final String html_files = S_APK_PATH + File.separator
            + "htmls";// apk
    public static final String cache_files = S_APK_PATH + File.separator
            + "cache";// apk
    public static final String down_files = S_APK_PATH + File.separator
            + "down";// apk
    public static final String err_files = S_APK_PATH + File.separator
            + "err";// apk
    public static final String screen_files = S_APK_PATH + File.separator
            + "screen";// apk

    public static final String auto_play_files = S_APK_PATH + File.separator
            + "auto_play";// apk

    public static final String[] file_names = {S_APK_PATH, public_files,
            private_files, LOCAL_FILE, adver_files, doc_files, pic_files,
            apks_files, html_files, gif_files, cache_files, down_files, err_files, screen_files,auto_play_files};

    public static void mkFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < file_names.length; i++) {
                    File file = new File(file_names[i]);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                }
            }
        }).start();

    }

    public static void delectFile() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < file_names.length; i++) {
                    File file = new File(file_names[i]);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }).start();
    }

    public static void clearFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                deleteDirectory(S_APK_PATH);
                for (int i = 0; i < file_names.length; i++) {
                    File file = new File(file_names[i]);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                }
            }
        }).start();
    }


    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     *                 * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }


    public static boolean delete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                return deleteDirectory(filePath);
            } else {
                return deleteFile(filePath);
            }
        } else {
            return false;
        }
    }



}

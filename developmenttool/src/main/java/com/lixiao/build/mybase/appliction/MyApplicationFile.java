package com.lixiao.build.mybase.appliction;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import com.lixiao.build.mybase.LG;
import com.lixiao.build.util.myapp.MyAppUtils;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class MyApplicationFile {
    private String system_Path ;
    private String pack_name ;
    private  String S_APK_PATH;// 总文件夹
    private String public_files;// 公共文件
    private String private_files;// 私有文件
    private String movies_files ;// 电影
    private String LOCAL_FILE;// 电影
    private String adver_files;// 广告
    private String pic_files ;// 图片广告
    private String gif_files ;// 图片广告
    private String doc_files ;// 需求文档
    private String apks_files ;// apk
    private String html_files;// apk
    private String cache_files;// apk
    private String down_files;// apk
    private String err_files;// apk
    private String screen_files ;// apk
    private List<String> file_names=new ArrayList<>();

    private static MyApplicationFile myApplicationFile;

    public MyApplicationFile(){
        initSystemPath();
        LG.i("kankandaodishenmegui","--------kankandaodifile:"+S_APK_PATH);
    }

    public static MyApplicationFile getInstance(){
        if(null==myApplicationFile){
            synchronized (MyApplicationFile.class){
                if(null==myApplicationFile){
                    myApplicationFile=new MyApplicationFile();

                }
            }
        }
        return myApplicationFile;
    }

    private void initSystemPath(){
        system_Path=getStoragePath(BaseApplication.getContext(),false);
        LG.i("kankanshenmegui","shenmegui1111111:"+system_Path);
        if(TextUtils.isEmpty(system_Path)){

            system_Path=Environment.getExternalStorageDirectory().getAbsolutePath();
            LG.i("kankanshenmegui","shenmegui1111111:2");
        }
        pack_name =   MyAppUtils.getLastPackgeName();

        S_APK_PATH =system_Path + File.separator + pack_name;
        public_files = S_APK_PATH + File.separator + "public";
        private_files = S_APK_PATH + File.separator
                + "private";
        movies_files= S_APK_PATH + File.separator
                + "movies";
        LOCAL_FILE = S_APK_PATH + File.separator
                + "local";
        adver_files = S_APK_PATH + File.separator
                + "adver";
        pic_files = S_APK_PATH + File.separator + "pic";// 图片广告
        gif_files = S_APK_PATH + File.separator + "gif";// 图片广告
        doc_files = S_APK_PATH + File.separator + "doc";// 需求文档
        apks_files = S_APK_PATH + File.separator
                + "apks";// apk
        html_files = S_APK_PATH + File.separator
                + "htmls";// apk
        cache_files = S_APK_PATH + File.separator
                + "cache";// apk
        down_files = S_APK_PATH + File.separator
                + "down";// apk
        err_files = S_APK_PATH + File.separator
                + "err";// apk
        screen_files = S_APK_PATH + File.separator
                + "screen";// apk
        file_names.add(S_APK_PATH);
        file_names.add(public_files);
        file_names.add(private_files);
        file_names.add(LOCAL_FILE);
        file_names.add(adver_files);
        file_names.add(doc_files);
        file_names.add(pic_files);
        file_names.add(apks_files);
        file_names.add(html_files);
        file_names.add(gif_files);
        file_names.add(cache_files);
        file_names.add(down_files);
        file_names.add(err_files);
        file_names.add(screen_files);
    }


    public void mkFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < file_names.size(); i++) {
                    File file = new File(file_names.get(i));

                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    LG.i("---chuangjianwenjian","-------chuangjianwenjianjianame:"+file.getAbsolutePath()+"--"+file.exists());
                }
            }
        }).start();

    }

    public  void delectFile() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < file_names.size(); i++) {
                    File file = new File(file_names.get(i));

                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }).start();
    }

    public  void clearFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                deleteDirectory(S_APK_PATH);
                for (int i = 0; i < file_names.size(); i++) {
                    File file = new File(file_names.get(i));
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
    public  boolean deleteDirectory(String dir) {
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
    public  boolean deleteFile(String fileName) {
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


    public  boolean delete(String filePath) {
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


    /**
     * 通过反射调用获取内置存储和外置sd卡根路径(通用)
     *
     * @param mContext    上下文
     * @param is_removale 是否可移除，false返回内部存储，true返回外置sd卡
     * @return
     */
    public  String getStoragePath(Context mContext, boolean is_removale) {
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        String path="";
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
                LG.i("kankanpath","----kankanfilePath:"+path+"---"+removable);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return path;
    }


    public String getSystem_Path() {
        return system_Path;
    }

    public String getPack_name() {
        return pack_name;
    }

    public String getS_APK_PATH() {
        return S_APK_PATH;
    }

    public String getPublic_files() {
        return public_files;
    }

    public String getPrivate_files() {
        return private_files;
    }

    public String getMovies_files() {
        return movies_files;
    }

    public String getLOCAL_FILE() {
        return LOCAL_FILE;
    }

    public String getAdver_files() {
        return adver_files;
    }

    public String getPic_files() {
        return pic_files;
    }

    public String getGif_files() {
        return gif_files;
    }

    public String getDoc_files() {
        return doc_files;
    }

    public String getApks_files() {
        return apks_files;
    }

    public String getHtml_files() {
        return html_files;
    }

    public String getCache_files() {
        return cache_files;
    }

    public String getDown_files() {
        return down_files;
    }

    public String getErr_files() {
        return err_files;
    }

    public String getScreen_files() {
        return screen_files;
    }

    public List<String> getFile_names() {
        return file_names;
    }
}

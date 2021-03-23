package com.lixiao.build.glide;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;


import java.io.File;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class MyGlideModule implements GlideModule {
    private String tag = this.getClass().toString() + ">>>";
    private static final int DISK_CACHE_SIZE = 2000 * 1024 * 1024;
    public static final int MAX_MEMORY_CACHE_SIZE = 2000 * 1024 * 1024;
    public static final String local_Path = Environment
            .getExternalStorageDirectory().getAbsolutePath();
    public static final String pack_name = "lhcx_draw_borad";

    public static final String S_APK_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + pack_name;// 总文件夹

    public static final String cache_files = S_APK_PATH + File.separator
            + "cache";// apk
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置磁盘缓存的路径 path
        final File cacheDir = new File(cache_files);
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                return DiskLruCacheWrapper.get(cacheDir, DISK_CACHE_SIZE);
            }
        });
        //设置内存缓存大小，一般默认使用glide内部的默认值
        builder.setMemoryCache(new LruResourceCache(MAX_MEMORY_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(IDataModel.class, InputStream.class,
                new MyDataLoader.Factory());
    }
}

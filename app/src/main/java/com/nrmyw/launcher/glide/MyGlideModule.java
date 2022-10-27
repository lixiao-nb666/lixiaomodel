package com.nrmyw.launcher.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

import com.lixiao.build.mybase.appliction.MyApplicationFile;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class MyGlideModule implements GlideModule {
    private String tag = this.getClass().toString() + ">>>";

    private static final int DISK_CACHE_SIZE = 1024*1024 * 1024 * 1024;
    public static final int MAX_MEMORY_CACHE_SIZE = 1024*1024 * 1024 * 1024;


    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 设置磁盘缓存为100M，缓存在内部缓存目录
        final int cacheSize100MegaBytes = 104857600;

        //设置磁盘缓存的路径 path
        final File cacheDir = new File(MyApplicationFile.getInstance().getCache_files());
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                return DiskLruCacheWrapper.get(cacheDir, cacheSize100MegaBytes*5);
            }
        });
        //设置内存缓存大小，一般默认使用glide内部的默认值
//        builder.setMemoryCache(new LruResourceCache(MAX_MEMORY_CACHE_SIZE));


//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes));
        //builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, cacheSize100MegaBytes));

        // 20%大的内存缓存作为 Glide 的默认值
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));


    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(IDataModel.class, InputStream.class,
                new MyDataLoader.Factory());
    }
}

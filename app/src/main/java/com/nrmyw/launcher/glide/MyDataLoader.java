package com.nrmyw.launcher.glide;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class MyDataLoader extends BaseGlideUrlLoader<IDataModel> {
    public MyDataLoader(Context context) {
        super(context);
    }

    public MyDataLoader(ModelLoader<GlideUrl, InputStream> urlLoader) {
        super(urlLoader, null);
    }

    @Override
    protected String getUrl(IDataModel model, int width, int height) {
        return model.buildDataModelUrl(width, height);
    }

    /**
     */
    public static class Factory implements ModelLoaderFactory<IDataModel, InputStream> {

        @Override
        public ModelLoader<IDataModel, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new MyDataLoader(factories.buildModelLoader(GlideUrl.class, InputStream.class));
        }

        @Override
        public void teardown() {
        }
    }
}
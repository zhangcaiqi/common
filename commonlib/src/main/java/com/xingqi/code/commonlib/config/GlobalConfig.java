package com.xingqi.code.commonlib.config;

import android.app.Application;

import com.xingqi.code.commonlib.imageloader.BaseImageLoaderStrategy;

import java.io.File;

import okhttp3.OkHttpClient;

public class GlobalConfig {

    private GlobalConfig(Builder builder){
        this.application = builder.application;
        this.cacheDir = builder.cacheDir;
        this.imageLoaderStrategy = builder.imageLoaderStrategy;
        this.okHttpClient = builder.okHttpClient;
    }

    private Application application;
    //缓存目录
    private File cacheDir;
    private BaseImageLoaderStrategy imageLoaderStrategy;
    private OkHttpClient okHttpClient;


    public Application getApplication() {
        return application;
    }

    public File getCacheDir() {
        return cacheDir;
    }

    public BaseImageLoaderStrategy getImageLoaderStrategy() {
        return imageLoaderStrategy;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static Builder with(Application application){
        return new Builder(application);
    }

    public static class Builder{
        private Application application;
        //缓存目录
        private File cacheDir;

        private BaseImageLoaderStrategy imageLoaderStrategy;

        private OkHttpClient okHttpClient;

        public Builder(Application application) {
            this.application = application;
        }

        public Builder cacheDir(File cacheDir){
            this.cacheDir = cacheDir;
            return this;
        }

        public Builder imageLoaderStrategy(BaseImageLoaderStrategy imageLoaderStrategy) {
            this.imageLoaderStrategy = imageLoaderStrategy;
            return this;
        }

        public Builder okHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        public GlobalConfig build(){
            return new GlobalConfig(this);
        }
    }


}

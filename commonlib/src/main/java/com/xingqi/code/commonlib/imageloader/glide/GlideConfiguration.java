package com.xingqi.code.commonlib.imageloader.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.xingqi.code.commonlib.base.BaseApplication;
import com.xingqi.code.commonlib.config.GlobalConfig;
import com.xingqi.code.commonlib.imageloader.BaseImageLoaderStrategy;
import com.xingqi.code.commonlib.utils.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.OkHttpClient;

@GlideModule(glideName = "GlideArms")
public class GlideConfiguration extends AppGlideModule {
    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;//图片缓存文件最大值为100Mb
    private GlobalConfig globalConfig = BaseApplication.getGlobalConfig();
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {

        builder.setDiskCache(() -> {
            // Careful: the external cache directory doesn't enforce permissions
            File cacheDir = globalConfig.getCacheDir();
            return DiskLruCacheWrapper.create(FileUtil.makeDirs(new File(cacheDir, "Glide")), IMAGE_DISK_CACHE_MAX_SIZE);
        });

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));


        BaseImageLoaderStrategy loadImgStrategy = globalConfig.getImageLoaderStrategy();
        if (loadImgStrategy instanceof GlideAppliesOptions) {
            ((GlideAppliesOptions) loadImgStrategy).applyGlideOptions(context, builder);
        }
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //Glide 默认使用 HttpURLConnection 做网络请求,在这切换成 Okhttp 请求
        OkHttpClient okHttpClient = globalConfig.getOkHttpClient();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));

        BaseImageLoaderStrategy loadImgStrategy = globalConfig.getImageLoaderStrategy();
        if (loadImgStrategy instanceof GlideAppliesOptions) {
            ((GlideAppliesOptions) loadImgStrategy).registerComponents(context, glide, registry);
        }
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}

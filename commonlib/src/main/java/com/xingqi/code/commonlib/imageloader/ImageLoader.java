
package com.xingqi.code.commonlib.imageloader;

import android.content.Context;

import androidx.annotation.Nullable;

import com.xingqi.code.commonlib.base.BaseApplication;


public final class ImageLoader {
    private static BaseImageLoaderStrategy mStrategy = BaseApplication.getGlobalConfig().getImageLoaderStrategy();

    private ImageLoader() {
    }

    /**
     * 加载图片
     *
     * @param context
     * @param config
     * @param <T>
     */
    public static <T extends ImageConfig> void loadImage(Context context, T config) {
        //noinspection unchecked
        mStrategy.loadImage(context, config);
    }

    /**
     * 停止加载或清理缓存
     *
     * @param context
     * @param config
     * @param <T>
     */
    public static <T extends ImageConfig> void clear(Context context, T config) {
        //noinspection unchecked
        mStrategy.clear(context, config);
    }

    @Nullable
    public static BaseImageLoaderStrategy getLoadImgStrategy() {
        return mStrategy;
    }

    /**
     * 可在运行时随意切换 {@link BaseImageLoaderStrategy}
     *
     * @param strategy
     */
    public static void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        mStrategy = strategy;
    }
}

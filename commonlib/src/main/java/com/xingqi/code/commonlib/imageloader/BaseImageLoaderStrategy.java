package com.xingqi.code.commonlib.imageloader;

import android.content.Context;

import androidx.annotation.Nullable;

public interface BaseImageLoaderStrategy<T extends ImageConfig> {

    /**
     * 加载图片
     *
     */
    void loadImage(@Nullable Context ctx, @Nullable T config);

    /**
     * 停止加载
     *
     */
    void clear(@Nullable Context ctx, @Nullable T config);
}

package com.xingqi.code.commonlib.imageloader.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
public interface GlideAppliesOptions {

    void applyGlideOptions(@NonNull Context context, @NonNull GlideBuilder builder);

    void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry);
}

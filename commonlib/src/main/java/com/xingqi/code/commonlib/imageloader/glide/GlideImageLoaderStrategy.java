package com.xingqi.code.commonlib.imageloader.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.xingqi.code.commonlib.imageloader.BaseImageLoaderStrategy;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<ImageConfigImpl>, GlideAppliesOptions {

    @Override
    public void loadImage(@Nullable Context ctx, @Nullable ImageConfigImpl config) {

        GlideRequests requests;

        requests = GlideArms.with(ctx);//如果context是activity则自动使用Activity的生命周期

        GlideRequest<Drawable> glideRequest = requests.load(config.getUrl());

        switch (config.getCacheStrategy()) {
            //缓存策略
            case CacheStrategy.NONE:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case CacheStrategy.RESOURCE:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case CacheStrategy.DATA:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case CacheStrategy.AUTOMATIC:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
        }

        if (config.isCrossFade()) {
            glideRequest.transition(DrawableTransitionOptions.withCrossFade());
        }

        if (config.isCenterCrop()) {
            glideRequest.centerCrop();
        }

        if (config.isCircle()) {
            glideRequest.circleCrop();
        }

        if (config.isImageRadius()) {
            glideRequest.transform(new RoundedCorners(config.getImageRadius()));
        }

        if (config.isBlurImage()) {
            glideRequest.transform(new BlurTransformation(config.getBlurValue()));
        }


        if (config.getPlaceholder() != 0)//设置占位符
        {
            glideRequest.placeholder(config.getPlaceholder());
        }

        if (config.getErrorPic() != 0)//设置错误的图片
        {
            glideRequest.error(config.getErrorPic());
        }

        if (config.getFallback() != 0)//设置请求 url 为空图片
        {
            glideRequest.fallback(config.getFallback());
        }

        glideRequest
                .into(config.getImageView());
    }

    @Override
    public void clear(@Nullable final Context ctx, @Nullable ImageConfigImpl config) {

        if (config.getImageView() != null) {
            GlideArms.get(ctx).getRequestManagerRetriever().get(ctx).clear(config.getImageView());
        }

        if (config.getImageViews() != null && config.getImageViews().length > 0) {//取消在执行的任务并且释放资源
            for (ImageView imageView : config.getImageViews()) {
                GlideArms.get(ctx).getRequestManagerRetriever().get(ctx).clear(imageView);
            }
        }

        if (config.isClearDiskCache()) {//清除本地缓存
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    Glide.get(ctx).clearDiskCache();
                }
            }).subscribeOn(Schedulers.io()).subscribe();
        }

        if (config.isClearMemory()) {//清除内存缓存
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    Glide.get(ctx).clearMemory();
                }
            }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
        }
    }

    @Override
    public void applyGlideOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
    }
}

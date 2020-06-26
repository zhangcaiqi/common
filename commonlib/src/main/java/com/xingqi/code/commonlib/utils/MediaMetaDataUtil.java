package com.xingqi.code.commonlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import java.io.File;
import java.util.HashMap;

public class MediaMetaDataUtil {
    public static Bitmap getBitmap(Context context, String url, boolean isSD) {
        Bitmap bitmap = null;
        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
        //的接口，用于从输入的媒体文件中取得帧和元数据；
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (isSD){
                //（）根据文件路径获取缩略图
                retriever.setDataSource(context, Uri.fromFile(new File(url)));
            }else {
                //根据网络路径获取缩略图
                retriever.setDataSource(url, new HashMap());
            }
            //获得第一帧图片
            bitmap = retriever
            .getFrameAtTime((1 * 1000 + 1L), MediaMetadataRetriever.OPTION_CLOSEST_SYNC);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }
    public static String getRingDuring(String mUri) {
        String duration = null;
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        try {
            if (mUri != null) {
                HashMap<String, String> headers = null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                }
                mmr.setDataSource(mUri, headers);
            }

            duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Exception ex) {
        } finally {
            mmr.release();
        }
        return duration;
    }
}

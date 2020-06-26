package com.xingqi.code.commonlib.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.text.DecimalFormat;

public class ImageFileUtil {

    public static boolean isExistsImage(String url){
        File imageFile = getCachedImageFile(url);
        return FileUtil.isExistFile(imageFile);
    }

    public static File getCachedImageFile(String url){
        File cacheDir = EnvironmentUtil.getExternalCacheDir();
        String imgFilePath = String.format("%s/%d.jpg",cacheDir.getPath(),url.hashCode());
        File file = new File(imgFilePath);
        return file;
    }
    public static void clearCached(){
        File cacheDir = EnvironmentUtil.getExternalCacheDir();
        FileUtil.deleteFiles(cacheDir);
    }

    public static Bitmap getBitmap(String url) {
        File imageFile = ImageFileUtil.getCachedImageFile(url);
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath());
        return bitmap;
    }
    public static ImageWH getImageWH(String url) {
        File imageFile = ImageFileUtil.getCachedImageFile(url);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//这个参数设置为true才有效，
        Bitmap bmp = BitmapFactory.decodeFile(imageFile.getPath(), options);//这里的bitmap是个
        ImageWH imageWH = new ImageWH();
        imageWH.width = options.outWidth;
        imageWH.height = options.outHeight;
        return imageWH;
    }
    public static class ImageWH {
        public int width;
        public int height;
    }

    public static long getCacheSize(){
        long size = 0l;
        File cacheDir = EnvironmentUtil.getExternalCacheDir();
        for(File file :cacheDir.listFiles()){
            size += file.length();
        }
        return size;
    }
    public static String formatCache(long size){
        String cache;
        DecimalFormat df = new DecimalFormat("#.00");
        double m = (double)size/1024/1024;
        if(m < 1){
            m = (double)size/1024;
            cache = df.format(m) + "KB";
            return cache;
        }
        cache = df.format(m) + "M";
        return cache;
    }
}

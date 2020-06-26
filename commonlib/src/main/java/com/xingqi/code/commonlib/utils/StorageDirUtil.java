package com.xingqi.code.commonlib.utils;

import android.content.Context;
import android.os.Environment;

public class StorageDirUtil {

    /**
     *
     * @return /data
     */
    public static String getDataDir(){
        return Environment.getDataDirectory().getPath();
    }

    /**
     *
     * @param context
     * @return  /data/data/com.learn.test/cache
     */
    public static String getInternalCacheDir(Context context){
        return context.getCacheDir().getPath();
    }

    /**
     *
     * @param context
     * @return /data/data/com.learn.test/files
     */
    public static String getInternalFilesDir(Context context){
        return context.getFilesDir().getPath();
    }

    /**
     *
     * @return  /storage/emulated/0
     */
    public static String getExternalStorageDir(){
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     *
     * @return /storage/emulated/0/Pictures
     */
    public static String getPublicExternalPicDir(){
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
    }

    /**
     *
     * @param dir
     * @return /storage/emulated/0/test
     */
    public static String getPublicExternalDir(String dir){
        return Environment.getExternalStoragePublicDirectory(dir).getPath();
    }

    /**
     *
     * @param context
     * @return /storage/emulated/0/Android/data/com.learn.test/cache
     */
    public static String getPrivateExternalCacheDir(Context context){
        return context.getExternalCacheDir().getPath();
    }

    /**
     *
     * @param context
     * @return /storage/emulated/0/Android/data/com.learn.test/files
     */
    public static String getPrivateExternalFilesDir(Context context){
        return context.getExternalFilesDir("").getPath();
    }

    /**
     *
     * @param context
     * @param customDir
     * @return /storage/emulated/0/Android/data/com.learn.test/files/test
     */
    public static String getPrivateExternalFilesDir(Context context,String customDir){
        return context.getExternalFilesDir(customDir).getPath();
    }

    /**
     *
     * @param context
     * @return /storage/emulated/0/Android/data/com.learn.test/files/Pictures
     */
    public static String getPrivateExternalPicDir(Context context){
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
    }

}

package com.xingqi.code.commonlib.utils;

import android.content.Context;
import android.os.Environment;


import com.xingqi.code.commonlib.base.BaseApplication;

import java.io.File;

public class EnvironmentUtil {

   private static Context context = BaseApplication.getContext();

    public static boolean hasSD(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }

    public static File getExternalFilesDir(){
        File externalFilesDir = context.getExternalFilesDir(null);
        return externalFilesDir;
    }

    public static File getExternalCacheDir(){
        File externalFilesDir = context.getExternalCacheDir();
        return externalFilesDir;
    }

    public static File getExternalDownloadDir(){
        return Environment
                .getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS);
    }

    public static String getExternalDownloadPath(){
        return context
                .getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                .getPath();
    }

    public static String getExternalPicturePath(){
        return context
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                .getPath();
    }

    public static String getPublicExternalPicturePath(){
        return Environment
                .getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES)
                .getPath();
    }

}

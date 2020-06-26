package com.xingqi.code.commonlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.content.FileProvider;

import java.io.File;
import java.lang.reflect.Field;

public class AppUtil {


    public static  void installApk(Context context, String downloadApk) {


        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(downloadApk);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, AppUtil.getPackageName(context)+".fileprovider", file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    public static Object getBuildConfigValue(Context context, String fieldName) {

        try {

            Class<?> clazz = Class.forName(context.getPackageName() + ".BuildConfig");

            Field field = clazz.getField(fieldName);

            return field.get(null);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } catch (NoSuchFieldException e) {

            e.printStackTrace();

        } catch (IllegalAccessException e) {

            e.printStackTrace();

        }

        return null;

    }



    public static void goIntentSetting(Activity activity) {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

        Uri uri = Uri.fromParts("package", AppUtil.getPackageName(activity), null);

        intent.setData(uri);

        try {

            activity.startActivity(intent);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static synchronized String getPackageName(Context context) {

        try {

            PackageManager packageManager = context.getPackageManager();

            PackageInfo packageInfo = packageManager.getPackageInfo(

                    context.getPackageName(), 0);

            return packageInfo.packageName;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }
    /**

     * [获取应用程序版本名称信息]

     * @param context

     * @return 当前应用的版本名称

     */

    public static synchronized String getVersionName(Context context) {

        try {

            PackageManager packageManager = context.getPackageManager();

            PackageInfo packageInfo = packageManager.getPackageInfo(

                    context.getPackageName(), 0);

            return packageInfo.versionName;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }





    /**

     * [获取应用程序版本名称信息]

     * @param context

     * @return 当前应用的版本名称

     */

    public static synchronized int getVersionCode(Context context) {

        try {

            PackageManager packageManager = context.getPackageManager();

            PackageInfo packageInfo = packageManager.getPackageInfo(

                    context.getPackageName(), 0);

            return packageInfo.versionCode;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return 0;

    }


}

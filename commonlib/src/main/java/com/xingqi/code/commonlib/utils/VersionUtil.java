package com.xingqi.code.commonlib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.xingqi.code.commonlib.R;
import com.xingqi.code.commonlib.base.BaseApplication;


public class VersionUtil {
    //获取版本号
    public static String getVersion(){
        Context context = BaseApplication.getContext();
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return context.getString(R.string.version_unknown);
        }
    }
    //获取版本号(内部识别号)
    public static int getVersionCode() {
        try {
            Context context = BaseApplication.getContext();
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
}

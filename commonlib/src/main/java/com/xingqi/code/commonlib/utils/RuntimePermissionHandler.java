package com.xingqi.code.commonlib.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.xingqi.code.commonlib.manager.AppManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuntimePermissionHandler {

    Activity activity;
    private Set<String> deniedPermissionSet = new HashSet<>();
    private Set<String> noAskAgainPermissionSet = new HashSet<>();

    private final int REQUEST_PERMISSION_CODE = 1024;

    private final int REQUEST_PERMISSION_SETTING = 2048;

    private String tipMessage = "您需要手动开启权限，否则无法运行程序";

    public RuntimePermissionHandler(AppCompatActivity activity) {
        this.activity = activity;
    }

    private OnRequestPermissionListener listener;

    public void requestPermissions(OnRequestPermissionListener listener, String... permissions){
        this.listener = listener;
        List<String> needRequestPermissionList = new ArrayList<>();
        for(String permission:permissions){
            if(!isGranted(permission)){
                needRequestPermissionList.add(permission);
            }
        }
        String[] needRequestPermissionArray = new String[needRequestPermissionList.size()];
        if(needRequestPermissionArray.length > 0){
            ActivityCompat.requestPermissions(activity,
                    needRequestPermissionList.toArray(needRequestPermissionArray),
                    REQUEST_PERMISSION_CODE);
        }else{
            if(null != listener){
                listener.onAllPermissionGranted();
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION_CODE){
            deniedPermissionSet.clear();
            noAskAgainPermissionSet.clear();
            for(int i=0;i<grantResults.length;i++){
                int result = grantResults[i];
                if(result == PackageManager.PERMISSION_DENIED){
                    String permission = permissions[i];
                    if(!ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
                        noAskAgainPermissionSet.add(permission);
                    }else{
                        deniedPermissionSet.add(permission);
                    }
                }
            }
            if(!deniedPermissionSet.isEmpty()){
                ActivityCompat.requestPermissions(activity,
                        deniedPermissionSet.toArray(new String[deniedPermissionSet.size()]),
                                REQUEST_PERMISSION_CODE);
            }else if(!noAskAgainPermissionSet.isEmpty()){
                toldWhy();
            }else{
                if(null != listener){
                    listener.onAllPermissionGranted();
                }
            }
        }
    }

    private void toldWhy(){
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setTitle(tipMessage)
                .setNegativeButton("退出", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    AppManager.getAppManager().appExit();
                })
                .setPositiveButton("去授权", (dialogInterface, i) -> {
                    jumpToSetting();
                    dialogInterface.dismiss();
                })
                .create();
        alertDialog.show();
    }

    private void jumpToSetting(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        String packageName =  activity.getPackageName();
        intent.setData(Uri.parse("package:" + packageName));

        activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_PERMISSION_SETTING){
            for(String permission:noAskAgainPermissionSet){
                if(!isGranted(permission)){
                    toldWhy();
                    return;
                }
            }
            if(null != listener){
                listener.onAllPermissionGranted();
            }
        }
    }

    private boolean isGranted(String permission){
        PackageManager packageManager = activity.getPackageManager();
        String packageName =  activity.getPackageName();
        int result = packageManager.checkPermission( permission,packageName);
        if(result != PackageManager.PERMISSION_GRANTED){
            return false;
        }
        return true;
    }


    public interface OnRequestPermissionListener{
        void onAllPermissionGranted();
    }
}

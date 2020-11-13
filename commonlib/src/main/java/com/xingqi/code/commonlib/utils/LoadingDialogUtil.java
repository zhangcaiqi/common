package com.xingqi.code.commonlib.utils;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.xingqi.code.commonlib.dialog.DialogLoading;


public class LoadingDialogUtil {

    public static void showLoading(FragmentManager fm){
        showLoading(fm,"loading...");
    }

    public static void showLoading(FragmentManager fm, String message){
        if(null == fm.findFragmentByTag(DialogLoading.TAG)){
            Bundle bundle = new Bundle();
            bundle.putString("message",message);
            DialogLoading dialog = DialogLoading.newInstance(bundle);
            dialog.show(fm,DialogLoading.TAG);
        }
    }

    public static void hideLoading(FragmentManager fm){
        DialogLoading dialog = (DialogLoading) fm.findFragmentByTag(DialogLoading.TAG);
        if(null != dialog){
            dialog.dismiss();
        }
    }
}

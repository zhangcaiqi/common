package com.xingqi.code.commonlib.manager;

import androidx.fragment.app.FragmentManager;

import com.xingqi.code.commonlib.dialog.LoadingDialog;

public class LoadingDialogManager {
    private static final String TAG = "LoadingDialogManager";
    private static volatile LoadingDialogManager manager;
    private LoadingDialog loadingDialog;
    private LoadingDialogManager(){}

    public static LoadingDialogManager getInstance(){
        if(null == manager){
            synchronized (LoadingDialogManager.class){
                if(null == manager){
                    manager = new LoadingDialogManager();
                }
            }
        }
        return manager;
    }
    public synchronized void showLoading(FragmentManager fm) {
        if(null == loadingDialog){
            loadingDialog = LoadingDialog.newInstance();
        }else{
            loadingDialog.dismiss();
        }
        loadingDialog.show(fm,TAG);
    }

    public synchronized void hideLoading(){
        if(null != loadingDialog){
            loadingDialog.dismiss();
        }
    }
}

package com.xingqi.code.commonlib.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtils {

    public static void shortShow(View view, String msg){
        Snackbar snackbar = makeShort(view,msg);
        snackbar.show();
    }

    public static void longShow(View view, String msg){
        Snackbar snackbar = makeLong(view,msg);
        snackbar.show();
    }

    public static Snackbar makeShort(View view,String msg){
        Snackbar snackbar = Snackbar.make(view,msg,Snackbar.LENGTH_SHORT);
        return snackbar;
    }

    public static Snackbar makeLong(View view,String msg){
        Snackbar snackbar = Snackbar.make(view,msg,Snackbar.LENGTH_LONG);
        return snackbar;
    }

    public static Snackbar setAction(Snackbar snackbar,String actionText,View.OnClickListener onClickListener){
        snackbar.setAction(actionText,onClickListener);
        return snackbar;
    }
}

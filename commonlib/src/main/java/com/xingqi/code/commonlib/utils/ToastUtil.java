package com.xingqi.code.commonlib.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;


/**
 * Created by Administrator on 2019/5/18.
 */

public class ToastUtil {
    public static void toast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void toastOnUiThread(final Activity activity, final String msg){
        if(null != activity){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}

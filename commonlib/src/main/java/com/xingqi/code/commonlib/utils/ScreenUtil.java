package com.xingqi.code.commonlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2019/5/18.
 */

public class ScreenUtil {

    public static Integer getScreenWidth(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        return widthPixels;
    }
    public static Integer getScreenHeight(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int heightPixels = displayMetrics.heightPixels;
        int notchHeight = 0;
        int deviceBrand = NotchScreenUtil.getDeviceBrand();
        if(deviceBrand == NotchScreenUtil.DEVICE_BRAND_HUAWEI){
            if(NotchScreenUtil.hasNotchInScreenAtHuawei(context)){
                notchHeight = NotchScreenUtil.getNotchSizeAtHuawei(context);
            }
        }else if(deviceBrand == NotchScreenUtil.DEVICE_BRAND_OPPO){
            if(NotchScreenUtil.hasNotchInScreenAtOppo(context)){
                notchHeight = NotchScreenUtil.getNotchSizeAtOppo();
            }
        }else if(deviceBrand == NotchScreenUtil.DEVICE_BRAND_VIVO){
            if(NotchScreenUtil.hasNotchInScreenAtVivo(context)){
                notchHeight = NotchScreenUtil.getNotchSizeAtVivo(context);
            }else{
                notchHeight = dip2px(context,24) + 1;
            }

        }
        heightPixels += notchHeight;
        return heightPixels;
    }

    public static int dip2px(Context context,float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context,float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    public static float getTextWidth(String text,float textSize){
        if(null == text || text.length() <= 0){
            return 0;
        }
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        return paint.measureText(text);
    }

    public static float getTextHeight(float textSize){
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;//文本自身高度
        //return fm.bottom - fm.top + fm.leading;//文本所在行的行高
    }

    public static float getTextAscent(float textSize){
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return Math.abs(fm.ascent);
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static int getDecorViewHeight(Activity activity){
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.bottom - rect.top;
    }
    public static int getTitleBarHeight(Activity activity){
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop - getStatusBarHeight(activity);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}

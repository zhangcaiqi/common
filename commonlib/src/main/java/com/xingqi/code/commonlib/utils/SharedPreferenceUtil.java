package com.xingqi.code.commonlib.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.xingqi.code.commonlib.base.BaseApplication;
import com.xingqi.code.commonlib.config.Constants;

import java.util.Map;

public class SharedPreferenceUtil {
    private static SharedPreferences sharedPreferences;
    private static int PRIVATE_MODE = Context.MODE_PRIVATE;
    private static Context context = BaseApplication.getContext();

    public static void save(String name, Map<String,Object> map){
        sharedPreferences = context.getSharedPreferences(name,PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(String key:map.keySet()){
            Object value = map.get(key);
            String valueString = String.valueOf(value);
            if(value.getClass().equals(String.class)){
                editor.putString(key,valueString);
            }else if(value.getClass().equals(Integer.class)){
                editor.putInt(key,Integer.parseInt(valueString));
            }else if(value.getClass().equals(Long.class)){
                editor.putLong(key,Long.parseLong(valueString));
            }
        }
        editor.commit();
    }

    public static void clear(String name){
        sharedPreferences = context.getSharedPreferences(name,PRIVATE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static String getSessionId(){
        sharedPreferences = context.getSharedPreferences(Constants.APP_SESSION_NAME,PRIVATE_MODE);
        String sessionId = sharedPreferences.getString(Constants.SESSION_ID,"");
        return sessionId;
    }
    public static String getTokenId(){
        sharedPreferences = context.getSharedPreferences(Constants.APP_SESSION_NAME,PRIVATE_MODE);
        String tokenId = sharedPreferences.getString(Constants.TOKEN_ID,"");
        return tokenId;
    }
    public static String getPhone(){
        String phone = SharedPreferenceUtil.getValue(String.class,
                Constants.APP_SESSION_NAME,
                Constants.PHONE,
                "");
        return phone;
    }
    public static <T> T getValue(Class<T> clazz,String name,String key,T defaultValue){
        T t ;
        Object value = null;
        sharedPreferences = context.getSharedPreferences(name,PRIVATE_MODE);
        if(clazz.equals(String.class)){
            value = sharedPreferences.getString(key, (String) defaultValue);
        }else if(clazz.equals(Integer.class)){
            value = sharedPreferences.getInt(key, (Integer) defaultValue);
        }else if(clazz.equals(Float.class)){
            value = sharedPreferences.getFloat(key, (Float) defaultValue);
        } else if (clazz.equals(Boolean.class)) {
            value = sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        }
        t = (T) value;
        return t;
    }
}

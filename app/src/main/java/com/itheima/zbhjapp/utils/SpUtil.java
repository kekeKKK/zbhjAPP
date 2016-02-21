package com.itheima.zbhjapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/16
 * @描述: TODO
 */
public class SpUtil {

    public static void putBoolean(Context context,String key,boolean value){
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context context,String key,boolean defValue){
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    public static void putString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context,String key,String defValue){
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static void putLong(Context context,String key,long value){
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE,Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).commit();
    }

    public static long getLong(Context context,String key,long defValue){
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    public static void putInt(Context context,String key,int value){
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE,Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }

    public static long getInt(Context context,String key,int defValue){
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }
}

package com.example.b2c.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class CacheUtils {
    /**
     * 读取缓存
     */
    public static String getCache(String key , Context context){
        SharedPreferences sp  = context.getSharedPreferences("cache", context.MODE_PRIVATE);
        return sp.getString(key, "");
    }
    
    /**
     * 写入缓存
     */
    public static void saveCache(String key , String json,Context context){
        SharedPreferences sp  = context.getSharedPreferences("cache", context.MODE_PRIVATE);
        sp.edit().putString(key, json).commit();
    }

    //把字符串转为日期
    public static Date ConverToDate(String strDate) throws Exception
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(strDate);
    }
}

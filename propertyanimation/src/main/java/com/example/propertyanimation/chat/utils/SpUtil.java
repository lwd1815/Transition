package com.example.propertyanimation.chat.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by huang on 2017/2/24.
 */

public class SpUtil {

    private static String name = "qq97";
    public static String KEY_USERNAME = "username";
    public static String KEY_PASSWORD = "password";
    private static SharedPreferences sp;



    public static void putString(Context context,String key,String value){
        if(sp==null){
            sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();
    }

    public static String getString(Context context,String key,String value){
        if(sp==null){
            sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
        return sp.getString(key,value);
    }
}

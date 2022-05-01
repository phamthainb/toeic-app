package com.ptit.toeic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MySharedPreferences {

    public static void savePreferences(Context mContext, String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("my_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).apply();
    }

    public static String getPreferences(Context mContext, String keyValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("my_data", Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyValue, "");
    }

    public static void removeAllSharedPreferences(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("my_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }
}

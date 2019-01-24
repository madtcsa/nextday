package com.kkxx.nextdaylock.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author kkxx
 * @date 2019/1/23
 */
public class BaseSp {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public BaseSp(Context context, String fileName) {
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void saveIntValue(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public int getIntValue(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void saveLongValue(String key, long value) {
        editor.putLong(key, value).apply();
    }

    public long getLongValue(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void saveStringValue(String key, String value) {
        editor.putString(key, value).apply();
    }

    public String getStringValue(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void saveBooleanValue(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }
}

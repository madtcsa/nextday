package com.kkxx.nextdaylock;

import android.util.Log;

public class LogUtils {
    private static final String TAG = "NextDay";

    public static void logd(String log) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, log);
        }
    }

    public static void loge(String log) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, log);
        }
    }

    public static void logw(String log) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, log);
        }
    }
}

package com.kkxx.nextdaylock;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;


/**
 * @author chenwei
 * @date 2018/2/13
 */

public class NextDayApplication extends Application {

    public static Resources RESOURCE;
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        RESOURCE = getResources();
        appContext = this;
    }
}

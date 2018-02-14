package com.kkxx.nextdaylock;

import android.app.Application;
import android.content.res.Resources;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author chenwei
 * @date 2018/2/13
 */

public class NextDayApplication extends Application {

    public static Resources RESOURCE;

    @Override
    public void onCreate() {
        super.onCreate();
        RESOURCE = getResources();
        Fresco.initialize(this);
    }
}

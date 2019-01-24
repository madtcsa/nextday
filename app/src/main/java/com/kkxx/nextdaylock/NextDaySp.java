package com.kkxx.nextdaylock;

import android.content.Context;

import com.kkxx.nextdaylock.base.BaseSp;

import java.util.zip.Deflater;

/**
 * @author kkxx
 * @date 2019/1/24
 */
public class NextDaySp extends BaseSp {

    private final String DATE_PREFIX = "date_";

    public NextDaySp(Context context) {
        super(context, "next_day_sp");
    }

    public void saveTargetDateJson(String date, String json) {
        saveStringValue(DATE_PREFIX + date, json);
    }

    public String getTargetDateJson(String date, String defaultVale) {
        return getStringValue(DATE_PREFIX + date, defaultVale);
    }
}

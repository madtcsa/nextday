package com.kkxx.nextdaylock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kkxx
 * @date 2019/1/24
 */
public class DateToolsUtils {

    public static String getDateFromMillSeconds(long millSeconds) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(millSeconds));
    }

}

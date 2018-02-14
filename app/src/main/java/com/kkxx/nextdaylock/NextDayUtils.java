package com.kkxx.nextdaylock;

import java.util.Calendar;

/**
 * @author chenwei
 * @date 2018/2/13
 */

public class NextDayUtils {
    private static long currentDay = System.currentTimeMillis();
    private static Calendar calendar = Calendar.getInstance();

    public static String getTodayDate() {
        currentDay = System.currentTimeMillis();
        return getDate();
    }

    private static String getDate() {

        calendar.clear();
        calendar.setTimeInMillis(currentDay);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder();
        sb.append(year).append("/");
        int ten = 10;
        if (month < ten) {
            sb.append("0").append(month);
        } else {
            sb.append(month);
        }
        sb.append("/");
        if (date < ten) {
            sb.append("0").append(date);
        } else {
            sb.append(date);
        }
        return sb.toString();
    }

    public static int getWeek() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}

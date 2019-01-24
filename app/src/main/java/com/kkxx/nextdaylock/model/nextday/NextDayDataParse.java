package com.kkxx.nextdaylock.model.nextday;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.kkxx.nextdaylock.Constants;
import com.kkxx.nextdaylock.NextDayApplication;
import com.kkxx.nextdaylock.NextDayUtils;
import com.kkxx.nextdaylock.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author chenwei
 * @date 2018/2/13
 */

public class NextDayDataParse {

    private final String TAG = NextDayDataParse.class.getSimpleName();
    private static String SCREEN_IMG_SIZE = "";

    public static NextDay getNextDay(String jsonString, String requestDate) {
        NextDay nextDay = new NextDay();
        if (null == jsonString) {
            return nextDay;
        }
        try {
            JSONObject root = new JSONObject(jsonString);
            JSONObject subObject = root.optJSONObject(requestDate.replaceAll("/", ""));
            JSONObject minSubject;
            minSubject = subObject.optJSONObject("author");
            if (minSubject != null) {
                nextDay.setName(minSubject.optString("name"));
            }
            nextDay.setBackground(subObject.optJSONObject("colors").optString("background"));
            nextDay.setCity(subObject.optJSONObject("geo").optString("reverse"));
            nextDay.setContent(subObject.optJSONObject("text").optString("short"));
            minSubject = subObject.optJSONObject("music");
            nextDay.setMusicArtist(minSubject.optString("artist"));
            nextDay.setMusicTitle(minSubject.optString("title"));
            nextDay.setMusicUrl(minSubject.optString("url").replace("{music}", Constants.BASE_MUSIC_URL));
            nextDay.setPictureImg(subObject.optJSONObject("images").optString(getImgStrKey()).replace("{img}", Constants.BASE_IMG_URL));
            String fullDateString = subObject.optString("dateKey");
            nextDay.setMonth(getShortMonthName(fullDateString.substring(4, 6)));
            nextDay.setDate(fullDateString.substring(6));
            nextDay.setWeek(getDayOfWeek(NextDayUtils.getWeek()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nextDay;
    }

    private static String getImgStrKey() {
        if ("".equals(SCREEN_IMG_SIZE)) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wmManager = (WindowManager) NextDayApplication.appContext.getSystemService(Context.WINDOW_SERVICE);
            wmManager.getDefaultDisplay().getMetrics(dm);
            int dpi = dm.densityDpi;
            if (dpi >= DisplayMetrics.DENSITY_XXXHIGH) {
                SCREEN_IMG_SIZE = "big568h3x";
            } else {
                SCREEN_IMG_SIZE = "big568h2x";
            }
        }
        return SCREEN_IMG_SIZE;
    }

    private static String getShortMonthName(String month) {
        switch (month) {
            case "01":
                return NextDayApplication.RESOURCE.getString(R.string.january_short);
            case "02":
                return NextDayApplication.RESOURCE.getString(R.string.february_short);
            case "03":
                return NextDayApplication.RESOURCE.getString(R.string.march_short);
            case "04":
                return NextDayApplication.RESOURCE.getString(R.string.april_short);
            case "05":
                return NextDayApplication.RESOURCE.getString(R.string.may_short);
            case "06":
                return NextDayApplication.RESOURCE.getString(R.string.june_short);
            case "07":
                return NextDayApplication.RESOURCE.getString(R.string.july_short);
            case "08":
                return NextDayApplication.RESOURCE.getString(R.string.august_short);
            case "09":
                return NextDayApplication.RESOURCE.getString(R.string.september_short);
            case "10":
                return NextDayApplication.RESOURCE.getString(R.string.october_short);
            case "11":
                return NextDayApplication.RESOURCE.getString(R.string.november_short);
            default:
                return NextDayApplication.RESOURCE.getString(R.string.december_short);
        }
    }

    private static String getDayOfWeek(int day) {
        switch (day) {
            case 1:
                return "SUNDAY";
            case 2:
                return "MONDAY";
            case 3:
                return "TUESDAY";
            case 4:
                return "WEDNESDAY";
            case 5:
                return "THURSDAY";
            case 6:
                return "FRIDAY";
            default:
                return "SATURDAY";
        }
    }
}

package com.kkxx.nextdaylock.model.nextday;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.kkxx.nextdaylock.Constants;
import com.kkxx.nextdaylock.LogUtils;
import com.kkxx.nextdaylock.NextDayApplication;
import com.kkxx.nextdaylock.NextDaySp;
import com.kkxx.nextdaylock.NextDayUtils;
import com.kkxx.nextdaylock.service.NextDayService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author chenwei
 * @date 2018/2/12
 */

public class NextDayViewModel extends ViewModel {

    private LiveData<NextDay> nextDayLiveData;
    private Map<String, LiveData<NextDay>> cacheData = new HashMap<>();
    private NextDaySp sp = new NextDaySp(NextDayApplication.appContext);

    public LiveData<NextDay> getNextDayLiveData(final String targetDate) {
        if (cacheData.containsKey(targetDate)) {
            return cacheData.get(targetDate);
        }
        final MutableLiveData<NextDay> nextDayData = new MutableLiveData<>();
        String cacheJson = sp.getTargetDateJson(targetDate, "");
        if (!"".equals(cacheJson)) {
            NextDay nextDay = NextDayUtils.cacheJsonToNextDay(cacheJson);
            nextDayData.postValue(nextDay);
            cacheData.put(targetDate, nextDayData);
            Log.d("NextDayViewModel", "----Cache hit--- " + cacheJson);
            Log.d("NextDayViewModel", "---cache nextDay--- " + nextDay.toString());
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_THIRD_PARTY_RUL)
                    .build();
            NextDayService service = retrofit.create(NextDayService.class);
            Call<ResponseBody> call = service.getNextDay(targetDate + ".json");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (null != response.body()) {
                        LogUtils.logd(response.message());
                        try {
                            Log.d("NextDayViewModel", "---target date-- " + targetDate);
                            String result = response.body().string();
                            NextDay nextDay = NextDayDataParse.getNextDay(result, targetDate);
                            Log.d("NextDayViewModel", "---result--- " + result);
                            nextDayData.postValue(nextDay);
                            cacheData.put(targetDate, nextDayData);
                            sp.saveTargetDateJson(targetDate, NextDayUtils.nextDayToJson(nextDay));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    LogUtils.logw(t.getMessage());
                }
            });
        }
        return nextDayLiveData = nextDayData;
    }
}

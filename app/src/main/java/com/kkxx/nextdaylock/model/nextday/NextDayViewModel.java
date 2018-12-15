package com.kkxx.nextdaylock.model.nextday;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.kkxx.nextdaylock.Constants;
import com.kkxx.nextdaylock.LogUtils;
import com.kkxx.nextdaylock.NextDayUtils;
import com.kkxx.nextdaylock.service.NextDayService;

import java.io.IOException;

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

    private final String TAG = "NextDaaViewModel";
    private LiveData<NextDay> nextDayLiveData;

    public LiveData<NextDay> getNextDayLiveData() {
        if (nextDayLiveData != null) {
            return nextDayLiveData;
        }
        final MutableLiveData<NextDay> nextDayData = new MutableLiveData<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_THIRD_PARTY_RUL)
                .build();
        NextDayService service = retrofit.create(NextDayService.class);
        final String todayDate = NextDayUtils.getTodayDate();
        Call<ResponseBody> call = service.getNextDay(todayDate + ".json");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (null != response.body()) {
                    LogUtils.logd(response.message());
                    try {
                        NextDay nextDay = NextDayDataParse.getNextDay(response.body().string(), todayDate);
                        nextDayData.postValue(nextDay);
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
        return nextDayLiveData = nextDayData;
    }
}

package com.kkxx.nextdaylock.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * @author chenwei
 * @date 2018/2/13
 */

public interface NextDayService {

    @GET("{date}")
    Call<ResponseBody> getNextDay(@Path("date") String date);
}

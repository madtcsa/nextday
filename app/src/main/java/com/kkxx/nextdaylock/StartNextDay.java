package com.kkxx.nextdaylock;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kkxx.nextdaylock.base.BaseFullScreenActivity;

/**
 * @author chenwei
 * @date 2018/2/20
 */

public class StartNextDay extends BaseFullScreenActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.startMainActivity(this);
    }
}

package com.kkxx.nextdaylock.model.music;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * @author chenwei
 * @date 2018/2/14
 */

public class MusicViewModel extends ViewModel {

    private final String TAG = MusicViewModel.class.getSimpleName();
    private LiveData<Music> musicLiveData;

    public LiveData<Music> getMusicLiveData() {

        final MutableLiveData<Music> musicMutableLiveData = new MutableLiveData<>();

        return musicMutableLiveData;
    }

}

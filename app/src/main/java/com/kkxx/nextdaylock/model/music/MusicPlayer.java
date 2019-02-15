package com.kkxx.nextdaylock.model.music;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.kkxx.nextdaylock.MainActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author chenwei
 * @date 2018/2/14
 */

public class MusicPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener {

    private final String TAG = MusicPlayer.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    private MainActivity activity;

    private Timer mTimer;
    private boolean isPrepare;
    private OnMediaListener onMediaListener;
    private final int MOVE_TIME = 15000;


    public MusicPlayer(MainActivity activity) {
        this.activity = activity;
        this.onMediaListener = activity;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setLooping(true);
        setTimer();
    }

    public void playMusic() {
        try {
            mediaPlayer.setDataSource(activity, Uri.parse(activity.getCurrentNextDay().getMusicUrl()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
    }

    public void pause() {
        if (isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void continuePlay() {
        if (!isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void setTimer() {
        mTimer = new Timer();
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (isPrepare) {
                    if (isPlaying()) {
                        onMediaListener.onSeek(mediaPlayer.getCurrentPosition(), mediaPlayer.getDuration());
                    }
                }
            }
        };
        mTimer.schedule(mTimerTask, 0, 1);
    }

    public void seekTo(int position) {
        if (position < 0 || position > mediaPlayer.getDuration()) {
            return;
        }
        mediaPlayer.seekTo(position);
    }

    public void next() {
        int curPosition = mediaPlayer.getCurrentPosition();
        if ((curPosition + MOVE_TIME) > mediaPlayer.getDuration()) {
            return;
        }
        mediaPlayer.seekTo(curPosition + MOVE_TIME);
    }

    public void previous() {
        int curPosition = mediaPlayer.getCurrentPosition();
        if ((curPosition - MOVE_TIME) < 0) {
            return;
        }
        mediaPlayer.seekTo(curPosition - MOVE_TIME);
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void release() {
        mediaPlayer.release();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isPrepare = false;
        mp.reset();
        onMediaListener.onComplete();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        isPrepare = true;
        mp.start();
        Log.d(TAG, "onPrepared");
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    public void stop(){
        mediaPlayer.stop();
        mediaPlayer.reset();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public interface OnMediaListener {

        void onSeek(int curPosition, int totalPosition);

        void onComplete();
    }
}

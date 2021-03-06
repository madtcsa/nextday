package com.kkxx.nextdaylock;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kkxx.nextdaylock.base.BaseFullScreenActivity;
import com.kkxx.nextdaylock.model.music.MusicPlayer;
import com.kkxx.nextdaylock.model.nextday.NextDay;
import com.kkxx.nextdaylock.model.nextday.NextDayViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author chenwei
 */
public class MainActivity extends BaseFullScreenActivity implements MusicPlayer.OnMediaListener, GestureDetector.OnGestureListener {

    @BindView(R.id.date_text)
    TextView dateText;
    @BindView(R.id.month_text)
    TextView monthText;
    @BindView(R.id.city_text)
    TextView cityText;
    @BindView(R.id.content_text)
    TextView contentText;
    @BindView(R.id.music_title_text)
    TextView musicTitleText;
    @BindView(R.id.singer_text)
    TextView musicArtistText;
    @BindView(R.id.author_name)
    TextView authorNameText;
    @BindView(R.id.music_switch_img)
    ImageView musicSwitchImg;
    @BindView(R.id.next_day_img)
    ImageView nextDayImg;
    @BindView(R.id.music_seekbar)
    SeekBar musicSeekBar;
    @BindView(R.id.music_layout)
    LinearLayout musicLayout;
    @BindView(R.id.layout_start_page)
    RelativeLayout startPageLayout;
    @BindView(R.id.start_up_img)
    ImageView startImgView;

    private NextDay currentNextDay;
    private MusicPlayer musicPlayer;
    private Handler mHandler = new Handler();
    private NextDayViewModel nextDayViewModel;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUi();
        setContentView(R.layout.activity_main);
        nextDayViewModel = ViewModelProviders.of(this).get(NextDayViewModel.class);
        ButterKnife.bind(this);
        startPageLayout.setVisibility(View.VISIBLE);
        getTodayData();
        gestureDetector = new GestureDetector(this, this);
        musicSeekBar.setClickable(false);
        musicSeekBar.setEnabled(false);
        musicSeekBar.setSelected(false);
        musicSeekBar.setFocusable(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != musicPlayer) {
            if (musicPlayer.isPlaying()) {
                musicSwitchImg.setImageResource(R.drawable.play);
                musicPlayer.pause();
            }
        }
    }

    private void hideSystemUi() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        MotionEvent downEvent = e1;
        MotionEvent moveEvent = e2;
        float startX = downEvent.getX();
        float endX = moveEvent.getX();
        if (startX - endX > 50F) {
            getNextData();
        } else if (startX - endX < -50F) {
            getPreviousData();
        }
        return false;
    }

    private void getTodayData() {
        nextDayViewModel.getNextDayLiveData(NextDayUtils.getTodayDate()).observe(this, new Observer<NextDay>() {
            @Override
            public void onChanged(@Nullable NextDay nextDay) {
                LogUtils.logd("NextDay: " + nextDay.toString());
                currentNextDay = nextDay;
                startAnimation();
            }
        });
    }

    private void getPreviousData() {
        nextDayViewModel.getNextDayLiveData(NextDayUtils.getPreviousDate()).observe(this, new Observer<NextDay>() {
            @Override
            public void onChanged(@Nullable NextDay nextDay) {
                LogUtils.logd("NextDay: " + nextDay.toString());
                currentNextDay = nextDay;
                showNextDay(currentNextDay);
            }
        });
    }

    private void getNextData() {
        nextDayViewModel.getNextDayLiveData(NextDayUtils.getNextDate()).observe(this, new Observer<NextDay>() {
            @Override
            public void onChanged(@Nullable NextDay nextDay) {
                LogUtils.logd("NextDay: " + nextDay.toString());
                currentNextDay = nextDay;
                showNextDay(currentNextDay);
            }
        });
    }

    private void showNextDay(NextDay nextDay) {
        Glide.with(this).asBitmap().load(Uri.parse(nextDay.getPictureImg()))
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(nextDayImg);
        dateText.setText(nextDay.getDate());
        monthText.setText(nextDay.getMonth() + NextDayApplication.RESOURCE.getString(R.string.comma) + nextDay.getWeek());
        cityText.setText(nextDay.getCity());
        contentText.setText(nextDay.getContent());
        contentText.setBackgroundColor(Color.parseColor(nextDay.getBackground()));
        musicArtistText.setText(nextDay.getMusicArtist());
        musicTitleText.setText(nextDay.getMusicTitle());
        authorNameText.setText(nextDay.getName());
        entranceAnimation();
        if (null != musicPlayer) {
            musicPlayer.stop();
            musicSeekBar.setProgress(0);
            updateSeekBarProgress();
            musicSwitchImg.setImageResource(R.drawable.play);
        }
    }

    @OnClick(R.id.music_switch_img)
    void playMusic() {
        if (null == musicPlayer) {
            updateSeekBarProgress();
            musicSwitchImg.setImageResource(R.drawable.pause);
            musicPlayer = new MusicPlayer(this);
            musicPlayer.playMusic();
        } else {
            if (musicPlayer.isPlaying()) {
                musicSwitchImg.setImageResource(R.drawable.play);
                musicPlayer.pause();
            } else {
                musicPlayer.continuePlay();
                musicSwitchImg.setImageResource(R.drawable.pause);
            }
        }
        hideSystemUi();
    }

    private void updateSeekBarProgress() {
        LayerDrawable layerDrawable = (LayerDrawable) musicSeekBar.getProgressDrawable();
        Drawable drawable = layerDrawable.getDrawable(1);
        drawable.setColorFilter(Color.parseColor(currentNextDay.getBackground()), PorterDuff.Mode.SRC);
        musicSeekBar.invalidate();
    }

    public NextDay getCurrentNextDay() {
        return currentNextDay;
    }

    public void setCurrentNextDay(NextDay currentNextDay) {
        this.currentNextDay = currentNextDay;
    }

    @Override
    public void onSeek(int curPosition, int totalPosition) {
        musicSeekBar.setProgress((int) (curPosition * 1.0f / totalPosition * 100));
    }

    @Override
    public void onComplete() {

    }

    public static void startMainActivity(Activity target) {
        Intent intent = new Intent(target, MainActivity.class);
        target.startActivity(intent);
    }

    private void entranceAnimation() {
        long animationDuration = 500L;
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator dateTextAnimator = ObjectAnimator.ofFloat(dateText, "translationX", -200F, 0F);
        ObjectAnimator monthTextAnimator = ObjectAnimator.ofFloat(monthText, "translationX", -200F, 0F);
        ObjectAnimator cityTextAnimator = ObjectAnimator.ofFloat(cityText, "translationX", -200F, 0F);
        ObjectAnimator contentTextAnimator = ObjectAnimator.ofFloat(contentText, "translationX", -200F, 0F);
        ObjectAnimator musicSwitchImgAnimator = ObjectAnimator.ofFloat(musicSwitchImg, "translationX", -200F, 0F);
        ObjectAnimator musicLayoutAnimator = ObjectAnimator.ofFloat(musicLayout, "translationX", -200F, 0F);
        set.playTogether(dateTextAnimator, monthTextAnimator, cityTextAnimator, contentTextAnimator, musicSwitchImgAnimator, musicLayoutAnimator);
        set.setDuration(animationDuration);
        set.start();
    }

    private void startAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(startImgView, "scaleX", 1F, 1.5F);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(startImgView, "scaleY", 1.0F, 1.5F);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(startPageLayout, "alpha", 1.0F, 0F);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().setBackgroundDrawable(null);
                        showNextDay(currentNextDay);
                    }
                }, 1000L);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startPageLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.playTogether(scaleX, scaleY, alpha);
        animatorSet.setDuration(1500L);
        animatorSet.setStartDelay(500L);
        animatorSet.start();
    }

}

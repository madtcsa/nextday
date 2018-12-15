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
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
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
public class MainActivity extends BaseFullScreenActivity implements MusicPlayer.OnMediaListener {

    private final String TAG = MainActivity.class.getSimpleName();

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
    SimpleDraweeView nextDayImg;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        startPageLayout.setVisibility(View.VISIBLE);
        NextDayViewModel nextDayViewModel = ViewModelProviders.of(this).get(NextDayViewModel.class);
        nextDayViewModel.getNextDayLiveData().observe(this, new Observer<NextDay>() {
            @Override
            public void onChanged(@Nullable NextDay nextDay) {
                LogUtils.logd("NextDay: " + nextDay.toString());
                currentNextDay = nextDay;
                startAnimation();
            }
        });

    }

    private void showNextDay(NextDay nextDay) {
        nextDayImg.setImageURI(Uri.parse(nextDay.getPictureImg()));
        dateText.setText(nextDay.getDate());
        monthText.setText(nextDay.getMonth() + NextDayApplication.RESOURCE.getString(R.string.comma) + nextDay.getWeek());
        cityText.setText(nextDay.getCity());
        contentText.setText(nextDay.getContent());
        contentText.setBackgroundColor(Color.parseColor(nextDay.getBackground()));
        musicArtistText.setText(nextDay.getMusicArtist());
        musicTitleText.setText(nextDay.getMusicTitle());
        authorNameText.setText(nextDay.getName());
        entranceAnimation();
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

    private void startAnimation(){
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
                },1000L);
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

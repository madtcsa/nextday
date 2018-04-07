package com.kkxx.nextdaylock;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kkxx.nextdaylock.base.BaseFullScreenActivity;

import butterknife.BindView;

/**
 * @author chenwei
 * @date 2018/2/20
 */

public class StartNextDay extends BaseFullScreenActivity {

    @BindView(R.id.start_up_img)
    ImageView startImgView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        startImgView = findViewById(R.id.start_up_img);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnimation();
//        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_set_next_day_start_up_scale);
    }

    private void startAnimation(){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(startImgView, "scaleX", 1F, 1.5F);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(startImgView, "scaleY", 1.0F, 1.5F);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(startImgView, "alpha", 1.0F, 0.5F);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                MainActivity.startMainActivity(StartNextDay.this);
                finish();
                overridePendingTransition(R.anim.anim_set_activity_alpha_out,R.anim.anim_set_activity_alpha_int);
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
        animatorSet.start();
    }
}

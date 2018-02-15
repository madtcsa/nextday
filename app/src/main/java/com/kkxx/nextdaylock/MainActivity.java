package com.kkxx.nextdaylock;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kkxx.nextdaylock.model.music.MusicPlayer;
import com.kkxx.nextdaylock.model.nextday.NextDay;
import com.kkxx.nextdaylock.model.nextday.NextDayViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author chenwei
 */
public class MainActivity extends AppCompatActivity implements MusicPlayer.OnMediaListener {

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


    private NextDay currentNextDay;
    private MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        NextDayViewModel nextDayViewModel = ViewModelProviders.of(this).get(NextDayViewModel.class);
        nextDayViewModel.getNextDayLiveData().observe(this, new Observer<NextDay>() {
            @Override
            public void onChanged(@Nullable NextDay nextDay) {
                Log.d(TAG, "NextDay: " + nextDay.toString());
                currentNextDay = nextDay;
                showNextDay(currentNextDay);
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
    }


    @OnClick(R.id.music_switch_img)
    void playMusic() {
        if (null == musicPlayer) {
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
}

package com.aptoon.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aptoon.BaseApplication;
import com.aptoon.R;
import com.aptoon.player.BaseExoPlayer;
import com.aptoon.player.ExoVideoPlayer;
import com.aptoon.utils.LocaleHelper;
import com.aptoon.utils.Logger;
import com.aptoon.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VideoPlayerActivity extends AppCompatActivity implements View.OnClickListener, BaseExoPlayer.OnVideoPlayerListener {
    public ExoVideoPlayer exoVideoPlayer;
    private TextView txtPostion, txtDuration;

    @Bind(R.id.progressbar) SeekBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);

        ButterKnife.bind(this);
        initView();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void initView() {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        initExoPlayer(viewGroup);

        String urlToStream = "http://18.220.186.69:8080/Gotoubun-no-Hanayome---01.mp4";
        exoVideoPlayer.playUrl(urlToStream, "mp4", true);


        findViewById(R.id.img_prev).setOnClickListener(this);
        findViewById(R.id.img_next).setOnClickListener(this);
        findViewById(R.id.img_backward).setOnClickListener(this);
        findViewById(R.id.img_forward).setOnClickListener(this);
        findViewById(R.id.img_playpause).setOnClickListener(this);

        txtPostion = findViewById(R.id.txt_position);
        txtDuration = findViewById(R.id.txt_duration);


        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                long duration = exoVideoPlayer.simpleExoPlayer.getDuration();
                long position = (long)((float)seekBar.getProgress()/100*duration);
                Logger.print("Position", String.valueOf(position));
                txtPostion.setText(Utils.durationToString(position));
                exoVideoPlayer.setPosition(position);
                progressBar.setProgress(seekBar.getProgress());
            }
        });
    }


    private void initExoPlayer(View view) {
        exoVideoPlayer = (new ExoVideoPlayer("ExoVideoPlayer", BaseApplication.getContext()));
        exoVideoPlayer.addOnMediaPlayerStateChangedListener(this);
        exoVideoPlayer.setup(view);
    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Dashboard.class));
        finish();
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        exoVideoPlayer.removeOnMediaPlayerStateChangedListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_next:

                break;
            case R.id.img_prev:
                break;
            case R.id.img_backward:
                exoVideoPlayer.onFastRewind();
                break;
            case R.id.img_forward:
                exoVideoPlayer.onFastForward();
                break;
            case R.id.img_playpause:
                if(exoVideoPlayer.isPlaying()) {
                    exoVideoPlayer.pausePlayer();
                    ((ImageView) findViewById(R.id.img_playpause)).setImageDrawable(BaseApplication.getContext().getResources().getDrawable(R.drawable.ico_pause));
                }
                else {
                    exoVideoPlayer.resumePlayer();
                    ((ImageView)findViewById(R.id.img_playpause)).setImageDrawable(BaseApplication.getContext().getResources().getDrawable(R.drawable.ico_play));
                }
                break;
        }
    }

    @Override
    public void onPositionChanged(int position, int duration) {
        txtPostion.setText(Utils.durationToString(position));
        txtDuration.setText(Utils.durationToString(duration));

        if(duration > 0) {
            float progress = (float)position / duration * 100;
            Logger.print("Progress", progress + ":" + (int)progress);
            progressBar.setProgress((int)progress);
            progressBar.refreshDrawableState();
        }
    }

    @Override
    public void onStatusChanged(int status) {

    }

    @Override
    public void onPlayError(boolean bDataSourceError) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height) {

    }

    @Override
    public void onPlayerReady() {

    }
}



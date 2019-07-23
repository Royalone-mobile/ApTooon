package com.aptoon.view.activity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.aptoon.R;
import com.aptoon.utils.UserSessionManager;

public class Splash_Screen extends AppCompatActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private static int SPLASH_TIME_OUT = 2000;
    SharedPreferences.Editor editor;
    UserSessionManager session;

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        videoView = findViewById(R.id.video_splash);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.splash;
        videoView.setVideoPath(videoPath);
        videoView.setOnCompletionListener(this);
        videoView.setOnPreparedListener(this);
        videoView.setOnErrorListener(this);
        videoView.setZOrderMediaOverlay(true);
        videoView.start();

//        editor = getSharedPreferences("my_Pref", MODE_PRIVATE).edit();
//        editor= getSharedPreferences("User Login Data", MODE_PRIVATE).edit();
//        editor.apply();
//
//        new Handler().postDelayed(new Runnable() {
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//            @Override
//            public void run() {
//                session = new UserSessionManager(getApplicationContext());
//                session.checkLogin();
//                finish();
//            }
//        }, SPLASH_TIME_OUT);
//
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        editor = getSharedPreferences("my_Pref", MODE_PRIVATE).edit();
        editor= getSharedPreferences("User Login Data", MODE_PRIVATE).edit();
        editor.apply();
        session = new UserSessionManager(getApplicationContext());
        session.checkLogin();
        finish();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
}


package com.aptoon.player;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.aptoon.R;
import com.aptoon.utils.Logger;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;


@SuppressWarnings({"WeakerAccess", "unused"})
public class ExoVideoPlayer extends BaseExoPlayer implements SimpleExoPlayer.VideoListener {
    public static final boolean DEBUG = true;
    public final String TAG;

    /*//////////////////////////////////////////////////////////////////////////
    // Views
    //////////////////////////////////////////////////////////////////////////*/

    private View rootView;

    private AspectRatioFrameLayout aspectRatioFrameLayout;
    private SurfaceView surfaceView;
    private View surfaceForeground;

    private Handler controlsVisibilityHandler = new Handler();

    ///////////////////////////////////////////////////////////////////////////

    public ExoVideoPlayer(String debugTag, Context context) {
        super(context);
        this.TAG = debugTag;
        this.context = context;
    }

    public void setup(View rootView) {
        initViews(rootView);
        setup();
    }



    public void initViews(View rootView) {
        this.rootView = rootView;
        this.aspectRatioFrameLayout = (AspectRatioFrameLayout) rootView.findViewById(R.id.aspectRatioLayout);
        this.surfaceForeground = (View) rootView.findViewById(R.id.surfaceForeground);
        this.surfaceView = (SurfaceView) rootView.findViewById(R.id.vlc_surface);
    }

    @Override
    public void initListeners() {
        super.initListeners();
    }

    @Override
    public void initPlayer() {
        super.initPlayer();

        simpleExoPlayer.setVideoSurfaceView(surfaceView);
        simpleExoPlayer.setVideoListener(this);
    }

    @Override
    public void playUrl(String url, String format, boolean autoPlay) {
        if (DEBUG) Log.d(TAG, "play() called with: url = [" + url + "], autoPlay = [" + autoPlay + "]");

        if (url == null || simpleExoPlayer == null) {
            return;
        }

        currentVideoWidth = 0;
        currentVideoHeight = 0;
        droppedFrames = 0;

        super.playUrl(url, format, autoPlay);
    }

    @Override
    public MediaSource buildMediaSource(String url, String overrideExtension) {
        MediaSource mediaSource = super.buildMediaSource(url, overrideExtension);
        return  mediaSource;
    }


    /*//////////////////////////////////////////////////////////////////////////
    // States Implementation
    //////////////////////////////////////////////////////////////////////////*/

    @Override
    public void onLoading() {
        if (!isProgressLoopRunning.get()) startProgressLoop();
        if(surfaceForeground!=null)
            surfaceForeground.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPlaying() {
        if (!isProgressLoopRunning.get()) startProgressLoop();

        if (surfaceForeground != null) {
            surfaceForeground.setVisibility(View.GONE);
        }
        setMute(false);
    }


    @Override
    public void onBuffering() {
    }

    @Override
    public void onPausedSeek() {
        Logger.print("onPausedSeek");
    }



    @Override
    public void onPlayerStatusChanged(int status){
        for (int i = 0; i < OnVideoPlayerListeners.size(); i++) {
            OnVideoPlayerListeners.get(i).onStatusChanged(status);
        }

    }

    @Override
    public void onPaused() {
        Logger.print("onPaused");
    }
    @Override
    public void onCompleted() {
        Logger.print("onCompleted");

        if (isProgressLoopRunning.get()) stopProgressLoop();

        if (currentRepeatMode == RepeatMode.REPEAT_ONE) {
            changeState(STATE_LOADING);
            simpleExoPlayer.seekTo(0);
        }
    }

    public void setPosition(long position) {
        simpleExoPlayer.seekTo(position);
    }

    public void forward() {
        Logger.print("ExoPlayer.currentPosition" + simpleExoPlayer.getCurrentPosition());
        setPosition(simpleExoPlayer.getCurrentPosition() + 30000);
    }

    public void backward(){
        Logger.print("ExoPlayer.currentPosition" + simpleExoPlayer.getCurrentPosition());
        setPosition(simpleExoPlayer.getCurrentPosition() - 30000);
    }

    /*//////////////////////////////////////////////////////////////////////////
    // ExoPlayer Video Listener
    //////////////////////////////////////////////////////////////////////////*/

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
//        aspectRatioFrameLayout.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        aspectRatioFrameLayout.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        Logger.print( "onVideoSizeChanged() called with: width / height = [" + width + " / " + height + " = " + (((float) width) / height) + "], unappliedRotationDegrees = [" + unappliedRotationDegrees + "], pixelWidthHeightRatio = [" + pixelWidthHeightRatio + "]");

        for (int i = 0; i < OnVideoPlayerListeners.size(); i++) {
            OnVideoPlayerListeners.get(i).onVideoSizeChanged(width,height);
        }

    }

    @Override
    public void onRenderedFirstFrame() {
        Logger.print("onRenderedFirstFrame");

        if(surfaceForeground!=null)
            surfaceForeground.setVisibility(View.GONE);
        setMute(true);
    }

    /*//////////////////////////////////////////////////////////////////////////
    // General Player
    //////////////////////////////////////////////////////////////////////////*/

    @Override
    public void onPrepared(boolean playWhenReady) {
        if (DEBUG) Log.d(TAG, "onPrepared() called with: playWhenReady = [" + playWhenReady + "]");
        Logger.print("onPrepared");
        setMute(false);

        super.onPrepared(playWhenReady);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void onUpdateProgress(int currentProgress, int duration, int bufferPercent) {
        if (!isPrepared) {
            currentProgress = 0;
            duration = 0;
        }
        for (int i = 0; i < OnVideoPlayerListeners.size(); i++) {
            OnVideoPlayerListeners.get(i).onPositionChanged(currentProgress, duration);
        }
    }

    @Override
    public void onVideoPlayPauseRepeat() {
        super.onVideoPlayPauseRepeat();
    }

    @Override
    public void onFastRewind() {
        super.onFastRewind();
    }

    @Override
    public void onFastForward() {
        super.onFastForward();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    public void setVolume(int volume){
        float f_vol = (float) volume/(float) 100.0;
        simpleExoPlayer.setVolume(f_vol);
    }

    public float getVolume(){
        return simpleExoPlayer.getVolume();
    }

    public AspectRatioFrameLayout getAspectRatioFrameLayout() {
        return aspectRatioFrameLayout;
    }

    public SurfaceView getSurfaceView() {
        return surfaceView;
    }

    public Handler getControlsVisibilityHandler() {
        return controlsVisibilityHandler;
    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public View getSurfaceForeground() {
        return surfaceForeground;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {

    }
}

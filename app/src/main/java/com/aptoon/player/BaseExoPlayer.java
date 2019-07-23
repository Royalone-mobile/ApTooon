package com.aptoon.player;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;

import com.aptoon.BaseApplication;
import com.aptoon.utils.Logger;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.metadata.id3.CommentFrame;
import com.google.android.exoplayer2.metadata.id3.GeobFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Frame;
import com.google.android.exoplayer2.metadata.id3.PrivFrame;
import com.google.android.exoplayer2.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer2.metadata.id3.UrlLinkFrame;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;


@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseExoPlayer implements ExoPlayer.EventListener, AudioManager.OnAudioFocusChangeListener {
    public static final boolean DEBUG = false;
    public static final String TAG = "BaseExoPlayer";

    protected Context context;
    protected SharedPreferences sharedPreferences;
    protected AudioManager audioManager;

    protected BroadcastReceiver broadcastReceiver;
    protected IntentFilter intentFilter;

    /*//////////////////////////////////////////////////////////////////////////
    // Intent
    //////////////////////////////////////////////////////////////////////////*/

    public static final String VIDEO_URL = "video_url";
    public static final String VIDEO_TITLE = "video_title";
    public static final String VIDEO_THUMBNAIL_URL = "video_thumbnail_url";
    public static final String START_POSITION = "start_position";
    public static final String CHANNEL_NAME = "channel_name";

    protected int videoStartPos = -1;
    protected String channelName = "";
    /*//////////////////////////////////////////////////////////////////////////
    // Player
    //////////////////////////////////////////////////////////////////////////*/

    float currentvolume = 0;
    public int FAST_FORWARD_REWIND_AMOUNT = 10000; // 10 Seconds
    public static final String CACHE_FOLDER_NAME = "exoplayer";

    public SimpleExoPlayer simpleExoPlayer;
    protected boolean isPrepared = false;

    protected MediaSource mediaSource;
    protected CacheDataSourceFactory cacheDataSourceFactory;
    private DefaultDataSourceFactory dataSourceFactory;
    protected final DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
    protected DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    protected ArrayList<OnVideoPlayerListener> OnVideoPlayerListeners;
    protected int PROGRESS_LOOP_INTERVAL = 100;
    protected AtomicBoolean isProgressLoopRunning = new AtomicBoolean();
    protected Handler progressLoop;
    protected Runnable progressUpdate;

    protected int currentVideoWidth;
    protected int currentVideoHeight;
    protected int droppedFrames = 0;
    public String timeTextLanguage;

    private String url = "";

    //////////////////////////////////////////////////////////////////////////*/
    public BaseExoPlayer(Context context) {
        this.context = context;
        this.progressLoop = new Handler();
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.audioManager = ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE));
        this.OnVideoPlayerListeners = new ArrayList<>();
        this.broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onBroadcastReceived(intent);
            }
        };
        this.intentFilter = new IntentFilter();
        setupBroadcastReceiver(intentFilter);
        context.registerReceiver(broadcastReceiver, intentFilter);

        bandwidthMeter = new DefaultBandwidthMeter(new Handler(), new BandwidthMeter.EventListener() {
            @Override
            public void onBandwidthSample(int elapsedMs, long bytes, long bitrate) {

            }
        });

    }

    public void addOnMediaPlayerStateChangedListener(OnVideoPlayerListener listener) {
        if (!OnVideoPlayerListeners.contains(listener)) {
            OnVideoPlayerListeners.add(listener);
        }
    }


    public void removeOnMediaPlayerStateChangedListener(OnVideoPlayerListener listener) {
        OnVideoPlayerListeners.remove(listener);
    }

    public interface OnVideoPlayerListener {
        void onPositionChanged(int position, int duration);
        void onStatusChanged(int status);
        void onPlayError(boolean bDataSourceError);
        void onVideoSizeChanged(int width, int height);

        void onPlayerReady();
    }

    public void setup() {
        if (simpleExoPlayer == null)
            initPlayer();
        initListeners();
    }


    DataSource dataSource;

    private void initExoPlayerCache() {
        if (cacheDataSourceFactory == null) {
            dataSourceFactory = new DefaultDataSourceFactory(context, BaseApplication.getApplication().getUserAgent(), bandwidthMeter);
            File cacheDir = new File(context.getExternalCacheDir(), CACHE_FOLDER_NAME);
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            }

            SimpleCache simpleCache = new SimpleCache(cacheDir, new LeastRecentlyUsedCacheEvictor(16 * 1024 * 1024L));
        }
    }

    DefaultTrackSelector defaultTrackSelector;

    public void initPlayer() {
        if (DEBUG) Log.d(TAG, "initPlayer() called with: context = [" + context + "]");
        dataSourceFactory = new DefaultDataSourceFactory(context, BaseApplication.getApplication().getUserAgent(), bandwidthMeter);
        AdaptiveTrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);


        DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(trackSelectionFactory);
        this.defaultTrackSelector = defaultTrackSelector;

        DefaultLoadControl loadControl = new DefaultLoadControl();
        @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER;

        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(BaseApplication.getContext(),
                null, extensionRendererMode);

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, defaultTrackSelector, loadControl, null, extensionRendererMode);
        simpleExoPlayer.setRepeatMode(1);
        simpleExoPlayer.setShuffleModeEnabled(true);

    }
    public void initListeners() {
        progressUpdate = new Runnable() {
            @Override
            public void run() {
                //if(DEBUG) Log.d(TAG, "progressUpdate run() called");
                onUpdateProgress((int) simpleExoPlayer.getCurrentPosition(), (int) simpleExoPlayer.getDuration(), simpleExoPlayer.getBufferedPercentage());
                if (isProgressLoopRunning.get()) progressLoop.postDelayed(this, PROGRESS_LOOP_INTERVAL);
            }
        };
    }

    public void setMute(boolean toMute){
        if(toMute){
            currentvolume = simpleExoPlayer.getVolume();
             simpleExoPlayer.setVolume(0f);
        } else {
            simpleExoPlayer.setVolume(currentvolume);
        }
    }


    @SuppressLint("WrongConstant")
    public void playUrl(String url, String format, boolean autoPlay) {
        this.url = url;
        if (DEBUG) {
            Log.d(TAG, "play() called with: url = [" + url + "], autoPlay = [" + autoPlay + "]");
        }

        if (url == null || simpleExoPlayer == null) {
            RuntimeException runtimeException = new RuntimeException((url == null ? "Url " : "Player ") + " null");
            throw runtimeException;
        }

        changeState(STATE_LOADING);


        isPrepared = false;

        mediaSource = buildMediaSource(url, format);

        if (simpleExoPlayer.getPlaybackState() != ExoPlayer.STATE_IDLE) simpleExoPlayer.stop();
        if (videoStartPos > 0) simpleExoPlayer.seekTo(videoStartPos);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(autoPlay);
        simpleExoPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC);
    }

    public void stopPlayer(){
        try {
            if (simpleExoPlayer != null) {
                simpleExoPlayer.stop();
            }
        }catch (Exception e) {

        }
    }
    public void destroyPlayer() {
        try {
            if (simpleExoPlayer != null) {
                simpleExoPlayer.setPlayWhenReady(false);
                simpleExoPlayer.stop();
                simpleExoPlayer.release();
                simpleExoPlayer.clearVideoSurface();
                simpleExoPlayer.setVideoSurfaceView(null);
                simpleExoPlayer = null;
            }
            if (progressLoop != null && isProgressLoopRunning.get()) stopProgressLoop();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        if (DEBUG) Log.d(TAG, "destroy() called");
        destroyPlayer();
        unregisterBroadcastReceiver();
        simpleExoPlayer = null;
    }

    public void resumePlayer(){
        if(getPlayer()==null) return;
        getPlayer().setPlayWhenReady(true);
        onPlayerStatusChanged(STATE_PLAYING);
    }
    public void pausePlayer(){
        if(getPlayer()==null) return;
        getPlayer().setPlayWhenReady(false);
        onPlayerStatusChanged(STATE_PAUSED);
    }

    public MediaSource buildMediaSource(String url, String overrideExtension) {
        Uri uri = Uri.parse(url);
        int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri) : Util.inferContentType("." + overrideExtension);
        MediaSource mediaSource;

        switch (type) {
            case C.TYPE_SS:
                mediaSource = new SsMediaSource(uri, dataSourceFactory, new DefaultSsChunkSource.Factory(dataSourceFactory), null, null);
                break;
            case C.TYPE_DASH:
                mediaSource = new DashMediaSource(uri, dataSourceFactory, new DefaultDashChunkSource.Factory(dataSourceFactory), null, null);
                break;
            case C.TYPE_HLS:
                mediaSource = new HlsMediaSource(uri, dataSourceFactory, null, null);
                break;
            case C.TYPE_OTHER:
                mediaSource =  new ExtractorMediaSource.Factory(dataSourceFactory).setExtractorsFactory().createMediaSource(uri, null,null);
                break;
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
        return mediaSource;
    }

    /*//////////////////////////////////////////////////////////////////////////
    // Broadcast Receiver
    //////////////////////////////////////////////////////////////////////////*/

    /**
     * Add your action in the intentFilter
     *
     * @param intentFilter intent filter that will be used for register the receiver
     */
    protected void setupBroadcastReceiver(IntentFilter intentFilter) {
        intentFilter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
    }

    public void onBroadcastReceived(Intent intent) {
        switch (intent.getAction()) {
            case AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                if (isPlaying()) simpleExoPlayer.setPlayWhenReady(false);
                break;
        }
    }

    public void unregisterBroadcastReceiver() {
        if (broadcastReceiver != null && context != null) {
            context.unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    /*//////////////////////////////////////////////////////////////////////////
    // AudioFocus
    //////////////////////////////////////////////////////////////////////////*/

    private static final int DUCK_DURATION = 1500;
    private static final float DUCK_AUDIO_TO = .2f;

    /*//////////////////////////////////////////////////////////////////////////
    // States Implementation
    //////////////////////////////////////////////////////////////////////////*/

    public static final int STATE_LOADING = 123;
    public static final int STATE_PLAYING = 124;
    public static final int STATE_BUFFERING = 125;
    public static final int STATE_PAUSED = 126;
    public static final int STATE_PAUSED_SEEK = 127;
    public static final int STATE_COMPLETED = 128;


    protected int currentState = -1;

    public void changeState(int state) {
        if (DEBUG) Log.d(TAG, "changeState() called with: state = [" + state + "]");
        currentState = state;
        onPlayerStatusChanged(currentState);
        switch (state) {
            case STATE_LOADING:
                onLoading();
                break;
            case STATE_PLAYING:
                onPlaying();
                break;
            case STATE_BUFFERING:
                onBuffering();
                break;
            case STATE_PAUSED:
                onPaused();
                break;
            case STATE_PAUSED_SEEK:
                onPausedSeek();
                break;
            case STATE_COMPLETED:
                onCompleted();
                break;
        }
    }

    public void onLoading() {
        if (!isProgressLoopRunning.get()) startProgressLoop();
    }

    public void onPlaying() {
        if (!isProgressLoopRunning.get()) startProgressLoop();
    }

    public void onBuffering() {
    }

    public void onPaused() {
        if (isProgressLoopRunning.get()) stopProgressLoop();
    }

    public void onPausedSeek() {
    }

    public void onCompleted() {
        if (DEBUG) Log.d(TAG, "onCompleted() called");
        if (isProgressLoopRunning.get()) stopProgressLoop();

        if (currentRepeatMode == RepeatMode.REPEAT_ONE) {
            changeState(STATE_LOADING);
            simpleExoPlayer.seekTo(0);

        }
    }

    protected RepeatMode currentRepeatMode = RepeatMode.REPEAT_DISABLED;

    public enum RepeatMode {
        REPEAT_DISABLED,
        REPEAT_ONE,
        REPEAT_ALL
    }

    public void onRepeatClicked() {
        setCurrentRepeatMode(getCurrentRepeatMode() == RepeatMode.REPEAT_DISABLED ?
                RepeatMode.REPEAT_ONE :
                RepeatMode.REPEAT_DISABLED);
    }

    private TrackGroupArray trackGroups;
    private TrackSelectionArray trackSelections;

    public void updateTrackString() {
        onTracksChanged(this.trackGroups, this.trackSelections);
    }


    @Override
    public void onLoadingChanged(boolean isLoading) {
        if (!isLoading && getCurrentState() == STATE_PAUSED && isProgressLoopRunning.get()) stopProgressLoop();
        else if (isLoading && !isProgressLoopRunning.get()) startProgressLoop();
    }

    long bufferingTime = 0;
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (getCurrentState() == STATE_PAUSED_SEEK) {
            if (DEBUG) Log.d(TAG, "onPlayerStateChanged() currently on PausedSeek");
            return;
        }
        try {
            if (playbackState == Player.STATE_BUFFERING){
                bufferingTime = System.currentTimeMillis();
            }

            if(playbackState == Player.STATE_READY){
                long mStateChangeTimeDiff = System.currentTimeMillis() - bufferingTime;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        switch (playbackState) {
            case ExoPlayer.STATE_IDLE: // 1
                isPrepared = false;
                break;
            case ExoPlayer.STATE_BUFFERING: // 2
                if (isPrepared && getCurrentState() != STATE_LOADING) changeState(STATE_BUFFERING);
                break;
            case ExoPlayer.STATE_READY: //3
                if (!isPrepared) {
                    isPrepared = true;
                    onPrepared(playWhenReady);
                    break;
                }
                if (currentState == STATE_PAUSED_SEEK) break;
                changeState(playWhenReady ? STATE_PLAYING : STATE_PAUSED);
                break;
            case ExoPlayer.STATE_ENDED: // 4
                changeState(STATE_COMPLETED);
                isPrepared = false;
                break;
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

        try {
        if(error.getCause()!=null &&  error.getCause().getLocalizedMessage()!=null && error.getCause().getLocalizedMessage().contains("Response code: 403")){
            for (int i = 0; i < OnVideoPlayerListeners.size(); i++) {
                OnVideoPlayerListeners.get(i).onPlayError(true);
            }
            Logger.print("DataSource ERROR");
        }
        else {
            for (int i = 0; i < OnVideoPlayerListeners.size(); i++) {
                OnVideoPlayerListeners.get(i).onPlayError(false);
            }
            Logger.print("No DataSource ERROR");
        }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*//////////////////////////////////////////////////////////////////////////
    // General Player
    //////////////////////////////////////////////////////////////////////////*/


    public void onPrepared(boolean playWhenReady) {
        if (playWhenReady) audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        for (int i = 0; i < OnVideoPlayerListeners.size(); i++) {
            OnVideoPlayerListeners.get(i).onPlayerReady();
        }

        changeState(playWhenReady ? STATE_PLAYING : STATE_PAUSED);
    }

    public abstract void onPlayerStatusChanged(int status);
    public abstract void onUpdateProgress(int currentProgress, int duration, int bufferPercent);

    public void onVideoPlayPause() {
        if (DEBUG) Log.d(TAG, "onVideoPlayPause() called");

        if (currentState == STATE_COMPLETED) {
            onVideoPlayPauseRepeat();
            return;
        }

        if (!isPlaying()) audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        else audioManager.abandonAudioFocus(null);

        simpleExoPlayer.setPlayWhenReady(!isPlaying());
    }

    public void onVideoPlayPauseRepeat() {
        changeState(STATE_LOADING);
        setVideoStartPos(0);
        simpleExoPlayer.seekTo(0);
        simpleExoPlayer.setPlayWhenReady(true);
        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    public void onFastRewind() {
        seekBy(-FAST_FORWARD_REWIND_AMOUNT);
    }

    public void onFastForward() {
        seekBy(FAST_FORWARD_REWIND_AMOUNT);
    }


    public void seekBy(int milliSeconds) {
        if (simpleExoPlayer == null) return;
        int progress = (int) (simpleExoPlayer.getCurrentPosition() + milliSeconds);
        if (progress < 0) progress = 0;
        simpleExoPlayer.seekTo(progress);
    }

    public void seekTo(int milliSeconds) {
        if (simpleExoPlayer == null) return;
        int progress = milliSeconds;
        if (progress < 0) progress = 0;
        simpleExoPlayer.seekTo(progress);
    }
    public boolean isPlaying() {
        if(simpleExoPlayer==null) return false;
        return simpleExoPlayer.getPlaybackState() == ExoPlayer.STATE_READY && simpleExoPlayer.getPlayWhenReady();
    }



    /*//////////////////////////////////////////////////////////////////////////
    // Utils
    //////////////////////////////////////////////////////////////////////////*/

    private final StringBuilder stringBuilder = new StringBuilder();
    private final Formatter formatter = new Formatter(stringBuilder, Locale.getDefault());

    public String getTimeString(int milliSeconds) {
        long seconds = (milliSeconds % 60000L) / 1000L;
        long minutes = (milliSeconds % 3600000L) / 60000L;
        long hours = (milliSeconds % 86400000L) / 3600000L;
        long days = (milliSeconds % (86400000L * 7L)) / 86400000L;

        stringBuilder.setLength(0);
        return days > 0 ? formatter.format("%d:%02d:%02d:%02d", days, hours, minutes, seconds).toString()
                : hours > 0 ? formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
                : formatter.format("%02d:%02d", minutes, seconds).toString();
    }

    protected void startProgressLoop() {
        progressLoop.removeCallbacksAndMessages(null);
        isProgressLoopRunning.set(true);
        progressLoop.post(progressUpdate);
    }

    protected void stopProgressLoop() {
        isProgressLoopRunning.set(false);
        progressLoop.removeCallbacksAndMessages(null);
    }

    protected void tryDeleteCacheFiles(Context context) {
        File cacheDir = new File(context.getExternalCacheDir(), CACHE_FOLDER_NAME);

        if (cacheDir.exists()) {
            try {
                if (cacheDir.isDirectory()) {
                    for (File file : cacheDir.listFiles()) {
                        try {
                            if (DEBUG) Log.d(TAG, "tryDeleteCacheFiles: " + file.getAbsolutePath() + " deleted = " + file.delete());
                        } catch (Exception ignored) {
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    public void triggerProgressUpdate() {
        onUpdateProgress((int) simpleExoPlayer.getCurrentPosition(), (int) simpleExoPlayer.getDuration(), simpleExoPlayer.getBufferedPercentage());
    }

    public void animateAudio(final float from, final float to, int duration) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(from, to);
        valueAnimator.setDuration(duration);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (simpleExoPlayer != null) simpleExoPlayer.setVolume(from);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (simpleExoPlayer != null) simpleExoPlayer.setVolume(to);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (simpleExoPlayer != null) simpleExoPlayer.setVolume(to);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (simpleExoPlayer != null) simpleExoPlayer.setVolume(((float) animation.getAnimatedValue()));
            }
        });
        valueAnimator.start();
    }

    /*//////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    //////////////////////////////////////////////////////////////////////////*/

    public SimpleExoPlayer getPlayer() {
        return simpleExoPlayer;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public RepeatMode getCurrentRepeatMode() {
        return currentRepeatMode;
    }

    public void setCurrentRepeatMode(RepeatMode mode) {
        currentRepeatMode = mode;
    }

    public int getCurrentState() {
        return currentState;
    }


    public int getVideoStartPos() {
        return videoStartPos;
    }

    public void setVideoStartPos(int videoStartPos) {
        this.videoStartPos = videoStartPos;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public boolean isCompleted() {
        return simpleExoPlayer != null && simpleExoPlayer.getPlaybackState() == ExoPlayer.STATE_ENDED;
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public void setPrepared(boolean prepared) {
        isPrepared = prepared;
    }

    public int getCurrentVideoWidth(){
        return this.currentVideoWidth;
    }

    public int getCurrentVideoHeight(){
        return this.currentVideoHeight;
    }

    public int getDroppedFrames(){
        return this.droppedFrames;
    }

    public String getVideoUrl(){
        return this.url;
    }
}

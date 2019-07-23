package com.aptoon;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;

import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class BaseApplication extends Application {
    private static Gson gson;
    private static BaseApplication application;
    private static Handler handler;

    public static BaseApplication getApplication() {
        return application;
    }


    public static void setApplication(BaseApplication application) {
        BaseApplication.application = application;
    }

    public static int streamsCount = 0;
    public static Handler getHandler() {
        return handler;
    }
    public static void setHandler(Handler handler) {
        BaseApplication.handler = handler;
    }
    public static Gson getGson() {
        return gson;
    }
    public static void setGson(Gson gson) {
        BaseApplication.gson = gson;
    }

    private String userAgent;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        userAgent = Util.getUserAgent(this, getPackageName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        handler = new Handler(getMainLooper());
        gson = new Gson();
        deleteCache();
    }

    public static void deleteCache() {
        try {
            deleteCache(BaseApplication.getContext());
        } catch (Exception e) {}
    }

    public static void deleteCache(Context context) {
        try {
            File cacheDir = context.getCacheDir();
            File externalCacheDir = context.getExternalCacheDir();
            deleteDir(cacheDir);
            deleteDir(externalCacheDir);
        } catch (Exception e) {}
    }


    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static float getDimension(int dimensionId) {
        return application.getResources().getDimension(dimensionId);
    }

    public static SharedPreferences getPreferenceManager() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

    public static List<String> getStringArrayAsList(int stringArrayResId) {
        return Arrays.asList(getStringArray(stringArrayResId));
    }

    public static String[] getStringArray(int stringArrayResId) {
        return application.getResources().getStringArray(stringArrayResId);
    }

    public static String getStr(int stringResId) {
        return application.getString(stringResId);
    }

    public static boolean isServiceRunning(Class<?> playerServiceClass) {
        ActivityManager manager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (playerServiceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void executeOnUIThread(Runnable runnable) {
        application.getHandler().post(runnable);
    }



    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}

package com.aptoon.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    public static void setAnimation(View viewToAnimate, int position, int lp) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lp) {
            ScaleAnimation anim = new ScaleAnimation(0.5f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(
//                    500
                    new Random().nextInt(800)
            );
            viewToAnimate.startAnimation(anim);
//            lastPosition = position;
        }
    }
    //get facebook integration hash key
    public void printHashKey(Context context) {
        String TAG = "ISing_world";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }
    public static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null) {
                return netInfos.isConnected();
            }
        }
        return false;
    }
    //check permission and allow by user in App
    public static boolean checkAndRequestPermissions(Context context) {
        int camerapermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int writepermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readpermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int internetpermission = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);
        int wifiaccespermisson = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_WIFI_STATE);
        int networkpermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (internetpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (wifiaccespermisson != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (networkpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public static String durationToString(long millis) {
        long secondsTotal = millis / 1000;
        long hours, minutes, seconds;

        hours = secondsTotal / 3600;
        minutes = (secondsTotal / 60) % 60;
        seconds = secondsTotal % 60;

        String time = "";

        if (hours > 0) {
            if (hours < 10) {
                time += "0";
            }
            time += hours;
            time += ":";
        }

        if (minutes < 10) {
            time += "0";
        }
        time += minutes;
        time += ":";

        if (seconds < 10) {
            time += "0";
        }
        time += seconds;

        return time;
    }


}



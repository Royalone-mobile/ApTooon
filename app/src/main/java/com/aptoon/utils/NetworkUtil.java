package com.aptoon.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;


/**
 * Created by anartzmugika on 22/6/16.
 */

public class NetworkUtil extends BroadcastReceiver{
	public NetworkUtil() {
	}
	private static int TYPE_WIFI = 1;
	private static int TYPE_MOBILE = 2;
	private static int TYPE_NOT_CONNECTED = 0;
	public static final String CONNECT_TO_WIFI = "WIFI";
	public static final String CONNECT_TO_MOBILE = "MOBILE";
	public static final String NOT_CONNECT = "NOT_CONNECT";
	public final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
	@Override
	public void onReceive(Context context, Intent intent) {
		boolean isVisible = InternetConnection.isActivityVisible();
			 getConnectivityStatusString(context);
//				if(isVisible==true) {
//					Log.e("Receiver ", "" + status);
//
//					if (status.isEmpty()) {
//						Log.e("Receiver1 ", "not connection");// your code when internet lost
//
//
//					} else {
//						Log.e("Receiver11 ", "connected to internet");//your code when internet connection come back
//					}
//				}

		 }

	public static int getConnectivityStatus(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		assert cm != null;
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

		if (null != activeNetwork) {

			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return TYPE_WIFI;

			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return TYPE_MOBILE;
		}
		return TYPE_NOT_CONNECTED;
	}

	public static String getConnectivityStatusString(Context context) {

		int conn = NetworkUtil.getConnectivityStatus(context);

		String status = null;
		if (conn == NetworkUtil.TYPE_WIFI) {
			//status = "Wifi enabled";
			status = CONNECT_TO_WIFI;
		} else if (conn == NetworkUtil.TYPE_MOBILE) {
			//status = "Mobile data enabled";
			System.out.println(CONNECT_TO_MOBILE);
			status = getNetworkClass(context);
		} else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
			status =NOT_CONNECT;
		}

		//return status + " / " + DateTime.getCurrentDataTime();
		return status;
	}

	private static String getNetworkClass(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		assert cm != null;
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info == null || !info.isConnected())
			return "-"; //not connected
		if(info.getType() == ConnectivityManager.TYPE_WIFI)
			return "WIFI";
		if(info.getType() == ConnectivityManager.TYPE_MOBILE){
			int networkType = info.getSubtype();
			switch (networkType) {
				case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
					return "3G";
				case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
					return "4G";
				default:
					return "UNKNOWN";
			}
		}
		return "UNKNOWN";
	}


}
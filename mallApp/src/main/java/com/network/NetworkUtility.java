package com.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
/**
 * 
 * @author Muhammad Babar Naveed
 *    Date Feb 26, 2013
 *
 */
public class NetworkUtility {

	public static boolean isWifiConnected(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return ni != null && ni.getState() == NetworkInfo.State.CONNECTED;
	}
	
	public static boolean isHighSpeedMobileNetworkAvailable(Context context){
		boolean result = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager != null){
			NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (networkInfo != null && networkInfo.getSubtype() >= TelephonyManager.NETWORK_TYPE_UMTS){
				result = networkInfo.isConnected();
			}
		}
		return result;
	}
}

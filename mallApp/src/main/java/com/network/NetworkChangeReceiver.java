/**
 * 
 */
package com.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;


/**
 * @author Muhammad Babar Naveed
 *    Date Apr 23, 2013
 * 
 */
public class NetworkChangeReceiver extends BroadcastReceiver{
	@SuppressWarnings("unused")
	private SharedPreferences remember;
	@SuppressWarnings("unused")
	private String KEY = "rememberornot";
	@Override
	public void onReceive(final Context context, final Intent intent) {
		
		final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() || mobile.isAvailable()) {
        	//CallManager callManager=CallManager.getInstance();
//        	remember =context. getSharedPreferences(KEY, 0);
//        	String strUName = remember.getString("username", "");
//    		String strPassword = remember.getString("password", "");
//        	callManager.registerSipClient(strUName,
//        			strPassword, context.getApplicationContext());
        }
		
	}

}

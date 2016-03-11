package com.mallapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.net.InetAddress;

/**
 * Created by Sharjeel on 2/3/2016.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive( Context context, Intent intent )
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

        // if network is NOT available networkInfo will be null
        // otherwise check if we are connected

        if ( activeNetInfo != null && activeNetInfo.isConnected() )
        {

        }
        else {
            Toast.makeText(context, "Internet Connection Lost", Toast.LENGTH_SHORT).show();
        }

    }
}

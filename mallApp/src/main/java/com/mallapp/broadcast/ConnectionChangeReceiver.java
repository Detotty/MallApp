package com.mallapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

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
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        // if network is NOT available networkInfo will be null
        // otherwise check if we are connected

        if ( activeNetInfo != null && activeNetInfo.isConnected() )
        {
//            Toast.makeText(context, "Connec", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Internet Connection Lost", Toast.LENGTH_SHORT).show();
        }
        /*if( mobNetInfo != null )
        {
            Toast.makeText( context, "Mobile Network Type : " + mobNetInfo.getTypeName(), Toast.LENGTH_SHORT ).show();
        }*/
    }
}

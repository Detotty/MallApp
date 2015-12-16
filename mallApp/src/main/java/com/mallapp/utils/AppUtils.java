package com.mallapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.View.R;
import com.mallapp.cache.CentersCacheManager;

/**
 * Created by Sharjeel on 12/15/2015.
 */
public class AppUtils {

    public static String GetSelectedMallPlaceId(Context context){
        String MallPlacecId = null;
        for (FavouriteCentersModel fav: CentersCacheManager.getAllCenters(context)
                ) {
            if (fav.getName().equals(MainMenuConstants.SELECTED_CENTER_NAME)){
                MallPlacecId = fav.getMallPlaceId();
                return MallPlacecId;
            }
        }
        return MallPlacecId;
    }

    public static void displayCallDialog(final Context context, final String num) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        // set title
        alertDialogBuilder.setTitle(R.string.confirmation);
        // set dialog message
        alertDialogBuilder
                .setMessage(R.string.call_alert)
                .setCancelable(true)
                .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + Uri.encode(num)));
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(callIntent);
                    }
                })
                .setNegativeButton(R.string.no_, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}

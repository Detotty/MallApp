package com.mallapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Model.FavoritesModel;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.UserLocationModel;
import com.mallapp.View.R;
import com.mallapp.cache.CentersCacheManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static void CityLatLong(Context context, String city){
        if(Geocoder.isPresent()){
            try {
                String location = city;
                Geocoder gc = new Geocoder(context);
                List<Address> addresses= gc.getFromLocationName(location, 1); // get the found Address Objects
                UserLocationModel model = new UserLocationModel();
                model.setCountryName(addresses.get(0).getCountryName());
                model.setLatitude(addresses.get(0).getLatitude());
                model.setLongitude(addresses.get(0).getLongitude());
                model.setCountryCode(addresses.get(0).getCountryCode());
                model.setCityName(addresses.get(0).getLocality());

                SharedInstance.getInstance().getSharedHashMap().put(AppConstants.USER_LOCATION, model);
                /*List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                    }
                }*/
            } catch (IOException e) {
                // handle the exception
            }
        }
    }

    public static MallActivitiesModel CastToMallActivities(FavoritesModel model){
        MallActivitiesModel mallActivitiesModel = new MallActivitiesModel();
        mallActivitiesModel.setEntityType(model.getEntityType());
        mallActivitiesModel.setActivityId(model.getEntityId());
        mallActivitiesModel.setActivityTextTitle(model.getActivityTextTitle());
        mallActivitiesModel.setBriefText(model.getBriefText());

        return mallActivitiesModel;
    }
}

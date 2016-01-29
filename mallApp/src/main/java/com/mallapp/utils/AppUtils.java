package com.mallapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.util.*;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Model.FavoritesModel;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.RestaurantModel;
import com.mallapp.Model.ShopDetailModel;
import com.mallapp.Model.ShopsModel;
import com.mallapp.Model.UserLocationModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.R;
import com.mallapp.cache.CentersCacheManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Sharjeel on 12/15/2015.
 */
public class AppUtils {

    public static final int DEFAULT_MAP_ZOOM_LEVEL = 12;
    static MaterialDialog mMaterialDialog;

    public static String GetSelectedMallPlaceId(Context context){
        String MallPlacecId = null;
        try {
            for (FavouriteCentersModel fav: CentersCacheManager.readSelectedObjectList(context)
                    ) {
                if (fav.getName().equals(MainMenuConstants.SELECTED_CENTER_NAME)){
                    MallPlacecId = fav.getMallPlaceId();
                    return MallPlacecId;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MallPlacecId;
    }

    public static String MallIdSelection(Context context, int position){
        ArrayList<FavouriteCentersModel> TITLES_Centers = GlobelOffersNews.TITLES_centers;
        if (TITLES_Centers == null || TITLES_Centers.size() == 0) {
            try {
                TITLES_Centers = CentersCacheManager.readSelectedObjectList(context);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            FavouriteCentersModel centers = TITLES_Centers.get(position-1);
            return centers.getMallPlaceId();
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

    public static void sendEmail(Context context, String email){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "Mall App");
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
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

//                SharedInstance.getInstance().getSharedHashMap().put(AppConstants.USER_LOCATION, model);
                DataHandler.updatePreferences(AppConstants.USER_LOCATION, model);
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
        mallActivitiesModel.setEntityName(model.getEntityName());
        mallActivitiesModel.setEntityType(model.getEntityType());
        mallActivitiesModel.setActivityName(model.getEntityType());
        mallActivitiesModel.setActivityTextTitle(model.getActivityTextTitle());
        mallActivitiesModel.setActivityId(model.getEntityId());
        mallActivitiesModel.setBriefText(model.getBriefText());
        mallActivitiesModel.setDetailText(model.getAboutText());
        mallActivitiesModel.setEntityLogo(model.getLogoURL());
        mallActivitiesModel.setImageURL(model.getLogoURL());
        mallActivitiesModel.setEntityLogoSquare(model.getLogoSquareURL());
        mallActivitiesModel.setStartDate(model.getStartDate());
        mallActivitiesModel.setEndDate(model.getEndDate());
        mallActivitiesModel.setFav(true);

        return mallActivitiesModel;
    }

    public static ShopsModel CastToShopModel(FavoritesModel model){
        ShopsModel shopDetailModel = new ShopsModel();
        shopDetailModel.setMallStoreId(model.getEntityId());
        shopDetailModel.setStoreName(model.getEntityName());
        shopDetailModel.setBriefText(model.getBriefText());
        shopDetailModel.setLogoURL(model.getLogoURL());
        shopDetailModel.setCat(model.getCategoryName());
        shopDetailModel.setFloor(model.getFloor());
        shopDetailModel.setFav(true);

        return shopDetailModel;
    }

    public static RestaurantModel CastToRestaurantModel(FavoritesModel model){
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setMallResturantId(model.getEntityId());
        restaurantModel.setRestaurantName(model.getEntityName());
        restaurantModel.setBriefText(model.getBriefText());
        restaurantModel.setLogoURL(model.getLogoURL());
        restaurantModel.setCat(model.getCategoryName());
        restaurantModel.setFloor(model.getFloor());
        restaurantModel.setFav(true);


        return restaurantModel;
    }

    public static void drawMarkerandZoom(double latitude, double longitude, String tagName,GoogleMap map) {

        android.util.Log.e("In zoom", "In Xoom");
        android.util.Log.e("Latt", "lati :" + latitude);
        android.util.Log.e("Longg", "longg :" + longitude);
        android.util.Log.e("TagName", "tagName :" + tagName);
        LatLng thisPosition;
        thisPosition = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(thisPosition).title(tagName));
        map.clear();
        map.addMarker(new MarkerOptions().position(thisPosition).title(tagName));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(thisPosition).zoom(DEFAULT_MAP_ZOOM_LEVEL).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public static void matDialog(Context con,String title, String msg){
                mMaterialDialog = new MaterialDialog(con)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });

        mMaterialDialog.show();
    }
}

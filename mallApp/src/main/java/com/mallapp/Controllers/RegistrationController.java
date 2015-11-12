package com.mallapp.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.List.Adapter.FavouriteCenterAdapter;
import com.List.Adapter.InterestAdapter;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mallapp.Application.MallApplication;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Model.FacebookProfileModel;
import com.mallapp.Model.FavouriteCenters;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.InterestSelectionModel;
import com.mallapp.Model.ThirdPartyRegistrationModel;
import com.mallapp.Model.UserProfile;
import com.mallapp.Model.VolleyErrorHelper;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.DashboardTabFragmentActivity;
import com.mallapp.View.R;
import com.mallapp.View.RegistrationProfileActivity;
import com.mallapp.View.Select_Interest;
import com.mallapp.cache.CentersCacheManager;
import com.mallapp.cache.InterestCacheManager;
import com.mallapp.globel.GlobelServices;
import com.mallapp.imagecapture.Image_Scaling;
import com.mallapp.listeners.NearbyListener;
import com.mallapp.listeners.RegistrationUserListener;
import com.mallapp.utils.SharedInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sharjeel on 11/5/2015.
 */
public class RegistrationController {

    private Context context;
    private RegistrationUserListener listener;


    private String TAG = RegistrationController.class.getSimpleName();

    // Tag used to cancel the request
    private String tag_json_obj = "json_obj_req";
    private ProgressDialog progressDialog;

    public RegistrationController(Context context){
        this.context = context;
    }


    public void  updateUserProfile( final UserProfile user_profile , RegistrationUserListener registrationUserListener) {

        progressDialog =  ProgressDialog.show(context,"",context.getResources().getString(R.string.updating_profile_data_message) );

        this.listener  = registrationUserListener;


        //JSONObject jsonObj = null ;
        try {

            if(user_profile.getImageBitmap()!= null) {
                user_profile.setImageBase64String(Image_Scaling.encodeTobase64(user_profile.getImageBitmap()));
                user_profile.setImageBitmap(null);
            }

            String userId = SharedPreferenceUserProfile.getUserId(context);

            user_profile.setUserId(userId);

            Gson gson = new Gson();

            final String jsonString = gson.toJson( user_profile );
            //System.out.println(jsonString);
            final JSONObject jsonObject = new JSONObject( jsonString );

            android.util.Log.d("", "json send usper profile ...." + jsonObject.toString() );

            String url		= GlobelServices.PROFILE_UPDATE_URL_KEY;

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        if( progressDialog!= null )
                            progressDialog.dismiss();

                        boolean success = jsonObject.getBoolean("Success");
                        Log.e(" update ", "Update user response: " + jsonObject.toString());

                        if (success) {

                            //Log.e(" update ", "JSONObject  update user = " + jsonObject.toString());
//
//                            Gson gson = new Gson();
//                            String jsonString = jsonObject.getJSONObject("Profile").toString();
//
//                            UserProfile userProfile = gson.fromJson(jsonString, UserProfile.class);

                            listener.onDataReceived(user_profile);

                        } else if (jsonObject.has("Message")){

                            String message = jsonObject.getString("Message");

                            if(message!=null && message!= "")
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if(progressDialog!=null)
                        progressDialog.dismiss();

                    String message = VolleyErrorHelper.getMessage(volleyError,context);



                    Log.e("", " error message ..." + message);

                    if(message!=null && message!= "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.request_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }
                }
            })
            {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    String token = SharedPreferenceUserProfile.getUserToken(context);
                    Log.e("", " token:" + token);
                    //headers.put("Content-Type", "application/json");
                    headers.put("Auth-Token", token);

                    return headers;
                }
            };

            MallApplication.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void registerWithFacebook(final FacebookProfileModel facebookProfileModel){

        progressDialog =  ProgressDialog.show(context,"",context.getResources().getString(R.string.registration_with_fb_dialog_message));

        try{

            ThirdPartyRegistrationModel thirdPartyRegistrationModel = new ThirdPartyRegistrationModel();
            thirdPartyRegistrationModel.setAccount("facebook");
            thirdPartyRegistrationModel.setId(facebookProfileModel.getId());

            String[] name = facebookProfileModel.getName().split(" ");

            String firstName = name[0];

            String lastName = "";

            for(int i=1;i< name.length;i++){
                lastName += name[1];
            }

            thirdPartyRegistrationModel.setFirstName(firstName);

            if(lastName!= "")
                thirdPartyRegistrationModel.setLastName(lastName);

            String parameters = "" ;

            parameters += "account="+thirdPartyRegistrationModel.getAccount()
                    + "&id="+ thirdPartyRegistrationModel.getId()
                    + "&firstName="+ thirdPartyRegistrationModel.getFirstName()
                    + "&lastName="+ thirdPartyRegistrationModel.getLastName();

            Gson gson = new Gson();

            JSONObject jsonObject = new JSONObject( gson.toJson(thirdPartyRegistrationModel) );

            Log.d(TAG, jsonObject.toString());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, GlobelServices.FB_REG_URL_KEY+thirdPartyRegistrationModel.getId(),
                    null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonObj) {
                    if(progressDialog!=null)
                        progressDialog.dismiss();

                    Log.d(TAG, jsonObj.toString());

                    try {
                        boolean success = jsonObj.getBoolean("Success");



                             /** {"Success":true,"User_Id":"3def09ea-9850-46dc-9831-a8d6b1f44b00",
                             * "Auth_Token":"d1bbadcb-1118-4c4a-84e4-38221f7dc37b",
                             * "Message":"","httpStatusCode":200}*/


                        if(success) {
                            String profileID = jsonObj.getString("User_Id");
                            String security_token = jsonObj.getString("Auth_Token");


                            SharedPreferenceUserProfile.saveUserToken(security_token, profileID, context);

//                            downloadCategoryList(true);

                            //Toast.makeText(getApplicationContext(), "Registration Successfully!", Toast.LENGTH_LONG).show();


                            Intent profileScreen = new Intent(context, RegistrationProfileActivity.class);
                            context.startActivity(profileScreen);

                        }else{

                            if( jsonObj.has("Message")) {
                                String message = jsonObj.getString("Message");
                                if(message != "")
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                    catch(JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if(progressDialog!=null)
                        progressDialog.dismiss();


                    String message = VolleyErrorHelper.getMessage(volleyError, context);



                    Log.e("", " error message ..." + message);

                    if(message!=null && message!= "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.request_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }
                }

            }
            );



            // Adding request to request queue
            MallApplication.getInstance().addToRequestQueue(request, tag_json_obj);



        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void GetMallList(String url, final FavouriteCenterAdapter adapter, final ArrayList<FavouriteCentersModel> favouriteCentersArrayList, final NearbyListener nearbyListener){
        progressDialog =  ProgressDialog.show(context,"",context.getResources().getString(R.string.loading_data_message));
//        final ArrayList<FavouriteCentersModel> favouriteCentersArrayList = new ArrayList<FavouriteCentersModel>();
        try{


            JsonArrayRequest request = new JsonArrayRequest( url, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArr) {
                    if(progressDialog!=null)
                        progressDialog.dismiss();

                    Log.d(TAG, jsonArr.toString());

                    for (int i = 0; i < jsonArr.length(); i++) {
                        try {

                            JSONObject obj = jsonArr.getJSONObject(i);
                            FavouriteCentersModel fav = new Gson().fromJson(String.valueOf(obj), FavouriteCentersModel.class);

                            // adding movie to movies array
                            favouriteCentersArrayList.add(fav);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    nearbyListener.onMallDataReceived(favouriteCentersArrayList);
                    CentersCacheManager.saveFavorites(context, favouriteCentersArrayList);
//                    adapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if(progressDialog!=null)
                        progressDialog.dismiss();


                    String message = VolleyErrorHelper.getMessage(volleyError, context);



                    Log.e("", " error message ..." + message);

                    if(message!=null && message!= "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.request_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }
                }

            }
            )
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    String token = SharedPreferenceUserProfile.getUserToken(context);
                    Log.e("", " token:" + token);
                    //headers.put("Content-Type", "application/json");
                    headers.put("Auth-Token", token);

                    return headers;
                }
            };

            // Adding request to request queue
            MallApplication.getInstance().addToRequestQueue(request, tag_json_obj);



        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<InterestSelectionModel> GetInterestList(String url, final InterestAdapter adapter, final ArrayList<InterestSelectionModel> interestSelectionModels){
        progressDialog =  ProgressDialog.show(context,"",context.getResources().getString(R.string.loading_data_message));
//        final ArrayList<FavouriteCentersModel> favouriteCentersArrayList = new ArrayList<FavouriteCentersModel>();
        try{


            JsonArrayRequest request = new JsonArrayRequest( url, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArr) {
                    if(progressDialog!=null)
                        progressDialog.dismiss();

                    Log.d(TAG, jsonArr.toString());

                    for (int i = 0; i < jsonArr.length(); i++) {
                        try {

                            JSONObject obj = jsonArr.getJSONObject(i);
                            InterestSelectionModel fav = new Gson().fromJson(String.valueOf(obj), InterestSelectionModel.class);

                            // adding movie to movies array
                            interestSelectionModels.add(fav);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    InterestCacheManager.saveFavorites(context, interestSelectionModels);
                    adapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if(progressDialog!=null)
                        progressDialog.dismiss();


                    String message = VolleyErrorHelper.getMessage(volleyError, context);



                    Log.e("", " error message ..." + message);

                    if(message!=null && message!= "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.request_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }
                }

            }
            )
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    String token = SharedPreferenceUserProfile.getUserToken(context);
                    Log.e("", " token:" + token);
                    //headers.put("Content-Type", "application/json");
                    headers.put("Auth-Token", token);

                    return headers;
                }
            };

            // Adding request to request queue
            MallApplication.getInstance().addToRequestQueue(request, tag_json_obj);



        }catch(Exception e){
            e.printStackTrace();
        }
        return interestSelectionModels;
    }

    public void PostMallInterestSelection(String url, final boolean act){

        progressDialog =  ProgressDialog.show(context,"",context.getResources().getString(R.string.updating_profile_data_message) );

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        boolean success = jsonObject.getBoolean("Success");
                        if (success){
                            if (act){
                                Intent fav= new Intent(context, Select_Interest.class);
                                ((Activity) context).finish();
                                context.startActivity(fav);
                            }
                            else {
                                Intent select_interest= new Intent(context, DashboardTabFragmentActivity.class);
                                ((Activity) context).finish();
                                context.startActivity(select_interest);
                            }
                        }
                        else {
                            if( jsonObject.has("Message")) {
                                String message = jsonObject.getString("Message");
                                if(message != "")
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if(progressDialog!=null)
                        progressDialog.dismiss();

                    String message = VolleyErrorHelper.getMessage(volleyError,context);



                    Log.e("", " error message ..." + message);

                    if(message!=null && message!= "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.request_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    String token = SharedPreferenceUserProfile.getUserToken(context);
                    Log.e("", " token:" + token);
//                    headers.put("Content-Type", "application/json");
                    headers.put("Auth-Token", token);

                    return headers;
                }
            };

            MallApplication.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);

        }


}


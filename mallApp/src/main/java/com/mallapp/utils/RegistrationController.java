package com.mallapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.mallapp.Application.MallApplication;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Fragments.ProfileTabFragment;
import com.mallapp.Model.FacebookProfileModel;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.InterestSelectionModel;
import com.mallapp.Model.ThirdPartyRegistrationModel;
import com.mallapp.Model.UserProfile;
import com.mallapp.Model.UserProfileModel;
import com.mallapp.Model.VolleyErrorHelper;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.DashboardTabFragmentActivity;
import com.mallapp.View.R;
import com.mallapp.View.RegistrationProfileActivity;
import com.mallapp.View.Select_Interest;
import com.mallapp.cache.CentersCacheManager;
import com.mallapp.cache.InterestCacheManager;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.imagecapture.Image_Scaling;
import com.mallapp.listeners.NearbyListener;
import com.mallapp.listeners.RegistrationUserListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sharjeel on 11/5/2015.
 */
public class RegistrationController {

    private Context context;
    private RegistrationUserListener listener;
    ImageView is_interest_select_all;

    private String TAG = RegistrationController.class.getSimpleName();

    // Tag used to cancel the request
    private String tag_json_obj = "json_obj_req";
    private ProgressDialog progressDialog;

    public RegistrationController(Context context) {
        this.context = context;
    }

    //******-------Profile Updation Api Call-----*********

    public void updateUserProfile(final UserProfileModel user_profile, RegistrationUserListener registrationUserListener) {

        progressDialog = ProgressDialog.show(context, "", context.getResources().getString(R.string.updating_profile_data_message));

        this.listener = registrationUserListener;


        //JSONObject jsonObj = null ;
        try {

            /*if(user_profile.getImageBitmap()!= null) {
                user_profile.setImageBase64String(Image_Scaling.encodeTobase64(user_profile.getImageBitmap()));
                user_profile.setImageBitmap(null);
            }*/

            String userId = SharedPreferenceUserProfile.getUserId(context);

            user_profile.setUserId(userId);

            Gson gson = new Gson();

            final String jsonString = gson.toJson(user_profile);
            //System.out.println(jsonString);
            final JSONObject jsonObject = new JSONObject(jsonString);

            android.util.Log.d("", "json send usper profile ...." + jsonObject.toString());

            String url = ApiConstants.PROFILE_UPDATE_URL_KEY;

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        if (progressDialog != null)
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

                            SharedPreferenceUserProfile.SaveUserProfile(user_profile, context);
                            listener.onDataReceived(user_profile);

                        } else if (jsonObject.has("Message")) {

                            String message = jsonObject.getString("Message");

                            if (message != null && message != "")
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (progressDialog != null)
                        progressDialog.dismiss();

                    String message = VolleyErrorHelper.getMessage(volleyError, context);


                    Log.e("", " error message ..." + message);

                    if (message != null && message != "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.request_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }
                }
            }) {

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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //******-------Getting Information of Existing User-----*********

    public void getUserProfile(String url, RegistrationUserListener registrationUserListener) {


        String userId = SharedPreferenceUserProfile.getUserId(context);

        Log.e("Registration Controller:", " url ..." + url);
        this.listener = registrationUserListener;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                Log.d("", "ProfileModel:" + obj.toString());
                if (progressDialog != null)
                    progressDialog.dismiss();

                try {

                    boolean success = obj.getBoolean("Success");

                    if (success) {
                        JSONObject jsonObj = obj.getJSONObject("Profile");
                        Log.e("", " user not exist ..." + obj.toString());
                        UserProfileModel user_profile = null;
                        Gson gson = new Gson();
                        user_profile = gson.fromJson(jsonObj.toString(), UserProfileModel.class);
//                        if (user_profile == null)
//                            user_profile = new UserProfileModel();

                        if (listener != null && user_profile != null) {
                            listener.onDataReceived(user_profile);
                            SharedInstance.getInstance().getSharedHashMap().put(AppConstants.PROFILE_DATA, user_profile);
                            DataHandler.updatePreferences(AppConstants.PROFILE_DATA, user_profile);
                        }
//                            else
//                        ((Code_Verifiction)context).startProfileActivity();


				/*
                 * {"httpStatusCode":200,"Success":true,
				 * "Profile":{"Street":null,"Phone":"923361466623",
				 * "Password":null,"LastUpdateUnixDatetime":1300124700,
				 * "CurrentLoctionName":null,"DeviceType":"android","LastName":"Attiq",
				 * "Dob":"2010-03-05T19:00:00Z","LastLoginUTCDateTime":null,
				 * "Zipcode":null,"IsActive":true,"Image":null,"LastLoginUnixDateTime":0,
				 * "FirstName":"Attiq","MobilePhone":"923361466623","LastUpdateUTCDateTime":"2011-03-14T17:45:00",
				 * "ImageBase64String":null,"DefaultLocationName":"Lahore, Punjab, Pakistan",
				 * "CreatedUnixDatetime":1.438680902327E9,"UserId":"3def09ea-9850-46dc-9831-a8d6b1f44b00",
				 * "LanguageId":null,"DefaultLocationLatLong":"0.00.0","Country":"Pakistan","Title":"Mr.",
				 * "City":null,"Summary":null,"UnixDob":1267815600,"CurrentLocationLatLong":null,"Email":"atr@quantumcph.com",
				 * "PersonIdentificationNumber":null,"FileName":"http:\/\/52.28.59.218:9010\/\/profiles\/images\/3def09ea-9850-46dc-9831-a8d6b1f44b00.png",
				 * "Username":null,"Gender":"Female","CreatedUTCDateTime":"2015-08-04T09:35:02.327","IsDeleted":false},"Message":""}
				 */
                    } else if (obj.has("Message")) {

                        String message = obj.getString("Message");

                        if (message != null && message != "")
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("", " user not exist ..." + volleyError.toString());

                if (progressDialog != null)
                    progressDialog.dismiss();

                String message = VolleyErrorHelper.getMessage(volleyError, context);


                Log.e("", " error message ..." + message);

                if (message != null && message != "")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                else {
                    String serverError = context.getResources().getString(R.string.request_error_message);
                    Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                }


            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<String, String>();
                String token = SharedPreferenceUserProfile.getUserToken(context);
                Log.e("", " token" + token);
                headers.put("Content-Type", "application/json");
                headers.put("Auth-Token", token);

                return headers;

            }

        };

        MallApplication.getInstance().addToRequestQueue(jsonRequest, tag_json_obj);

    }

    //******-------Registration with Facbook Api Call-----*********

    public void registerWithFacebook(final FacebookProfileModel facebookProfileModel) {

        progressDialog = ProgressDialog.show(context, "", context.getResources().getString(R.string.registration_with_fb_dialog_message));

        try {

            ThirdPartyRegistrationModel thirdPartyRegistrationModel = new ThirdPartyRegistrationModel();
            thirdPartyRegistrationModel.setAccount("facebook");
            thirdPartyRegistrationModel.setId(facebookProfileModel.getId());

            String[] name = facebookProfileModel.getName().split(" ");

            String firstName = name[0];

            String lastName = "";

            for (int i = 1; i < name.length; i++) {
                lastName += name[1];
            }

            thirdPartyRegistrationModel.setFirstName(firstName);

            if (lastName != "")
                thirdPartyRegistrationModel.setLastName(lastName);

            String parameters = "";

            parameters += "account=" + thirdPartyRegistrationModel.getAccount()
                    + "&id=" + thirdPartyRegistrationModel.getId()
                    + "&firstName=" + thirdPartyRegistrationModel.getFirstName()
                    + "&lastName=" + thirdPartyRegistrationModel.getLastName();

            Gson gson = new Gson();

            JSONObject jsonObject = new JSONObject(gson.toJson(thirdPartyRegistrationModel));

            Log.d(TAG, jsonObject.toString());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ApiConstants.FB_REG_URL_KEY + thirdPartyRegistrationModel.getId(),
                    null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonObj) {
                    if (progressDialog != null)
                        progressDialog.dismiss();

                    Log.d(TAG, jsonObj.toString());

                    try {
                        boolean success = jsonObj.getBoolean("Success");


                        /** {"Success":true,"User_Id":"3def09ea-9850-46dc-9831-a8d6b1f44b00",
                         * "Auth_Token":"d1bbadcb-1118-4c4a-84e4-38221f7dc37b",
                         * "Message":"","httpStatusCode":200}*/


                        if (success) {
                            String profileID = jsonObj.getString("User_Id");
                            String security_token = jsonObj.getString("Auth_Token");


                            SharedPreferenceUserProfile.saveUserToken(security_token, profileID, context);

//                            downloadCategoryList(true);

                            //Toast.makeText(getApplicationContext(), "Registration Successfully!", Toast.LENGTH_LONG).show();


                            Intent profileScreen = new Intent(context, RegistrationProfileActivity.class);
                            context.startActivity(profileScreen);

                        } else {

                            if (jsonObj.has("Message")) {
                                String message = jsonObj.getString("Message");
                                if (message != "")
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

                    if (progressDialog != null)
                        progressDialog.dismiss();


                    String message = VolleyErrorHelper.getMessage(volleyError, context);


                    Log.e("", " error message ..." + message);

                    if (message != null && message != "")
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


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //******-------Get Mall List Api Call-----*********

    public void GetMallList(String url, final FavouriteCenterAdapter adapter, final ArrayList<FavouriteCentersModel> favouriteCentersArrayList, final NearbyListener nearbyListener, final ListView listView) {
        progressDialog = ProgressDialog.show(context, "", context.getResources().getString(R.string.loading_data_message));
//        final ArrayList<FavouriteCentersModel> favouriteCentersArrayList = new ArrayList<FavouriteCentersModel>();
        try {

            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArr) {
                    if (progressDialog != null)
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
                    String urls = ApiConstants.GET_USER_MALL_URL_KEY + SharedPreferenceUserProfile.getUserId(context);
                    GetSubscribedMallList(urls, adapter, listView, favouriteCentersArrayList);
//                    adapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if (progressDialog != null)
                        progressDialog.dismiss();

                    String message = VolleyErrorHelper.getMessage(volleyError, context);

                    Log.e("", " error message ..." + message);

                    if (message != null && message != "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.request_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }
                }

            }
            ) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetSubscribedMallList(String url, final FavouriteCenterAdapter adapter, final ListView listView, final ArrayList<FavouriteCentersModel> favouriteCentersArrayList) {
        progressDialog = ProgressDialog.show(context, "", context.getResources().getString(R.string.loading_data_message));
//        final ArrayList<FavouriteCentersModel> favouriteCentersArrayList = new ArrayList<FavouriteCentersModel>();
        try {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArr) {
                    if (progressDialog != null)
                        progressDialog.dismiss();

                    Log.d(TAG, jsonArr.toString());

                    for (int i = 0; i < jsonArr.length(); i++) {
                        try {
                            JSONObject obj = jsonArr.getJSONObject(i);
                            FavouriteCentersModel fav = new Gson().fromJson(String.valueOf(obj), FavouriteCentersModel.class);
                            for (FavouriteCentersModel f : favouriteCentersArrayList) {
                                if (f.getMallPlaceId().equals(fav.getMallPlaceId())) {
                                    fav.setIsfav(true);
                                    favouriteCentersArrayList.set(favouriteCentersArrayList.indexOf(f), fav);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if (progressDialog != null)
                        progressDialog.dismiss();

                    String message = VolleyErrorHelper.getMessage(volleyError, context);
                    Log.e("", " error message ..." + message);

                    if (message != null && message != "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.request_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            ) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetSelectedInterestList(String url, final InterestAdapter adapter, final ArrayList<InterestSelectionModel> interestSelectionModels) {
        progressDialog = ProgressDialog.show(context, "", context.getResources().getString(R.string.loading_data_message));
//        final ArrayList<FavouriteCentersModel> favouriteCentersArrayList = new ArrayList<FavouriteCentersModel>();
        try {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArr) {
                    if (progressDialog != null)
                        progressDialog.dismiss();

                    Log.d(TAG, jsonArr.toString());

                    for (int i = 0; i < jsonArr.length(); i++) {
                        try {
                            JSONObject obj = jsonArr.getJSONObject(i);
                            InterestSelectionModel fav = new Gson().fromJson(String.valueOf(obj), InterestSelectionModel.class);
                            for (InterestSelectionModel f : interestSelectionModels) {
                                if (f.getCategoryId().equals(fav.getCategoryId())) {
                                    fav.setInterested(true);
                                    interestSelectionModels.set(interestSelectionModels.indexOf(f), fav);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                    if (areAllTrue(interestSelectionModels)) {
                        is_interest_select_all.setImageResource(R.drawable.interest_p);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if (progressDialog != null)
                        progressDialog.dismiss();


                    String message = VolleyErrorHelper.getMessage(volleyError, context);


                    Log.e("", " error message ..." + message);

                    if (message != null && message != "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.request_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }
                }

            }
            ) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //******-------Get Interest List Api Call-----*********

    public ArrayList<InterestSelectionModel> GetInterestList(String url, final InterestAdapter adapter, final ArrayList<InterestSelectionModel> interestSelectionModels, ImageView is_interest_select_all) {
        progressDialog = ProgressDialog.show(context, "", context.getResources().getString(R.string.loading_data_message));
        this.is_interest_select_all = is_interest_select_all;

//        final ArrayList<FavouriteCentersModel> favouriteCentersArrayList = new ArrayList<FavouriteCentersModel>();
        try {


            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArr) {
                    if (progressDialog != null)
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
                    String urls = ApiConstants.GET_SELECTED_INTEREST_URL_KEY + SharedPreferenceUserProfile.getUserId(context) + "&LanguageId=1";
                    GetSelectedInterestList(urls, adapter, interestSelectionModels);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if (progressDialog != null)
                        progressDialog.dismiss();


                    String message = VolleyErrorHelper.getMessage(volleyError, context);


                    Log.e("", " error message ..." + message);

                    if (message != null && message != "")
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.request_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }
                }

            }
            ) {
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


        } catch (Exception e) {
            e.printStackTrace();
        }
        return interestSelectionModels;
    }

    //******-------Saving User Mall and Interest Selection through Api Call-----*********

    public void PostMallInterestSelection(String url, final boolean act) {

        progressDialog = ProgressDialog.show(context, "", context.getResources().getString(R.string.loading_data_message));

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                try {
                    boolean success = jsonObject.getBoolean("Success");
                    if (success) {
                        if (act) {
                            try{
                                if (!ProfileTabFragment.isUpdate){
                                    Intent fav = new Intent(context, Select_Interest.class);
                                    ((Activity) context).finish();
                                    context.startActivity(fav);
                                }
                            }catch (Exception e){

                            }

                        } else {
                            if (!ProfileTabFragment.isUpdate){
                                Intent select_interest = new Intent(context, DashboardTabFragmentActivity.class);
                                ((Activity) context).finish();
                                context.startActivity(select_interest);
                            }

                        }
                    } else {
                        if (jsonObject.has("Message")) {
                            String message = jsonObject.getString("Message");
                            if (message != "")
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
                if (progressDialog != null)
                    progressDialog.dismiss();

                String message = VolleyErrorHelper.getMessage(volleyError, context);
                Log.e("", " error message ..." + message);

                if (message != null && message != "")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                else {
                    String serverError = context.getResources().getString(R.string.request_error_message);
                    Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                }
            }
        }) {
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


    public static boolean areAllTrue(ArrayList<InterestSelectionModel> array) {
        for (InterestSelectionModel b : array) if (!b.isInterested()) return false;
        return true;
    }
}


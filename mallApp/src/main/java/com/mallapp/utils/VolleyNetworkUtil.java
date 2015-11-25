package com.mallapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.*;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mallapp.Application.MallApplication;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.ShopDetailModel;
import com.mallapp.Model.ShopsModel;
import com.mallapp.Model.VolleyErrorHelper;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.R;
import com.mallapp.listeners.MallDataListener;
import com.mallapp.listeners.ShopsDataListener;
import com.mallapp.listeners.VolleyDataReceivedListener;
import com.mallapp.listeners.VolleyErrorListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sharjeel Haider on 11/16/2015.
 */
public class VolleyNetworkUtil implements VolleyErrorListener, VolleyDataReceivedListener, Response.Listener<JSONObject> {

    private Context context;
    private ProgressDialog progressDialog;
    private String TAG = VolleyNetworkUtil.class.getSimpleName();
    ShopsDataListener shopsDataListener;
    private String requestType;

    private final String GET_SUBSCRIBED_MALLS = "GET_SUBSCRIBED_MALLS";
    private final String GET_SHOP_DETAIL = "GET_SHOP_DETAIL";
    private final String IMAGE_UPLOADING = "IMAGE_UPLOADING";
    private final String BOOKMARK_ENDORSEMENT = "BOOKMARK_ENDORSEMENT";
    private final String FAVORITE_CATEGORY = "FAVORITE_CATEGORY";

    public VolleyNetworkUtil(Context context) {
        this.context = context;
    }


    /*<---------------MALLS ACTVITY DATA ---------------->*/
    public ArrayList<String> GetMallsActivities(String url, final MallDataListener mallDataListener) {
        final ArrayList<MallActivitiesModel> mallActivitiesModels = new ArrayList<>();
        try {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArr) {
                    android.util.Log.d(TAG, jsonArr.toString());

                    for (int i = 0; i < jsonArr.length(); i++) {
                        try {
                            JSONObject obj = jsonArr.getJSONObject(i);
                            MallActivitiesModel fav = new Gson().fromJson(String.valueOf(obj), MallActivitiesModel.class);
                            mallActivitiesModels.add(fav);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mallDataListener.onDataReceived(mallActivitiesModels);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if (progressDialog != null)
                        progressDialog.dismiss();

                    mallDataListener.OnError();
                    String message = VolleyErrorHelper.getMessage(volleyError, context);
                    android.util.Log.e("", " error message ..." + message);

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
            MallApplication.getInstance().addToRequestQueue(request, url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*<---------------SHOPS DATA ---------------->*/

    public ArrayList<String> GetShops(String url, final ShopsDataListener shopsDataListener) {
        final ArrayList<ShopsModel> shopsModels = new ArrayList<>();
        try {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArr) {
                    android.util.Log.d(TAG, jsonArr.toString());

                    for (int i = 0; i < jsonArr.length(); i++) {
                        try {
                            JSONObject obj = jsonArr.getJSONObject(i);
                            ShopsModel fav = new Gson().fromJson(String.valueOf(obj), ShopsModel.class);
                            shopsModels.add(fav);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    shopsDataListener.onDataReceived(shopsModels);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if (progressDialog != null)
                        progressDialog.dismiss();

                    shopsDataListener.OnError();
                    String message = VolleyErrorHelper.getMessage(volleyError, context);
                    android.util.Log.e("", " error message ..." + message);

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
            MallApplication.getInstance().addToRequestQueue(request, url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*<--------------SHOPS DETAILS DATA ---------------->*/

    public void GetShopDetail(String url,final ShopsDataListener shopsDataListener) {
        this.shopsDataListener = shopsDataListener;
        requestType = GET_SHOP_DETAIL;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, this, this) {
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
        MallApplication.getInstance().addToRequestQueue(request, url);
    }


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.d(TAG, volleyError.toString());

        if (progressDialog != null)
            progressDialog.dismiss();

//        listener.OnError();

        String message = VolleyErrorHelper.getMessage(volleyError, context);

        switch (requestType) {
            case BOOKMARK_ENDORSEMENT: {
//                listener.OnDataReceived(null,0,false);
            }
        }


        Log.e("", " error message ..." + message);

        if (message != null && message != "")
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        else {
            String serverError = context.getResources().getString(R.string.request_error_message);
            Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d(TAG, response.toString());
        if (progressDialog != null)
            progressDialog.dismiss();

        switch (requestType) {

            case GET_SHOP_DETAIL: {
                try {
                    Gson gson = new Gson();
                    ShopDetailModel model = gson.fromJson(String.valueOf(response), ShopDetailModel.class);
                    Log.d(TAG, "Shop Detail:" + String.valueOf(response));
                        if (model != null)
                            shopsDataListener.onShopDetailReceived(model);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }


        }
    }
}

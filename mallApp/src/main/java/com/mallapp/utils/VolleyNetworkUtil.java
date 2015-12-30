package com.mallapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.*;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.mallapp.Application.MallApplication;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.FavoritesModel;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.MallDetailModel;
import com.mallapp.Model.Restaurant;
import com.mallapp.Model.RestaurantDetailModel;
import com.mallapp.Model.RestaurantModel;
import com.mallapp.Model.ServicesModel;
import com.mallapp.Model.ShopDetailModel;
import com.mallapp.Model.ShopsModel;
import com.mallapp.Model.VolleyErrorHelper;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.R;
import com.mallapp.db.DatabaseHelper;
import com.mallapp.listeners.FavoritesDataListener;
import com.mallapp.listeners.MallDataListener;
import com.mallapp.listeners.RestaurantDataListener;
import com.mallapp.listeners.ServicesDataListener;
import com.mallapp.listeners.ShopsDataListener;
import com.mallapp.listeners.VolleyDataReceivedListener;
import com.mallapp.listeners.VolleyErrorListener;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sharjeel Haider on 11/16/2015.
 */
public class VolleyNetworkUtil implements VolleyErrorListener, VolleyDataReceivedListener, Response.Listener<JSONObject> {

    //region Data Members
    private Context context;
    private ProgressDialog progressDialog;
    private String TAG = VolleyNetworkUtil.class.getSimpleName();
    MallDataListener mallDataListener;
    ShopsDataListener shopsDataListener;
    RestaurantDataListener restaurantDataListener;
    private String requestType;

    private final String POST_FAV_NnO = "POST_FAV_NnO";
    private final String POST_FAV_SHOP = "POST_FAV_SHOP";
    private final String POST_FAV_REST = "POST_FAV_REST";
    private final String GET_SHOP_DETAIL = "GET_SHOP_DETAIL";
    private final String GET_REST_DETAIL = "GET_REST_DETAIL";
    private final String GET_MALL_DETAIL = "GET_MALL_DETAIL";
    private final String GET_MALL_NEWSnOFFERS = "GET_MALL_NEWSnOFFERS";
    private final String GET_MALL_SERVICES = "GET_MALL_SERVICES";
    private final String FAVORITE_CATEGORY = "FAVORITE_CATEGORY";
    //endregion

    public VolleyNetworkUtil(Context context) {
        this.context = context;
    }

    /*<--------------GET MALL NEWS AND OFFER ---------------->*/
    public void GetMallNewsnOffers(String url,final MallDataListener mallDataListener) {
//        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = GET_MALL_NEWSnOFFERS;
        this.mallDataListener = mallDataListener;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String token = SharedPreferenceUserProfile.getUserToken(context);
                Log.e("Token", token);
//                headers.put("Content-Type", "application/json");
                headers.put("Auth-Token", token);
                return headers;
            }
        };
        MallApplication.getInstance().addToRequestQueue(request, url);
    }

    /*<---------------GET MALL SHOPS ---------------->*/

    public ArrayList<String> GetShops(String url, final ShopsDataListener shopsDataListener) {
        progressDialog = ProgressDialog.show(context,"","Loading");
        final ArrayList<ShopsModel> shopsModels = new ArrayList<>();
        try {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArr) {
                    android.util.Log.d(TAG, jsonArr.toString());
                    if (progressDialog != null)
                        progressDialog.dismiss();
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
                        Toast.makeText(context, "No shops found for this mall", Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.shop_error_message);
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

    /*<---------------GET MALL SERVICES ---------------->*/

    public ArrayList<String> GetServices(String url, final ServicesDataListener servicesDataListener) {
        progressDialog = ProgressDialog.show(context,"","Loading");
        try {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArr) {
                    android.util.Log.d(TAG, jsonArr.toString());
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Gson gson = new Gson();
                    String data = jsonArr.toString();
                    Type listType = new TypeToken<List<ServicesModel>>() {
                    }.getType();

                    ArrayList<ServicesModel> model = gson.fromJson(data, listType);
                    servicesDataListener.onDataReceived(model);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if (progressDialog != null)
                        progressDialog.dismiss();

                    servicesDataListener.OnError();
                    String message = VolleyErrorHelper.getMessage(volleyError, context);
                    android.util.Log.e("", " error message ..." + message);

                    if (message != null && message != "")
                        Toast.makeText(context, MainMenuConstants.NO_SERVICES_ERROR, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.shop_error_message);
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

    /*<---------------GET USER FAVORITE ---------------->*/

    public ArrayList<String> GetUserFav(String url, final FavoritesDataListener favoritesDataListener) {
        progressDialog = ProgressDialog.show(context,"","Loading");
        try {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArr) {
                    android.util.Log.d(TAG, jsonArr.toString());
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Gson gson = new Gson();
                    String data = jsonArr.toString();
                    Type listType = new TypeToken<List<FavoritesModel>>() {
                    }.getType();

                    ArrayList<FavoritesModel> model = gson.fromJson(data, listType);
                    favoritesDataListener.onDataReceived(model);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if (progressDialog != null)
                        progressDialog.dismiss();

                    favoritesDataListener.OnError();
                    String message = VolleyErrorHelper.getMessage(volleyError, context);
                    android.util.Log.e("", " error message ..." + message);

                    if (message != null && message != "")
                        Toast.makeText(context, MainMenuConstants.NO_SERVICES_ERROR, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.shop_error_message);
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

    /*<---------------GET MALL RESTAURANTS ---------------->*/

    public ArrayList<String> GetRestaurant(String url, final RestaurantDataListener restaurantDataListener) {
        progressDialog = ProgressDialog.show(context,"","Loading");
        final ArrayList<RestaurantModel> restaurantModels = new ArrayList<>();
        try {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArr) {
                    android.util.Log.d(TAG, jsonArr.toString());
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    for (int i = 0; i < jsonArr.length(); i++) {
                        try {
                            JSONObject obj = jsonArr.getJSONObject(i);
                            RestaurantModel fav = new Gson().fromJson(String.valueOf(obj), RestaurantModel.class);
                            restaurantModels.add(fav);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    restaurantDataListener.onDataReceived(restaurantModels);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if (progressDialog != null)
                        progressDialog.dismiss();

                    restaurantDataListener.OnError();
                    String message = VolleyErrorHelper.getMessage(volleyError, context);
                    android.util.Log.e("", " error message ..." + message);

                    if (message != null && message != "")
                        Toast.makeText(context, "No shops found for this mall", Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.shop_error_message);
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

    /*<--------------GET SHOP DETAILS ---------------->*/

    public void GetShopDetail(String url,final ShopsDataListener shopsDataListener) {
        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = GET_SHOP_DETAIL;
        this.shopsDataListener = shopsDataListener;
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

    /*<--------------GET RESTAURANT DETAIL ---------------->*/

    public void GetRestaurantDetail(String url,final RestaurantDataListener restaurantDataListener) {
        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = GET_REST_DETAIL;
        this.restaurantDataListener = restaurantDataListener;
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

    /*<--------------GET MALL DETAIL ---------------->*/

    public void GetMallDetail(String url,final MallDataListener mallDataListener) {
        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = GET_MALL_DETAIL;
        this.mallDataListener = mallDataListener;
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
    /*<--------------NEWS AND OFFERS FAVORITE SELECTION ---------------->*/

    public void PostFavNnO(String url) {
//        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = POST_FAV_NnO;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, this, this) {
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

    /*<--------------SHOPS FAVORITE SELECTION ---------------->*/
    public void PostFavShop(String url) {
//        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = POST_FAV_SHOP;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, this, this) {
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

    /*<--------------RESTAURANT FAVORITE SELECTION ---------------->*/
    public void PostFavRestaurant(String url) {
//        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = POST_FAV_REST;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, this, this) {
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
        NetworkResponse networkResponse = volleyError.networkResponse;
        if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {}
            // HTTP Status Code: 401 Unauthorized
        if (progressDialog != null)
            progressDialog.dismiss();

//        listener.OnError();

        String message = VolleyErrorHelper.getMessage(volleyError, context);

        switch (requestType) {
            case GET_MALL_NEWSnOFFERS: {
                mallDataListener.OnError();
            }
        }


        Log.e("", " error message ..." + message);

        /*if (message != null && message != "")
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        else {
            String serverError = context.getResources().getString(R.string.request_error_message);
            Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d(TAG, response.toString());
        if (progressDialog != null)
            progressDialog.dismiss();
        //region SWITCH STATEMENT
        switch (requestType) {

            //region GET_SHOP_DETAIL
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
            //endregion

            //region GET_RESTAURANT_DETAIL
            case GET_REST_DETAIL: {
                try {
                    Gson gson = new Gson();
                    RestaurantDetailModel model = gson.fromJson(String.valueOf(response), RestaurantDetailModel.class);
                    Log.d(TAG, "Shop Detail:" + String.valueOf(response));
                        if (model != null)
                            restaurantDataListener.onRestaurantDetailReceived(model);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            //endregion

            //region GET_MALL_DETAIL
            case GET_MALL_DETAIL: {
                try {
                    Gson gson = new Gson();
                    MallDetailModel model = gson.fromJson(String.valueOf(response), MallDetailModel.class);
                    Log.d(TAG, "Mall Detail:" + String.valueOf(response));
                        if (model != null)
                            mallDataListener.onMallDetailReceived(model);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            //endregion

            //region POST_FAV_NEWSnOFFERS
            case POST_FAV_NnO: {
                try {
                    boolean success = response.getBoolean("Success");
                    if (success) {

                    }
                    else{

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            //endregion

            //region POST_FAV_SHOP
            case POST_FAV_SHOP: {
                try {
                    boolean success = response.getBoolean("Success");
                    if (success) {

                    }
                    else{

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            //endregion

            //region POST_FAV_REST
            case POST_FAV_REST: {
                try {
                    boolean success = response.getBoolean("Success");
                    if (success) {

                    }
                    else{

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            //endregion

            //region GET_MALL_NEWSnOFFERS
            case GET_MALL_NEWSnOFFERS: {
                try {
                    Gson gson = new Gson();
                    String data = response.getJSONArray("MallActivities").toString();
                    Type listType = new TypeToken<List<MallActivitiesModel>>() {
                    }.getType();

                    ArrayList<MallActivitiesModel> model = gson.fromJson(data, listType);
                    mallDataListener.onDataReceived(model);
                } catch (Exception e) {
                    mallDataListener.OnError();
                    e.printStackTrace();
                }
                break;
            }
            //endregion

        }
        //endregion
    }
}

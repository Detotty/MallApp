package com.mallapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
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
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.mallapp.Application.MallApplication;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.FavoritesModel;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.FloorOverViewModel;
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
import com.mallapp.listeners.ActivityDetailListener;
import com.mallapp.listeners.FavoritesDataListener;
import com.mallapp.listeners.FloorsDataListener;
import com.mallapp.listeners.MallDataListener;
import com.mallapp.listeners.RestaurantDataListener;
import com.mallapp.listeners.ServicesDataListener;
import com.mallapp.listeners.ShopsDataListener;
import com.mallapp.listeners.UniversalDataListener;
import com.mallapp.listeners.VolleyDataReceivedListener;
import com.mallapp.listeners.VolleyErrorListener;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    ActivityDetailListener activityDetailListener;
    FloorsDataListener floorsDataListener;
    ShopsDataListener shopsDataListener;
    RestaurantDataListener restaurantDataListener;
    UniversalDataListener universalDataListener;
    private String requestType;
    GoogleCloudMessaging gcmObj;

    private final String POST_FAV_NnO = "POST_FAV_NnO";
    private final String POST_FAV_SHOP = "POST_FAV_SHOP";
    private final String POST_FAV_REST = "POST_FAV_REST";
    private final String POST_NOT_SET = "POST_NOT_SET";
    private final String POST_LOYALTY_CARD = "POST_LOYALTY_CARD";
    private final String GET_SHOP_DETAIL = "GET_SHOP_DETAIL";
    private final String GET_REST_DETAIL = "GET_REST_DETAIL";
    private final String GET_MALL_DETAIL = "GET_MALL_DETAIL";
    private final String GET_MALL_FLOORS = "GET_MALL_FLOORS";
    private final String GET_MALL_NEWSnOFFERS = "GET_MALL_NEWSnOFFERS";
    private final String GET_USER_SIGN_OUT = "GET_USER_SIGN_OUT";
    private final String GET_ACTIVITY_DETAIL = "GET_ACTIVITY_DETAIL";
    private final String GET_BARCODE_TYPE = "GET_BARCODE_TYPE";
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

                    /*if (message != null && message != "")
                        Toast.makeText(context, "No shops found for this mall", Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.shop_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }*/
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

                    /*if (message != null && message != "")
                        Toast.makeText(context, MainMenuConstants.NO_SERVICES_ERROR, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.shop_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }*/
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

                    /*if (message != null && message != "")
                        Toast.makeText(context, MainMenuConstants.NO_SERVICES_ERROR, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.shop_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }*/
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

                    /*if (message != null && message != "")
                        Toast.makeText(context, "No shops found for this mall", Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.shop_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }*/
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


    /*<---------------GET MALL FLOORS ---------------->*/

    public ArrayList<String> GetFloorOverview(String url, final FloorsDataListener floorsDataListener) {
        progressDialog = ProgressDialog.show(context,"","Loading");
        final ArrayList<FloorOverViewModel> floorOverViewModels = new ArrayList<>();
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
                            FloorOverViewModel fav = new Gson().fromJson(String.valueOf(obj), FloorOverViewModel.class);
                            floorOverViewModels.add(fav);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    floorsDataListener.onDataReceived(floorOverViewModels);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if (progressDialog != null)
                        progressDialog.dismiss();

                    floorsDataListener.OnError();
                    String message = VolleyErrorHelper.getMessage(volleyError, context);
                    android.util.Log.e("", " error message ..." + message);

                    /*if (message != null && message != "")
                        Toast.makeText(context, "No shops found for this mall", Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.shop_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }*/
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

    /*<--------------NEWS AND OFFERS FAVORITE SELECTION ---------------->*/

    public void PostFavNnO(String url) {
//        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = POST_NOT_SET;
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

    /*<--------------POST NOTIFICATION and DEVICE IDENTITY SETTINGS ---------------->*/

    public void PostNotSet(String url, JSONObject user) {
//        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = POST_NOT_SET;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, user, this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String token = SharedPreferenceUserProfile.getUserToken(context);
                Log.e("", " token" + token);
                headers.put("Auth-Token", token);
                return headers;
            }
        };
        MallApplication.getInstance().addToRequestQueue(request, url);
    }

    /*<--------------POST LOYALTY CARD ---------------->*/

    public void PostLoyaltyCard(String url, JSONObject user, UniversalDataListener universalDataListener) {
        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = POST_LOYALTY_CARD;
        this.universalDataListener = universalDataListener;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, user, this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String token = SharedPreferenceUserProfile.getUserToken(context);
                Log.e("", " token" + token);
                headers.put("Auth-Token", token);
                return headers;
            }
        };
        MallApplication.getInstance().addToRequestQueue(request, url);
    }

    /*<--------------ACTIVITY DETAILS ---------------->*/

    public void GetActivityDetail(String url, ActivityDetailListener activityDetailListener) {
        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = GET_ACTIVITY_DETAIL;
        this.activityDetailListener = activityDetailListener;
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

    /*<--------------USER SIGN OUT ---------------->*/

    public void GetUserSignOut(String url) {
//        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = GET_USER_SIGN_OUT;
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

    /*<--------------NOTIFICATION ACK ---------------->*/

    public void GetNotAck(String url) {
//        progressDialog = ProgressDialog.show(context,"","Loading");
        url = ApiConstants.GET_NOT_ACK_URL_KEY+SharedPreferenceUserProfile.getUserId(context);
        requestType = POST_NOT_SET;
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


    /*<---------------GET BARCODE TYPES ---------------->*/

    public ArrayList<String> GetBarcodeTypes(String url, final UniversalDataListener universalDataListener) {
        progressDialog = ProgressDialog.show(context,"","Loading");
        try {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray jsonArr) {
                    android.util.Log.d(TAG, jsonArr.toString());
                    if (progressDialog != null)
                        progressDialog.dismiss();

                    universalDataListener.onDataReceived(null, jsonArr);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    if (progressDialog != null)
                        progressDialog.dismiss();
                    String message = VolleyErrorHelper.getMessage(volleyError, context);

                    universalDataListener.OnError(message);
                    android.util.Log.e("Error", " error message ..." + message);

                    /*if (message != null && message != "")
                        Toast.makeText(context, MainMenuConstants.NO_SERVICES_ERROR, Toast.LENGTH_SHORT).show();
                    else {
                        String serverError = context.getResources().getString(R.string.shop_error_message);
                        Toast.makeText(context, serverError, Toast.LENGTH_SHORT).show();
                    }*/
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

    /*<--------------DELETE LOYALTY CARD ---------------->*/

    public void PostDelCard(String url, UniversalDataListener universalDataListener) {
        progressDialog = ProgressDialog.show(context,"","Loading");
        requestType = POST_LOYALTY_CARD;
        this.universalDataListener = universalDataListener;
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
        String message = VolleyErrorHelper.getMessage(volleyError, context);
        switch (requestType) {
            case GET_MALL_NEWSnOFFERS: {
                mallDataListener.OnError();
                break;
            }
            case GET_REST_DETAIL: {
                restaurantDataListener.OnError();
                break;
            }
            case GET_SHOP_DETAIL: {
                shopsDataListener.OnError();
                break;
            }
            case GET_MALL_DETAIL: {
                mallDataListener.OnError();
                break;
            }
            case POST_LOYALTY_CARD: {
                universalDataListener.OnError(message);
                break;
            }
        }
        Log.d(TAG, volleyError.toString());
        NetworkResponse networkResponse = volleyError.networkResponse;
        if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {}
            // HTTP Status Code: 401 Unauthorized
        if (progressDialog != null)
            progressDialog.dismiss();

//        listener.OnError();





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

            //region POST_LOYALTY_CARD
            case POST_LOYALTY_CARD: {
                try {
                    boolean success = response.getBoolean("Success");
                    if (success) {
                        universalDataListener.onDataReceived(response,null);
                    }
                    else{
                        String message = response.getString("Message");
                        universalDataListener.OnError(message);
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

            //region GET_USER_SIGN_OUT
            case GET_USER_SIGN_OUT: {
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

            //region GET_ACTIVITY_DETAIL
            case GET_ACTIVITY_DETAIL: {
                try {
                    Gson gson = new Gson();
                    MallActivitiesModel model = gson.fromJson(String.valueOf(response), MallActivitiesModel.class);
                    Log.d(TAG, "Shop Detail:" + String.valueOf(response));
                    if (model != null)
                        activityDetailListener.onDataReceived(model);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            //endregion

            //region GET_MALL_FLOORS
            case GET_MALL_FLOORS: {
                try {
                    Gson gson = new Gson();
                    String data = response.getJSONArray("MallActivities").toString();
                    Type listType = new TypeToken<List<FloorOverViewModel>>() {
                    }.getType();

                    ArrayList<FloorOverViewModel> model = gson.fromJson(data, listType);
                    floorsDataListener.onDataReceived(model);
                } catch (Exception e) {
                    floorsDataListener.OnError();
                    e.printStackTrace();
                }
                break;
            }
            //endregion

            //region POST_NOTIFICATION SETTINGS
            case POST_NOT_SET: {
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

        }
        //endregion
    }



}

package com.mallapp.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.VolleyError;
import com.mallapp.listeners.VolleyDataReceivedListener;
import com.mallapp.listeners.VolleyErrorListener;

import org.json.JSONObject;

/**
 * Created by Sharjeel Haider on 11/16/2015.
 */
public class VolleyNetworkUtil implements VolleyErrorListener,VolleyDataReceivedListener{

    private Context context;
    private ProgressDialog progressDialog;
    private String TAG = VolleyNetworkUtil.class.getSimpleName();




    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }
}

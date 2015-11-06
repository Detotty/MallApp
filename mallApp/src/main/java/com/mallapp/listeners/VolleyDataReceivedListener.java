package com.mallapp.listeners;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by Sharjeel on 11/6/2015.
 */
public interface VolleyDataReceivedListener extends Response.Listener<JSONObject> {

    @Override
    void onResponse(JSONObject response);
}
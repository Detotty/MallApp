package com.mallapp.listeners;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Sharjeel on 11/6/2015.
 */
public interface VolleyErrorListener extends Response.ErrorListener {

    @Override
    void onErrorResponse(VolleyError volleyError);

}
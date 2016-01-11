package com.mallapp.utils;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Sharjeel on 1/11/2016.
 */
public class ourViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView v, String url){
        v.loadUrl(url);
        return true;

    }

}

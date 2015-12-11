package com.mallapp.View;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by hpp on 12/10/2015.
 */
public class WebViewActivity extends Activity {

    String urls;
    ImageButton is_fav,back_screen;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);

        is_fav		= (ImageButton) findViewById(R.id.fav_offer);
        is_fav.setVisibility(View.GONE);
        back_screen = (ImageButton) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.offer_title);
        title.setText("Website");
        urls = getIntent().getStringExtra("url");
        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl(urls);
        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        back_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals(urls)) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
}

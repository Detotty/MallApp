package com.mallapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mallapp.View.R;
import com.mallapp.utils.ourViewClient;

/**
 * Created by Sharjeel on 1/11/2016.
 */
public class WebFragment extends FragmentActivity {

    private View viewInflate;
    WebView r;
    ProgressBar Pbar;
    TextView heading;
    String url;


    public WebFragment() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_web);
        ImageButton button_back = (ImageButton) findViewById(R.id.back);
        heading = (TextView) findViewById(R.id.heading);
        heading.setText(getIntent().getStringExtra("heading"));
        url = getIntent().getStringExtra("url");
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                /*overridePendingTransition(R.anim.right_out,
                        R.anim.right_in);*/

            }
        });
        r=(WebView) findViewById(R.id.webView);
        r.setInitialScale(50);
        r.getSettings().setUseWideViewPort(true);
        r.setVerticalScrollBarEnabled(false);
        r.setHorizontalScrollBarEnabled(false);

        WebSettings webSettings = r.getSettings();
        webSettings.setJavaScriptEnabled(true);



        r.getSettings().setBuiltInZoomControls(true);

        r.setWebViewClient(new ourViewClient());
        r.loadUrl(url);


        Pbar = (ProgressBar) findViewById(R.id.progressBar);
        r.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                if(progress < 100 && Pbar.getVisibility() == ProgressBar.GONE){
                    Pbar.setVisibility(ProgressBar.VISIBLE);

                }
                Pbar.setProgress(progress);
                if(progress == 100) {
                    Pbar.setVisibility(ProgressBar.GONE);

                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



}


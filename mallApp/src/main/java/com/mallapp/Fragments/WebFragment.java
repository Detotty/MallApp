package com.mallapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.mallapp.View.R;
import com.mallapp.utils.ourViewClient;

/**
 * Created by Sharjeel on 1/11/2016.
 */
public class WebFragment extends Fragment {

    private View viewInflate;
    WebView r;
    ProgressBar Pbar;


    public WebFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.fragment_web, null);
        r=(WebView) viewInflate.findViewById(R.id.webView);
        r.setInitialScale(50);
        r.getSettings().setUseWideViewPort(true);
        r.setVerticalScrollBarEnabled(false);
        r.setHorizontalScrollBarEnabled(false);

        WebSettings webSettings = r.getSettings();
        webSettings.setJavaScriptEnabled(true);



        r.getSettings().setBuiltInZoomControls(true);

        r.setWebViewClient(new ourViewClient());
        r.loadUrl("https://www.google.com.pk/");


        Pbar = (ProgressBar) viewInflate.findViewById(R.id.progressBar);
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

        return viewInflate;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);

    }

}


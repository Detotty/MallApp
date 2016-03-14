package com.mallapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Sharjeel on 1/25/2016.
 */
public class FullScreenImage extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image_activity);
        ImageView img = (ImageView) findViewById(R.id.imageView1);
        TextView textView_done = (TextView) findViewById(R.id.textView_done);
        textView_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        Intent i = getIntent();
        String decoded = i.getExtras().getString("img");
        Picasso.with(this).load(decoded).rotate(90).placeholder(R.drawable.mallapp_placeholder).into(img);

        try {
            String url = i.getExtras().getString("url");
            if (url!=null || url =="null")
            Picasso.with(this).load(url).placeholder(R.drawable.mallapp_placeholder).into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

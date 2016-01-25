package com.mallapp.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
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
        Picasso.with(this).load(decoded).rotate(90).into(img);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

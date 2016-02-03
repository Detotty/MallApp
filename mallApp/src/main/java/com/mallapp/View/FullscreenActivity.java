package com.mallapp.View;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mallapp.Constants.AppConstants;
import com.mallapp.Model.UserProfileModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.squareup.picasso.Picasso;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends Activity {
    UserProfileModel user_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        ImageView img = (ImageView) findViewById(R.id.imageView1);
        TextView textView_done = (TextView) findViewById(R.id.textView_done);
        textView_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        Intent i = getIntent();
        user_profile = (UserProfileModel) DataHandler.getObjectPreferences(AppConstants.PROFILE_DATA, UserProfileModel.class);

        String decoded = user_profile.getImageBase64String();
        if (decoded!=null){
            byte[] imageAsBytes = Base64.decode(decoded.getBytes(), 0);
            img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        }
        else {
            img.setImageResource(R.drawable.avatar);
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

package com.mallapp.View;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mallapp.utils.StaticLiterls;


public class ProfileContactScreen extends Activity {


    String id;
    String nmbr;

    TextView button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_screen_profile);

        loadUi();
    }

    TextView contactNmbr;
    ImageView contactImage;

    private void loadUi() {
        // TODO Auto-generated method stub
        contactNmbr = (TextView) findViewById(R.id.contact_name);
        contactImage = (ImageView) findViewById(R.id.contact_image);
        ImageView imageView_invite = (ImageView) findViewById(R.id.invite_icon);

        TextView headerTextView = (TextView) findViewById(R.id.heading);

        nmbr = getIntent().getStringExtra("number");

        String name = getIntent().getStringExtra("name");
        headerTextView.setText(name);

        contactNmbr.setText(nmbr);
        if(BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("image"), 0, getIntent().getByteArrayExtra("image").length)!=null) {
            Bitmap _bitmap = StaticLiterls.getResizedBitmap(BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("image"), 0, getIntent().getByteArrayExtra("image").length));
            contactImage.setImageBitmap(_bitmap);
        }
        ImageButton closeButton = (ImageButton) findViewById(R.id.back);

        closeButton.setVisibility(View.VISIBLE);
        closeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
        imageView_invite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticLiterls.sendSms(ProfileContactScreen.this, getResources().getString(R.string.ma_share_msg) + "\n" + StaticLiterls.link);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

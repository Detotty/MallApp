package com.mallapp.View;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mallapp.Controllers.ShopList;
import com.mallapp.Model.Offers_News;
import com.mallapp.Model.Shops;
import com.mallapp.SharedPreferences.SharedPreference;
import com.mallapp.globel.GlobelShops;
import com.mallapp.socialsharing.Facebook_Login;
import com.mallapp.socialsharing.Twitter_Integration;
import com.mallapp.utils.GlobelOffersNews;

public class OffersRelatedDetailActivity extends Activity implements OnClickListener{
	
	private ImageView offer_image;
	private TextView offer_title, shope_name, offer_detail;
	private ImageButton back_screen, is_fav;
	private Button go_to_shop, social_sharing;
	private LinearLayout related_offers, social_sharing_layout;
	private ImageButton message, face_book, twitter, email, chat;
	Offers_News offer_object;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offer_detail_activity);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //UG4fewnT7&RV
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		offer_object= GlobelOffersNews.related_offer_obj;
		init();
		setOfferDetail();
		
	}

	private void setOfferDetail() {	
		offer_title.setText(offer_object.getTitle());
		shope_name.setText(offer_object.getShop_name());
		offer_detail.setText(offer_object.getDetail());
		boolean fav	= offer_object.isFav();
		if(fav)
			is_fav.setImageResource(R.drawable.ofer_detail_heart_p);
		else
			is_fav.setImageResource(R.drawable.ofer_detail_heart);
		
		String image_= offer_object.getImage();
		int imageResource = getResources().getIdentifier(image_, "drawable", getPackageName());
		Drawable d = getResources().getDrawable(imageResource);
		Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		//int width = size.x;
		int mDstWidth 	= size.x;;//getResources().getDimensionPixelSize(R.dimen.createview_destination_width);
        int mDstHeight 	= getResources().getDimensionPixelSize(R.dimen.offer_detail_height);
		d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, mDstWidth,mDstHeight, true));
		offer_image.setBackground(d);
		setRelatedOffers();
	}

	private void setRelatedOffers() {
		related_offers.removeAllViews();
		
		SharedPreference sharedPreference = new SharedPreference();
		ArrayList <Offers_News> endorsement_list_filter = sharedPreference.getOffersNews(getApplicationContext());
		if(endorsement_list_filter!=null){
			for(int i=0; i<3; i++){
				Offers_News object= endorsement_list_filter.get(i);
				View view =add_layout(object);
				related_offers.addView(view);
			}
		}
	}

	private View add_layout(final Offers_News offer_obj) {
		
        final View view 				= getLayoutInflater().inflate(R.layout.list_item_offers, null);
        
        TextView title 		= (TextView) view.findViewById(R.id.title);
        TextView decs 		= (TextView) view.findViewById(R.id.center_city);
        TextView center_name= (TextView) view.findViewById(R.id.center_name);
        TextView shop_name 	= (TextView) view.findViewById(R.id.shop_name);
        final ImageButton is_fav	= (ImageButton) view.findViewById(R.id.fav_center);
        ImageView back_image= (ImageView) view.findViewById(R.id.center_image);
        
        title.setText(offer_obj.getTitle());
		decs.setText(offer_obj.getDetail());
		center_name.setText(offer_obj.getCenter_name());
		shop_name.setText(offer_obj.getShop_name());
		
		final boolean fav	= offer_obj.isFav();
		if(fav)
			is_fav.setImageResource(R.drawable.offer_fav_p);
		else
			is_fav.setImageResource(R.drawable.offer_fav);
		
		String image_nam	= offer_obj.getImage();
		int imageResource 	= getResources().getIdentifier(image_nam, "drawable", getPackageName());
		Drawable d 			= getResources().getDrawable(imageResource);
		Bitmap bitmap 	= ((BitmapDrawable) d).getBitmap();
		int mDstWidth 	= getResources().getDimensionPixelSize(R.dimen.createview_destination_width);
        int mDstHeight 	= getResources().getDimensionPixelSize(R.dimen.createview_destination_height);
		d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, mDstWidth,mDstHeight, true));
		back_image.setBackground(d);
		
		is_fav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!offer_obj.isFav()){
					is_fav.setImageResource(R.drawable.offer_fav_p);
					offer_obj.setFav(true);
				}else{
					is_fav.setImageResource(R.drawable.offer_fav);
					offer_obj.setFav(false);
				}
			}
		});
		
        view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("related offer...", "related offer...");
				GlobelOffersNews.related_offer_obj= offer_obj;
				Intent intent= new Intent(OffersRelatedDetailActivity.this, OffersRelatedDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		return view;
	}

	private void init() {
		offer_title = (TextView) findViewById(R.id.offer_title);
		shope_name 	= (TextView) findViewById(R.id.shop_name);
		offer_detail= (TextView) findViewById(R.id.offer_detail);
		back_screen = (ImageButton) findViewById(R.id.back);
		is_fav		= (ImageButton) findViewById(R.id.fav_offer);
		go_to_shop	= (Button) 	findViewById(R.id.go_to_shop);
//		social_sharing=(Button) findViewById(R.id.share_detail_popup);
//		social_sharing.setOnClickListener(this);

		is_fav.setOnClickListener(this);
		go_to_shop.setOnClickListener(this);
		back_screen.setOnClickListener(this);
		
//		related_offers			= (LinearLayout) findViewById(R.id.related_offers);
		social_sharing_layout	= (LinearLayout) findViewById(R.id.social_layout);
		
		message		= (ImageButton) findViewById(R.id.sms);
		face_book	= (ImageButton) findViewById(R.id.fb); 
		twitter 	= (ImageButton) findViewById(R.id.twiter);
		email		= (ImageButton) findViewById(R.id.email);
		chat		= (ImageButton) findViewById(R.id.chat);
		
		message		.setOnClickListener(this);
		face_book	.setOnClickListener(this); 
		twitter 	.setOnClickListener(this);
		email		.setOnClickListener(this);
		chat		.setOnClickListener(this);
	}

	private Shops readShopList() {
		Shops shop_obj= null;
		ArrayList <Shops> shops_read_audience = GlobelShops.shop_array;
		
		if(shops_read_audience == null || shops_read_audience.size() == 0){
			ShopList.saveOffersNewsData(getApplicationContext());
			shops_read_audience 	= ShopList.readShopsList(getApplicationContext());
			GlobelShops.shop_array	= shops_read_audience;
			shop_obj= shops_read_audience.get(0);
		}else{
			shop_obj= shops_read_audience.get(0);
		}
		return shop_obj;
	}
	
	
	@Override
	public void onClick(View v) {
		if(v.getId() == back_screen.getId()){
			finish();
		}else if(v.getId() == is_fav.getId()){
			boolean fav	= offer_object.isFav();
			if(fav){
				is_fav.setImageResource(R.drawable.ofer_detail_heart);
				offer_object.setFav(false);
				
			}else{
				is_fav.setImageResource(R.drawable.ofer_detail_heart_p);
				offer_object.setFav(true);
			}
		}else if(v.getId() == go_to_shop.getId()){
			Shops shop_obj= readShopList();
			GlobelShops.shop_obj= shop_obj;
			Intent activity= new Intent(OffersRelatedDetailActivity.this, ShopDetailActivity.class);
			startActivity(activity);
		}else if(v.getId() == social_sharing.getId()){
			if(social_sharing_layout.getVisibility()== View.GONE)
				social_sharing_layout.setVisibility(View.VISIBLE);
			else
				social_sharing_layout.setVisibility(View.GONE);
		
		}else if(v.getId()== face_book.getId()){
		
			Facebook_Login fb_profile= new Facebook_Login(getApplicationContext(), 
					OffersRelatedDetailActivity.this, true );
			fb_profile.loginToFacebook();
			
		}else if(v.getId()== twitter.getId()){
		
			Twitter_Integration twitter= new Twitter_Integration(getApplicationContext(), 
					OffersRelatedDetailActivity.this);
			twitter.post_twitter();
			
		}else if(v.getId()== email.getId()){
		
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
			emailIntent.setType("plain/text");  
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share with friends");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"This mail from endorsement" );
			startActivity(emailIntent);
			
		}else if(v.getId()== message.getId()){
		
			Intent sendIntent = new Intent(Intent.ACTION_VIEW);
	        sendIntent.putExtra("sms_body", "Content of the SMS goes here..."); 
	        sendIntent.setType("vnd.android-dir/mms-sms");
	        startActivity(sendIntent);
		
		}else if(v.getId()== chat.getId()){
			
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
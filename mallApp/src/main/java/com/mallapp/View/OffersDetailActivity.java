package com.mallapp.View;

import java.sql.SQLException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Controllers.ShopList;
import com.mallapp.Model.BannerImagesModel;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.Offers_News;
import com.mallapp.Model.Shops;
import com.mallapp.SharedPreferences.SharedPreference;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.db.DatabaseHelper;
import com.mallapp.globel.GlobelShops;
import com.mallapp.socialsharing.Facebook_Login;
import com.mallapp.socialsharing.Twitter_Integration;
import com.mallapp.utils.GlobelOffersNews;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;

@SuppressLint("InflateParams") 

public class OffersDetailActivity extends Activity implements OnClickListener,BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
	
	private ImageView 	offer_image;
	private TextView 	offer_title, shope_name, offer_detail;
	private ImageButton back_screen, is_fav;
	private Button 		go_to_shop, social_sharing;
	private RelativeLayout related_offers, social_sharing_layout;
	private ImageButton message, face_book, twitter, email;
	MallActivitiesModel offer_object;
	private SliderLayout mDemoSlider;
	String url = ApiConstants.POST_FAV_OFFERS_URL_KEY;
	Dao<MallActivitiesModel, Integer> mallActivitiesModelIntegerDaol;
	VolleyNetworkUtil volleyNetworkUtil;
	private DatabaseHelper databaseHelper = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offer_detail_activity);
		volleyNetworkUtil = new VolleyNetworkUtil(this);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //UG4fewnT7&RV
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
//		offer_object= GlobelOffersNews.offer_obj;
		offer_object = (MallActivitiesModel) getIntent().getSerializableExtra(Offers_News_Constants.MALL_OBJECT);
		init();
		setOfferDetail();
	}

	private void setOfferDetail() {
		offer_title.setText(offer_object.getActivityTextTitle());
		shope_name.setText(offer_object.getMallName());
		offer_detail.setText(offer_object.getDetailText());
		boolean fav	= offer_object.isFav();
		if(fav)
			is_fav.setImageResource(R.drawable.ofer_detail_heart_p);
		else
			is_fav.setImageResource(R.drawable.ofer_detail_heart);
		ImageSlider(offer_object.getBannerImages());
		/*String image_		= offer_object.getImage();
		int imageResource 	= getResources().getIdentifier(image_, "drawable", getPackageName());
		Drawable d 			= getResources().getDrawable(imageResource);
		Bitmap bitmap 		= ((BitmapDrawable) d).getBitmap();
		
		Display display 	= getWindowManager().getDefaultDisplay();
		Point size 			= new Point();
		display.getSize(size);
		//int width = size.x;
		int mDstWidth 	= size.x;;//getResources().getDimensionPixelSize(R.dimen.createview_destination_width);
        int mDstHeight 	= getResources().getDimensionPixelSize(R.dimen.offer_detail_height);
		d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, mDstWidth,mDstHeight, true));
		offer_image.setBackground(d);*/
//		Picasso.with(this).load(offer_object.getImageURL()).into(offer_image);
//		setRelatedOffers();
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
		LayoutInflater layoutInflater 	= (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view 				= layoutInflater.inflate(R.layout.list_item_offers, null);
        
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
				Intent intent= new Intent(OffersDetailActivity.this, OffersRelatedDetailActivity.class);
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
		mDemoSlider = (SliderLayout)findViewById(R.id.slider);

		go_to_shop.setOnClickListener(this);
		back_screen.setOnClickListener(this);
		is_fav.setOnClickListener(this);
		
//		related_offers			= (LinearLayout) findViewById(R.id.related_offers);
		social_sharing_layout	= (RelativeLayout) findViewById(R.id.social_layout);
		
		message		= (ImageButton) findViewById(R.id.sms);
		face_book	= (ImageButton) findViewById(R.id.fb); 
		twitter 	= (ImageButton) findViewById(R.id.twiter);
		email		= (ImageButton) findViewById(R.id.email);

		message		.setOnClickListener(this);
		face_book	.setOnClickListener(this); 
		twitter 	.setOnClickListener(this);
		email		.setOnClickListener(this);

		try {
			// This is how, a reference of DAO object can be done
			mallActivitiesModelIntegerDaol =  getHelper().getMallActivitiesDao();

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				volleyNetworkUtil.PostFavNnO(url + SharedPreferenceUserProfile.getUserId(this) + "&ActivityId=" + offer_object.getActivityId() + "&isDeleted=true");
						offer_object.setFav(false);
				updateMalls(offer_object);
				
			}else{
				is_fav.setImageResource(R.drawable.ofer_detail_heart_p);
				volleyNetworkUtil.PostFavNnO(url + SharedPreferenceUserProfile.getUserId(this) + "&ActivityId=" + offer_object.getActivityId() + "&isDeleted=false");
				offer_object.setFav(true);
				updateMalls(offer_object);
			}
		}else if(v.getId() == go_to_shop.getId()){
		
			Shops shop_obj= readShopList();
			GlobelShops.shop_obj= shop_obj;
			Intent activity= new Intent(OffersDetailActivity.this, ShopDetailActivity.class);
			startActivity(activity);
		
		}else if(v.getId() == social_sharing.getId()){
			if(social_sharing_layout.getVisibility()== View.GONE)
				social_sharing_layout.setVisibility(View.VISIBLE);
			else
				social_sharing_layout.setVisibility(View.GONE);
		
		}else if(v.getId()== face_book.getId()){
		
			Facebook_Login fb_profile= new Facebook_Login(getApplicationContext(), 	OffersDetailActivity.this, true );
			fb_profile.loginToFacebook();
			
		}else if(v.getId()== twitter.getId()){
		
			Twitter_Integration twitter= new Twitter_Integration(getApplicationContext(), OffersDetailActivity.this);
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
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}
	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
		return databaseHelper;
	}

	public void ImageSlider(BannerImagesModel bImages[]){
		for(BannerImagesModel name : bImages){
			TextSliderView textSliderView = new TextSliderView(this);
			// initialize a SliderLayout
			textSliderView
					.error(R.drawable.placeholder)
					.empty(R.drawable.placeholder)
					.errorDisappear(false)
					.image(name.getBannerImageURL())
					.setScaleType(BaseSliderView.ScaleType.Fit)
					.setOnSliderClickListener(this);

			//add your extra information
			textSliderView.bundle(new Bundle());
			textSliderView.getBundle()
					.putString("extra",name.getBannerImageURL());

			mDemoSlider.addSlider(textSliderView);
		}
		mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
		mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
		mDemoSlider.setCustomAnimation(new DescriptionAnimation());
		mDemoSlider.setDuration(3500);
		mDemoSlider.addOnPageChangeListener(this);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onSliderClick(BaseSliderView slider) {

	}

	public void updateMalls(MallActivitiesModel fav){
		try {
			mallActivitiesModelIntegerDaol.createOrUpdate(fav);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
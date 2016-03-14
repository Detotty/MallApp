package com.mallapp.View;

import java.sql.SQLException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.mallapp.Application.MallApplication;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Controllers.ShopList;
import com.mallapp.Fragments.OfferPagerTabFragment;
import com.mallapp.Model.BannerImagesModel;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.MallDetailModel;
import com.mallapp.Model.Offers_News;
import com.mallapp.Model.Shops;
import com.mallapp.SharedPreferences.SharedPreference;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.db.DatabaseHelper;
import com.mallapp.globel.GlobelShops;
import com.mallapp.listeners.ActivityDetailListener;
import com.mallapp.listeners.MallDataListener;
import com.mallapp.socialsharing.Facebook_Login;
import com.mallapp.socialsharing.Twitter_Integration;
import com.mallapp.utils.BadgeUtils;
import com.mallapp.utils.GlobelOffersNews;
import com.mallapp.utils.SocialUtils;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;

@SuppressLint("InflateParams") 

public class OffersDetailActivity extends Activity implements ActivityDetailListener,OnClickListener,BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
	
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
	boolean fav = false;
	CallbackManager callbackManager;
	ShareDialog shareDialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offer_detail_activity);
		volleyNetworkUtil = new VolleyNetworkUtil(this);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //UG4fewnT7&RV
		FacebookSdk.sdkInitialize(getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		shareDialog = new ShareDialog(this);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
//		offer_object= GlobelOffersNews.offer_obj;
		offer_object = (MallActivitiesModel) getIntent().getSerializableExtra(Offers_News_Constants.MALL_OBJECT);
		fav = offer_object.isFav();
		init();
		BadgeUtils.clearBadge(MallApplication.appContext);
		volleyNetworkUtil.GetActivityDetail(ApiConstants.GET_NEWS_OFFERS_DETAIL_URL_KEY+offer_object.getActivityId() + "&languageId=1",this);
	}

	private void setOfferDetail() {
		offer_title.setText(offer_object.getActivityTextTitle());
		shope_name.setText(offer_object.getMallName());
		offer_detail.setText(offer_object.getDetailText());

		if (offer_object.getEntityType().equals(Offers_News_Constants.ENTITY_TYPE_SHOP)){
			go_to_shop.setVisibility(View.VISIBLE);
		}
		boolean fav	= offer_object.isFav();
		if(fav)
			is_fav.setImageResource(R.drawable.ofer_detail_heart_p);
		else
			is_fav.setImageResource(R.drawable.ofer_detail_heart);
		try {
			ImageSlider(offer_object.getBannerImages());
		}catch (Exception e){

			mDemoSlider.setVisibility(View.GONE);
			offer_image.setVisibility(View.VISIBLE);
			Picasso.with(this).load(offer_object.getImageURL()).placeholder(R.drawable.mallapp_placeholder).into(offer_image);
		}
	}


	private void init() {
		
		offer_title = (TextView) findViewById(R.id.offer_title);
		shope_name 	= (TextView) findViewById(R.id.shop_name);
		offer_detail= (TextView) findViewById(R.id.offer_detail);
		back_screen = (ImageButton) findViewById(R.id.back);
		is_fav		= (ImageButton) findViewById(R.id.fav_offer);
		offer_image		= (ImageView) findViewById(R.id.iv_background);
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
//				updateMalls(offer_object);
				OfferPagerTabFragment.isRefresh = true;
			}else{
				is_fav.setImageResource(R.drawable.ofer_detail_heart_p);
				volleyNetworkUtil.PostFavNnO(url + SharedPreferenceUserProfile.getUserId(this) + "&ActivityId=" + offer_object.getActivityId() + "&isDeleted=false");
				offer_object.setFav(true);
//				updateMalls(offer_object);
				OfferPagerTabFragment.isRefresh = true;
			}
		}else if(v.getId() == go_to_shop.getId()){
		
			/*Shops rest_obj= readShopList();
			GlobelShops.rest_obj= rest_obj;*/
			Intent activity= new Intent(OffersDetailActivity.this, ShopDetailActivity.class);
			activity.putExtra("MallStoreId", offer_object.getEntityId());
			startActivity(activity);
		
		}/*else if(v.getId() == social_sharing.getId()){
			if(social_sharing_layout.getVisibility()== View.GONE)
				social_sharing_layout.setVisibility(View.VISIBLE);
			else
				social_sharing_layout.setVisibility(View.GONE);
		
		}*/else if(v.getId()== face_book.getId()){
			ShareLinkContent linkContent = new ShareLinkContent.Builder()
					.setContentTitle("The Mall App")
					.setContentDescription(
							getResources().getString(R.string.mall_app_invite_msg))
					.setContentUrl(Uri.parse("http://mallappbackend.zap-itsolutions.com/"))
					.build();

			shareDialog.show(linkContent);
			/*Facebook_Login fb_profile= new Facebook_Login(getApplicationContext(), 	OffersDetailActivity.this, true );
			fb_profile.loginToFacebook();*/
			
		}else if(v.getId()== twitter.getId()){
			SocialUtils.postToTwitter(this, getResources().getString(R.string.mall_app_invite_msg) + "\n");
			/*Twitter_Integration twitter= new Twitter_Integration(getApplicationContext(), OffersDetailActivity.this);
			twitter.post_twitter();*/
			
		}else if(v.getId()== email.getId()){
			SocialUtils.sendEmail(this, getResources().getString(R.string.mall_app_invite_msg) + "\n");

		}else if(v.getId()== message.getId()){
			SocialUtils.sendSms(this, getResources().getString(R.string.mall_app_invite_msg) + "\n");

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
					.error(R.drawable.mallapp_placeholder)
					.empty(R.drawable.mallapp_placeholder)
					.errorDisappear(false)
					.image(name.getBannerImageURL())
					.setScaleType(BaseSliderView.ScaleType.Fit)
					.setOnSliderClickListener(this);

			//add your extra information
			textSliderView.bundle(new Bundle());
			textSliderView.getBundle()
					.putString("extra", name.getBannerImageURL());

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


	@Override
	public void onDataReceived(MallActivitiesModel mallActivitiesModels) {
		offer_object = mallActivitiesModels;
//		offer_object.setFav(fav);
		setOfferDetail();
	}

	@Override
	public void OnError() {

	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
}
package com.mallapp.Fragments;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.List.Adapter.Offers_News_Adapter;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Controllers.OffersNewsFiltration;
import com.mallapp.Model.Offers_News;
import com.mallapp.View.R;
import com.mallapp.utils.GlobelOffersNews;
import com.mallapp.utils.Log;

public class OfferPagerTabFragment extends Fragment {

	private static Context context;
	private static final String ARG_POSITION = "position";
	private static final String ARG_AUDIENCE = "audience";
	private ArrayList<String> TITLES;
	
	static ArrayList <Offers_News> endorsement_list_filter;
	static ArrayList <Offers_News> all_audience;
	static ArrayList <Offers_News> offers_audience;
	static ArrayList <Offers_News> news_audience;
	
	static ArrayList <Drawable> 	endorsement_list_filter_images;
	static ArrayList <Drawable> 	all_audience_images;
	static ArrayList <Drawable> 	offers_audience_images;
	static ArrayList <Drawable> 	news_audience_images;
	
	private ListView list;
	private int position;
	public static Handler uihandler;
	static String audienceFilter;
	String favouriteCentersFilter, headerFilter="";
	static ArrayList<BitmapDrawable> endorsement_images_list;
	boolean navigationFilter= false;
	Offers_News_Adapter adapter;

	public static OfferPagerTabFragment newInstance(
										int position, Handler handler, Context c,
										String header_filter) {
		context= c;
		uihandler		=	handler;
		audienceFilter	= 	header_filter;
		
		OfferPagerTabFragment f = new OfferPagerTabFragment();
		Bundle b 		= 	new Bundle();
		b.putInt(ARG_POSITION, position);
		b.putString(ARG_AUDIENCE, header_filter);
		f.setArguments(b);
		return f;
	}

	public OfferPagerTabFragment() {
		super();
	}

	public OfferPagerTabFragment(String favouriteCenterFilter,	String audienceOffersFilter) {
		super();
		this.favouriteCentersFilter = favouriteCenterFilter;
		this.headerFilter = audienceOffersFilter;
		navigationFilter=true;
	}

	public  String getHeaderFilter() {
		return headerFilter;
	}

	public void setHeaderFilter(String audienceEndorsementFilter) {
		this.headerFilter= audienceEndorsementFilter;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context	= getActivity().getApplicationContext();
		if(navigationFilter);
		else{
			position= getArguments().getInt(ARG_POSITION);
			if(headerFilter!= null && headerFilter.trim().length()>0) ;
			else
				headerFilter = getArguments().getString(ARG_AUDIENCE);
		}
		Log.e("OfferPagerTabFragment", "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		filterData();
		View rootView = inflater.inflate(R.layout.endorsement_view_pager_list, container, false);
		list = (ListView) rootView.findViewById(R.id.mallapp_listview);
		adapter= new Offers_News_Adapter(context, getActivity(),R.layout.list_item_offers, 
															all_audience, 
															offers_audience, 
															news_audience,
															all_audience_images,
															offers_audience_images,
															news_audience_images,
															headerFilter, 
															Offers_News_Constants.OFFERS_CLICK_TYPE);
		
		list.setAdapter(adapter);
		return rootView;
	}
	
	private  void filterData() {
		initArrays();
		// read array once and save in temporarily in class level
		getEndorsementArray();
		if(endorsement_list_filter!=null)
			resetData();
	}
	
	private void getEndorsementArray() {
		if( GlobelOffersNews.endorsement_array != null && GlobelOffersNews.endorsement_array.size()>0){
			endorsement_list_filter= GlobelOffersNews.endorsement_array;
		}
//		else{
//			SharedPreference s_p= new SharedPreference();
//			endorsement_list_filter= s_p.getOffersNews( );
//			GlobelOffersNews.endorsement_array= endorsement_list_filter;
//		}
	}

	private void resetData() {
		if(!navigationFilter){
			TITLES = GlobelOffersNews.TITLES;//SharedPreferenceFavourites.getFavouritesList(context);
			favouriteCentersFilter = TITLES.get(position).trim();
		}
		ArrayList<Offers_News> favouriteList = OffersNewsFiltration.filterFavouriteCenters(
													favouriteCentersFilter, endorsement_list_filter);
		
		all_audience 		= OffersNewsFiltration.getAllAudience(favouriteList);
		all_audience_images	= OffersNewsFiltration.getOffersImagesList(context, all_audience);
		
		for(Offers_News obj: favouriteList){
			if(obj.getType().trim().equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)) {
				offers_audience.add(obj);
			}else if(obj.getType().trim().equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)) {
				news_audience.add(obj);
			}
		}
		offers_audience_images	= OffersNewsFiltration.getOffersImagesList(context, offers_audience);
		news_audience_images	= OffersNewsFiltration.getOffersImagesList(context, news_audience);
	}

	private void initArrays() {
		
		endorsement_list_filter = new ArrayList<Offers_News>();
		all_audience			= new ArrayList<Offers_News>();
		offers_audience			= new ArrayList<Offers_News>();
		news_audience			= new ArrayList<Offers_News>();
		
		all_audience_images		= new ArrayList<Drawable>();
		offers_audience_images	= new ArrayList<Drawable>();
		news_audience_images	= new ArrayList<Drawable>();
	}

	public void changeType_Notification(String new_audience_type) {
		Log.e("changeType_Notification", new_audience_type);
		if(adapter!=null){
			adapter.setAudience_type(new_audience_type);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onDestroyView() {
		navigationFilter= false;
		super.onDestroyView();
	}
}

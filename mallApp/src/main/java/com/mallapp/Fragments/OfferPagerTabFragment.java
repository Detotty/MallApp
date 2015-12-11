package com.mallapp.Fragments;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.List.Adapter.Offers_News_Adapter;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Controllers.FavouriteCentersFiltration;
import com.mallapp.Controllers.OffersNewsFiltration;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.Offers_News;
import com.mallapp.Model.ShopsModel;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.MallApp_Application;
import com.mallapp.View.R;
import com.mallapp.cache.AppCacheManager;
import com.mallapp.cache.CentersCacheManager;
import com.mallapp.db.DatabaseHelper;
import com.mallapp.listeners.MallDataListener;
import com.mallapp.utils.GlobelOffersNews;
import com.mallapp.utils.Log;
import com.mallapp.utils.VolleyNetworkUtil;

public class OfferPagerTabFragment extends Fragment implements MallDataListener {

    private static Context context;
    private static final String ARG_POSITION = "position";
    private static final String ARG_AUDIENCE = "audience";
    private ArrayList<String> TITLES;

    static ArrayList<Offers_News> endorsement_list_filter;
    static ArrayList<MallActivitiesModel> mallActivitiesListing;
    static ArrayList<MallActivitiesModel> dbList = new ArrayList<>();
    static ArrayList<Offers_News> all_audience;
    static ArrayList<Offers_News> offers_audience;
    static ArrayList<Offers_News> news_audience;

    static ArrayList<Drawable> endorsement_list_filter_images;
    static ArrayList<Drawable> all_audience_images;
    static ArrayList<Drawable> offers_audience_images;
    static ArrayList<Drawable> news_audience_images;

    private String requestType = "";
    private final String LAZY_LOADING = "LAZY_LOADING";
    private final String REFRESH_MALL_ACTIVITIES = "REFRESH_MALL_ACTIVITIES";
    private final String LOADING_MALL_ACTIVITIES = "LOADING_MALL_ACTIVITIES";
    private int pageNo = 1;
    boolean lastPage = false;
    boolean isPaused = false;


    private ListView list;
    private int position;
    public static Handler uihandler;
    static String audienceFilter;
    String favouriteCentersFilter, headerFilter = "";
    static ArrayList<BitmapDrawable> endorsement_images_list;
    boolean navigationFilter = false;
    Offers_News_Adapter adapter;
    VolleyNetworkUtil volleyNetworkUtil;
    private SwipeRefreshLayout swipeRefreshLayout;
    Dao<MallActivitiesModel, Integer> mallActivitiesModelIntegerDaol;
    private DatabaseHelper databaseHelper = null;
    ArrayList<MallActivitiesModel> mallActivities_All,
            mallActivities_Offers,
            mallActivities_News;


    public static OfferPagerTabFragment newInstance(
            int position, Handler handler, Context c,
            String header_filter) {
        context = c;
        uihandler = handler;
        audienceFilter = header_filter;

        OfferPagerTabFragment f = new OfferPagerTabFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putString(ARG_AUDIENCE, header_filter);
        f.setArguments(b);
        return f;
    }

    public OfferPagerTabFragment() {
        super();
    }

    /*public OfferPagerTabFragment(String favouriteCenterFilter, String audienceOffersFilter) {
        super();
        this.favouriteCentersFilter = favouriteCenterFilter;
        this.headerFilter = audienceOffersFilter;
        navigationFilter = true;
    }*/

    public String getHeaderFilter() {
        return headerFilter;
    }

    public void setHeaderFilter(String audienceEndorsementFilter) {
        this.headerFilter = audienceEndorsementFilter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        mallActivities_All = new ArrayList<>();
        mallActivities_Offers = new ArrayList<>();
        mallActivities_News = new ArrayList<>();
        if (navigationFilter) ;
        else {
            position = getArguments().getInt(ARG_POSITION);
            if (headerFilter != null && headerFilter.trim().length() > 0) ;
            else
                headerFilter = getArguments().getString(ARG_AUDIENCE);
        }
        Log.e("OfferPagerTabFragment", "onCreate");
        try {
            // This is how, a reference of DAO object can be done
            mallActivitiesModelIntegerDaol = getHelper().getMallActivitiesDao();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		filterData();
        View rootView = inflater.inflate(R.layout.endorsement_view_pager_list, container, false);
        list = (ListView) rootView.findViewById(R.id.mallapp_listview);
        volleyNetworkUtil = new VolleyNetworkUtil(getActivity());
        requestType = LOADING_MALL_ACTIVITIES;
        getLatestListing();
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!requestType.equals(REFRESH_MALL_ACTIVITIES)) {
                    swipeRefreshLayout.setRefreshing(true);
                    pageNo = 1;
                    requestType = REFRESH_MALL_ACTIVITIES;
                    getLatestListing();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        /*adapter= new Offers_News_Adapter(context, getActivity(),R.layout.list_item_offers,
                                                            all_audience,
															offers_audience, 
															news_audience,
															all_audience_images,
															offers_audience_images,
															news_audience_images,
															headerFilter, 
															Offers_News_Constants.OFFERS_CLICK_TYPE);
		
		list.setAdapter(adapter);*/
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isPaused)
            pageNo = 1;
       /* if (headerFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_ALL)){
            lastPage = false;
            getLatestListing();
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
        isPaused = true;
//        pullToRefresh();
    }

    public void getLatestListing() {

        MallIdSelection();
        pullToRefresh();
    }

    public void pullToRefresh() {
        String url = ApiConstants.GET_NEWS_OFFERS_URL_KEY + SharedPreferenceUserProfile.getUserId(context) + "&LanguageId=1" + "&MallPlaceId="+MainMenuConstants.SELECTED_MALL_PLACE_ID+ "&PageIndex=" + pageNo + "&PageSize=10";
        volleyNetworkUtil.GetMallNewsnOffers(url, this);
    }

    private void filterData() {
        initArrays();
        // read array once and save in temporarily in class level
        getEndorsementArray();
        if (endorsement_list_filter != null)
            resetData();
    }

    private void getEndorsementArray() {
        if (GlobelOffersNews.endorsement_array != null && GlobelOffersNews.endorsement_array.size() > 0) {
            endorsement_list_filter = GlobelOffersNews.endorsement_array;
        }
//		else{
//			SharedPreference s_p= new SharedPreference();
//			endorsement_list_filter= s_p.getOffersNews( );
//			GlobelOffersNews.endorsement_array= endorsement_list_filter;
//		}
    }

    private void resetData() {
        if (!navigationFilter) {
            TITLES = GlobelOffersNews.TITLES;//SharedPreferenceFavourites.getFavouritesList(context);
            favouriteCentersFilter = TITLES.get(position).trim();
        }
        ArrayList<Offers_News> favouriteList = OffersNewsFiltration.filterFavouriteCenters(
                favouriteCentersFilter, endorsement_list_filter);

        all_audience = OffersNewsFiltration.getAllAudience(favouriteList);
        all_audience_images = OffersNewsFiltration.getOffersImagesList(context, all_audience);

        for (Offers_News obj : favouriteList) {
            if (obj.getType().trim().equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)) {
                offers_audience.add(obj);
            } else if (obj.getType().trim().equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)) {
                news_audience.add(obj);
            }
        }
        offers_audience_images = OffersNewsFiltration.getOffersImagesList(context, offers_audience);
        news_audience_images = OffersNewsFiltration.getOffersImagesList(context, news_audience);
    }

    private void initArrays() {

        endorsement_list_filter = new ArrayList<Offers_News>();
        all_audience = new ArrayList<Offers_News>();
        offers_audience = new ArrayList<Offers_News>();
        news_audience = new ArrayList<Offers_News>();

        all_audience_images = new ArrayList<Drawable>();
        offers_audience_images = new ArrayList<Drawable>();
        news_audience_images = new ArrayList<Drawable>();
    }

    public void changeType_Notification(String new_audience_type) {
        Log.e("changeType_Notification", new_audience_type);

        if (adapter != null) {
            Log.e("changeType_Notification", new_audience_type);
            adapter.setAudience_type(new_audience_type);
            /*if (mallActivitiesListing != null && mallActivitiesListing.size() > 0)
                mallActivitiesListing.clear();
            adapter.notifyDataSetChanged();*/
            requestType = LOADING_MALL_ACTIVITIES;
            pageNo = 1;
            if (!new_audience_type.equals(MainMenuConstants.AUDIENCE_FILTER_ALL)){
                callAddapter();
            }
            else
                pullToRefresh();

        }
    }

    @Override
    public void onDestroyView() {
        navigationFilter = false;
        super.onDestroyView();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onDataReceived(final ArrayList<MallActivitiesModel> mallActivitiesModels) {

        switch (requestType) {

            case LOADING_MALL_ACTIVITIES: {
                requestType = "";
                uihandler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (mallActivitiesModels != null && mallActivitiesModels.size() > 0) {
                            if (mallActivitiesModels.size() < 10)
                                lastPage = true;
                            mallActivitiesListing = FavouriteSelection(context, mallActivitiesModels);
//                            mallActivitiesListing = mallActivitiesModels;

                            callAddapter();
                            list.setOnScrollListener(new AbsListView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(AbsListView view, int scrollState) {

                                }

                                @Override
                                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                    Log.d("", "VisibleItemCount:" + visibleItemCount + ":: adapter count:" + adapter.getCount() + "::" + firstVisibleItem + "::" + totalItemCount + ":: calculation :" + (totalItemCount - visibleItemCount));
                                    Log.e("OfferPagerTabFragment", "onCreate position " + position);//+ "... audience..." + ((CategoryListingModel) list.getItemAtPosition(position)).getName());

                                    if (adapter.getCount() > 0)
                                        if (!lastPage && (totalItemCount - visibleItemCount) <= (firstVisibleItem) && requestType != LAZY_LOADING) {
                                            requestType = LAZY_LOADING;
                                            MallIdSelection();
                                            pageNo++;
                                            pullToRefresh();
                                        }
                                }
                            });
                        } else {
                            list.setAdapter(null);
                            mallActivitiesListing = null;
                            Toast.makeText(context, "No Mall Activity Found!", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
            }

            case LAZY_LOADING: {
                uihandler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (mallActivitiesModels != null && mallActivitiesModels.size() > 0) {
//                            mallActivitiesListing.addAll(FavouriteSelection(context, mallActivitiesModels));
                            mallActivitiesListing = FavouriteSelection(context, mallActivitiesModels);
                            callAddapter();
                            requestType = "";
                            if (mallActivitiesModels.size() < 10)
                                lastPage = true;
                            Log.e("OfferPagerTabFragment", "onCreate position " + position);//+ "... audience..." + ((CategoryListingModel)list.getItemAtPosition(position)).getName());
                        } else {
                            lastPage = true;
                        }
                    }
                });
                break;
            }

            case REFRESH_MALL_ACTIVITIES: {
                Log.d("REFRESH_MALL_ACTIVITY", "REFRESH_MALL_ACTIVITY:" + mallActivitiesModels.size());
                requestType = "";
                uihandler.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        if (mallActivitiesModels.size() < 10)
                            lastPage = true;
                        else
                            lastPage = false;
                        if (mallActivitiesListing != null && mallActivitiesListing.size() > 0) {
                            mallActivitiesListing.clear();
                            ArrayList<MallActivitiesModel> news = FavouriteSelection(context, mallActivitiesModels);
                            mallActivities_All.clear();
                            mallActivitiesListing.addAll(0, news);
                            callAddapter();
                        } else {
                            mallActivitiesListing = FavouriteSelection(context, mallActivitiesModels);
                            FilteredOffersNewsList(mallActivitiesListing);
                            callAddapter();
//                            adapter.notifyDataSetChanged();
                            list.setOnScrollListener(new AbsListView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(AbsListView view, int scrollState) {

                                }

                                @Override
                                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                    Log.d("", "VisibleItemCount:" + visibleItemCount + ":: adapter count:" + adapter.getCount() + "::" + firstVisibleItem + "::" + totalItemCount + ":: calculation :" + (totalItemCount - visibleItemCount));
                                    if (adapter.getCount() > 0)
                                        if (!lastPage && (totalItemCount - visibleItemCount) <= (firstVisibleItem) && requestType != LAZY_LOADING) {
                                            requestType = LAZY_LOADING;
                                            MallIdSelection();
                                            pageNo++;
                                            pullToRefresh();
                                        }
                                }
                            });
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
            }
        }
    }

    @Override
    public void OnError() {
        switch (requestType) {
            case REFRESH_MALL_ACTIVITIES: {
                if (adapter!= null)
                 adapter.clear();

//                mallActivities_All.clear();

//                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
            break;

            case LOADING_MALL_ACTIVITIES:{
                if (mallActivities_All!=null) {
                    mallActivities_All.clear();
                    if (adapter!=null)
                    adapter.notifyDataSetChanged();
                }
                break;
            }
        }
        uihandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    public static void writeOffersNews(Context context, ArrayList<MallActivitiesModel> offer_objects) {
        try {
            AppCacheManager.writeObjectList(context, offer_objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.w(MallApp_Application.TAG, "write:" + offer_objects.size());
    }

    public static ArrayList<MallActivitiesModel> readOffersNews(Context context) {
        try {
            mallActivitiesListing = AppCacheManager.readObjectList(context);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Log.w(MallApp_Application.TAG, "read:"+offer_objects.size());
        return mallActivitiesListing;
    }

    public ArrayList<MallActivitiesModel> FavouriteSelection(Context context, ArrayList<MallActivitiesModel> mallModelArrayList) {
        getDBMalls();
        if (dbList != null) {
            for (MallActivitiesModel shop : dbList
                    ) {
                for (int i = 0; i < mallModelArrayList.size(); i++) {
                    MallActivitiesModel sh = mallModelArrayList.get(i);
                    if (sh.getActivityId().equals(shop.getActivityId())) {
                        if (shop.isFav()) {
                            sh.setFav(true);
                            mallModelArrayList.set(i, sh);
                        }
                    }
                }
            }
        }
        return mallModelArrayList;
    }

    public void getDBMalls() {
        try {
            // This is how, a reference of DAO object can be done
            Dao<MallActivitiesModel, Integer> studentDao = getHelper().getMallActivitiesDao();
            // Get our query builder from the DAO
            final QueryBuilder<MallActivitiesModel, Integer> queryBuilder = studentDao.queryBuilder();
            // We need only Students who are associated with the selected Teacher, so build the query by "Where" clause
            // Prepare our SQL statement
            final PreparedQuery<MallActivitiesModel> preparedQuery = queryBuilder.prepare();
            // Fetch the list from Database by queryingit
            final Iterator<MallActivitiesModel> studentsIt = studentDao.queryForAll().iterator();
            // Iterate through the StudentDetails object iterator and populate the comma separated String
            while (studentsIt.hasNext()) {
                final MallActivitiesModel sDetails = studentsIt.next();
                dbList.add(sDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void FilteredOffersNewsList(ArrayList<MallActivitiesModel> all) {


        for (MallActivitiesModel ma : all) {

            if (ma.getActivityName().equals("Offer")) {
                mallActivities_Offers.add(ma);

               /* if (MainMenuConstants.SELECTED_CENTER_NAME.equals("All")) {
                    mallActivities_Offers.add(ma);
                } else {
                    if (ma.getMallName().equals(MainMenuConstants.SELECTED_CENTER_NAME)) {
                        mallActivities_Offers.add(ma);
                    }
                }*/
            }

            else if (ma.getActivityName().equals("News")) {
                mallActivities_News.add(ma);

                /*if (MainMenuConstants.SELECTED_CENTER_NAME.equals("All")) {
                    mallActivities_News.add(ma);
                } else {
                    if (ma.getMallName().equals(MainMenuConstants.SELECTED_CENTER_NAME)) {
                        this.mallActivities_News.add(ma);
                    }
                }*/
            } else {
                if (MainMenuConstants.SELECTED_CENTER_NAME.equals("All")) {
                    mallActivities_All = all;
                } else {
                    for (MallActivitiesModel mam : mallActivities_All
                            ) {
                        if (mam.getMallName().equals(MainMenuConstants.SELECTED_CENTER_NAME)) {
                            this.mallActivities_All.add(mam);
                        }
                    }
                }
            }
        }
    }

    private void callAddapter(){
        if (headerFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_ALL)){
            /*if (MainMenuConstants.SELECTED_CENTER_NAME.equals("All")) {
                mallActivities_All = mallActivitiesListing;
            } else {
                for (MallActivitiesModel mam : mallActivitiesListing
                        ) {
                    if (mam.getMallName().equals(MainMenuConstants.SELECTED_CENTER_NAME)) {
                        this.mallActivities_All.add(mam);
                    }
                }
            }*/
//            mallActivities_All = mallActivitiesListing;
            mallActivities_All.addAll(mallActivitiesListing);
            adapter = new Offers_News_Adapter(context, getActivity(), R.layout.list_item_offers_new,
                    mallActivities_All, headerFilter, mallActivitiesModelIntegerDaol
            );
        }
        else if (headerFilter.equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)){
            FilteredOffersNewsList(mallActivitiesListing);
            adapter = new Offers_News_Adapter(context, getActivity(), R.layout.list_item_offers_new,
                    mallActivities_Offers, headerFilter, mallActivitiesModelIntegerDaol
            );
        }
        else {
            FilteredOffersNewsList(mallActivitiesListing);
            adapter = new Offers_News_Adapter(context, getActivity(), R.layout.list_item_offers_new,
                    mallActivities_News, headerFilter, mallActivitiesModelIntegerDaol
            );
        }

        int index = list.getFirstVisiblePosition();
        View v = list.getChildAt(0);
        int top = (v == null) ? -1 : v.getTop();
        list.setAdapter(adapter);
        list.setSelectionFromTop(index, top);
        adapter.notifyDataSetChanged();
    }


    void MallIdSelection(){
        ArrayList<FavouriteCentersModel> TITLES_Centers = GlobelOffersNews.TITLES_centers;
        if (TITLES_Centers == null || TITLES_Centers.size() == 0) {
            TITLES_Centers = CentersCacheManager.getAllCenters(context);
        }
        String selectedCenter = GlobelOffersNews.TITLES.get(position).trim();
        if (selectedCenter.equals("All")){
            MainMenuConstants.SELECTED_MALL_PLACE_ID = "";
        }
        for (FavouriteCentersModel center : TITLES_Centers) {
            if (center.isIsfav() && center.getName().trim().equals(selectedCenter)) {
                MainMenuConstants.SELECTED_MALL_PLACE_ID = center.getMallPlaceId();
            }

        }
    }
}

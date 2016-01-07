package com.mallapp.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.content.Context;

import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.FavouriteCenters;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.MallActivitiesModel;
import com.mallapp.Model.Offers_News;
import com.mallapp.SharedPreferences.SharedPreferenceFavourites;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.cache.CentersCacheManager;
import com.mallapp.utils.GlobelOffersNews;
import com.mallapp.utils.Log;

public class FavouriteCentersFiltration {


    public static ArrayList<String> getFavCenterTITLES(Context context) {
        ArrayList<String> TITLES = SharedPreferenceFavourites.getFavouritesList(context);
        if (TITLES == null || TITLES.size() == 0) {
            String value = Offers_News_Constants.AUDIENCE_FILTER_ALL;
            SharedPreferenceFavourites.saveFavouritesArray(context, value);
            ArrayList<FavouriteCentersModel> TITLES_Centers = CentersCacheManager.getAllCenters(context);
            GlobelOffersNews.TITLES_centers = TITLES_Centers;

            for (FavouriteCentersModel centers : TITLES_Centers) {
                if (centers.isIsfav()) {
                    value = centers.getName();
                    SharedPreferenceFavourites.saveFavouritesArray(context, value);
                }
            }
            TITLES = SharedPreferenceFavourites.getFavouritesList(context);
        }
        GlobelOffersNews.TITLES = TITLES;
        return TITLES;
    }

    public static ArrayList<String> getFavTITLES(Context context){
        ArrayList<String> TITLES = new ArrayList<>();
        TITLES.add(Offers_News_Constants.AUDIENCE_FILTER_ALL);
        ArrayList<FavouriteCentersModel> selectedCenters = new ArrayList<>();
        try {
            selectedCenters = CentersCacheManager.readSelectedObjectList(context);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            StringBuilder sb = new StringBuilder();
            for (FavouriteCentersModel fv:selectedCenters) {
                TITLES.add(fv.getName());
            }
        GlobelOffersNews.TITLES = TITLES;
        return TITLES;
    }

    public static HashMap<String, ArrayList<MallActivitiesModel>> filterFavouriteOffersCategory(ArrayList<MallActivitiesModel> favourite_shop_List) {

        HashMap<String, ArrayList<MallActivitiesModel>> mainDictionary = new HashMap<String, ArrayList<MallActivitiesModel>>();
        ArrayList<String> mainSectionArray = new ArrayList<String>();
        String current_section_header = null;
        ArrayList<MallActivitiesModel> shop_list = null;

        favourite_shop_List = sortOffersListCategory(favourite_shop_List);

        for (int i = 0; i < favourite_shop_List.size(); i++) {
            MallActivitiesModel rest_obj = favourite_shop_List.get(i);

            if (rest_obj != null
                    && rest_obj.getEntityType().equals(Offers_News_Constants.ENTITY_TYPE_OFFER)) {

                String shop_category = rest_obj.getMallName();
                if (shop_category != null && shop_category.length() > 0) {

                    if (current_section_header == null || current_section_header.length() == 0)
                        current_section_header = shop_category;

                    if (mainSectionArray.size() > 0
                            && mainSectionArray.contains(shop_category)
                            && current_section_header.equals(shop_category)) {

                        shop_list.add(rest_obj);
                        if (i + 1 == favourite_shop_List.size()) {
                            Log.e("", "shop_list of " + current_section_header + " = " + shop_list.size());
                            mainDictionary.put(current_section_header, shop_list);

                        }

                    } else if (!current_section_header.equals(shop_category)) {

                        Log.e("", "shop_list of " + current_section_header + " = " + shop_list.size());
                        mainDictionary.put(current_section_header, shop_list);

                        shop_list = new ArrayList<MallActivitiesModel>();
                        shop_list.add(rest_obj);
                        mainSectionArray.add(shop_category);
                        current_section_header = shop_category;

                        if (i + 1 == favourite_shop_List.size()) {
                            Log.e("", "shop_list of " + current_section_header + " = " + shop_list.size());
                            mainDictionary.put(current_section_header, shop_list);
                        }
                    } else {
                        shop_list = new ArrayList<MallActivitiesModel>();
                        shop_list.add(rest_obj);
                        mainSectionArray.add(shop_category);
                        if (i + 1 == favourite_shop_List.size()) {
                            Log.e("", "shop_list of " + current_section_header + " = " + shop_list.size());
                            mainDictionary.put(current_section_header, shop_list);
                        }
                    }
                }
            }
        }
        GlobelOffersNews.header_section_offer = mainSectionArray;
        Log.e("", "offer mainSectionArray      " + mainSectionArray.size());
        Log.e("", "offer mainDictionary........." + mainDictionary.size());
        return mainDictionary;
    }


    public static HashMap<String, ArrayList<MallActivitiesModel>> filterFavouriteNewsCategory(ArrayList<MallActivitiesModel> favourite_shop_List) {

        HashMap<String, ArrayList<MallActivitiesModel>> mainDictionary
                = new HashMap<String, ArrayList<MallActivitiesModel>>();

        ArrayList<String> mainSectionArray = new ArrayList<String>();
        String current_section_header = null;
        ArrayList<MallActivitiesModel> shop_list = new ArrayList<>();

        favourite_shop_List = sortOffersListCategory(favourite_shop_List);
        for (int i = 0; i < favourite_shop_List.size(); i++) {
            MallActivitiesModel rest_obj = favourite_shop_List.get(i);

            if (rest_obj != null
                    && rest_obj.getEntityType().equals(Offers_News_Constants.ENTITY_TYPE_NEWS)) {

                Log.e("", "offer shop category   ......." + rest_obj.getMallName() + " .. rest_obj.getType()...." + rest_obj.getEntityType());

                String shop_category = rest_obj.getMallName();

                if (shop_category != null && shop_category.length() > 0) {
                    if (current_section_header == null || current_section_header.length() == 0)
                        current_section_header = shop_category;

                    if (mainSectionArray.size() > 0
                            && mainSectionArray.contains(shop_category)
                            && current_section_header.equals(shop_category)) {

                        shop_list.add(rest_obj);
                        Log.e("", "shop_list of 1...");
                        if (i + 1 == favourite_shop_List.size()) {
                            Log.e("", "shop_list of 1..." + current_section_header + " = " + shop_list.size());
                            mainDictionary.put(current_section_header, shop_list);
                        }

                    } else if (!current_section_header.equals(shop_category)) {
                        Log.e("", "shop_list of 2 ..." + current_section_header + " = " + shop_list.size());
                        mainDictionary.put(current_section_header, shop_list);

                        shop_list = new ArrayList<MallActivitiesModel>();
                        shop_list.add(rest_obj);
                        mainSectionArray.add(shop_category);
                        current_section_header = shop_category;

                        if (i + 1 == favourite_shop_List.size()) {
                            Log.e("", "shop_list of 3 ... " + current_section_header + " = " + shop_list.size());
                            mainDictionary.put(current_section_header, shop_list);
                        }
                    } else {
                        shop_list = new ArrayList<MallActivitiesModel>();
                        Log.e("", "shop_list of 4...");
                        shop_list.add(rest_obj);
                        mainSectionArray.add(shop_category);
                        if (i + 1 == favourite_shop_List.size()) {
                            Log.e("", "shop_list of 4 ... " + current_section_header + " = " + shop_list.size());
                            mainDictionary.put(current_section_header, shop_list);
                        }
                    }
                }
            } else {
                if (i + 1 == favourite_shop_List.size() && shop_list.size() > 0) {
                    Log.e("", "shop_list of 5 else case ... " + current_section_header + " = " + shop_list.size());
                    mainDictionary.put(current_section_header, shop_list);
                }
            }
        }
        GlobelOffersNews.header_section_news = mainSectionArray;
//		Log.e("", "news mainSectionArray      "+ mainSectionArray.size());
//		Log.e("", "news mainDictionary........."+ mainDictionary.size());
        return mainDictionary;
    }


    private static ArrayList<MallActivitiesModel> sortOffersListCategory(ArrayList<MallActivitiesModel> favourite_shop_List) {

        Collections.sort(favourite_shop_List, new Comparator<MallActivitiesModel>() {
            @Override
            public int compare(MallActivitiesModel lhs, MallActivitiesModel rhs) {
                if (lhs.getEntityName()!=null && rhs.getEntityName()!=null)
                return lhs.getEntityName().compareTo(rhs.getEntityName());
//                return lhs.getShop_catagory().compareTo(rhs.getShop_catagory());
                return 1;
            }
        });
        return favourite_shop_List;
    }

}

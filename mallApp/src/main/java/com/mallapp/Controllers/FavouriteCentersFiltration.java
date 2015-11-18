package com.mallapp.Controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.content.Context;

import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.FavouriteCenters;
import com.mallapp.Model.FavouriteCentersModel;
import com.mallapp.Model.Offers_News;
import com.mallapp.SharedPreferences.SharedPreferenceFavourites;
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


    public static HashMap<String, ArrayList<Offers_News>> filterFavouriteOffersCategory(ArrayList<Offers_News> favourite_shop_List) {

        HashMap<String, ArrayList<Offers_News>> mainDictionary = new HashMap<String, ArrayList<Offers_News>>();
        ArrayList<String> mainSectionArray = new ArrayList<String>();
        String current_section_header = null;
        ArrayList<Offers_News> shop_list = null;

        favourite_shop_List = sortOffersListCategory(favourite_shop_List);

        for (int i = 0; i < favourite_shop_List.size(); i++) {
            Offers_News rest_obj = favourite_shop_List.get(i);

            if (rest_obj != null
                    && rest_obj.getType().equals(Offers_News_Constants.AUDIENCE_FILTER_OFFERS)) {

                String shop_category = rest_obj.getShop_catagory();
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

                        shop_list = new ArrayList<Offers_News>();
                        shop_list.add(rest_obj);
                        mainSectionArray.add(shop_category);
                        current_section_header = shop_category;

                        if (i + 1 == favourite_shop_List.size()) {
                            Log.e("", "shop_list of " + current_section_header + " = " + shop_list.size());
                            mainDictionary.put(current_section_header, shop_list);
                        }
                    } else {
                        shop_list = new ArrayList<Offers_News>();
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


    public static HashMap<String, ArrayList<Offers_News>> filterFavouriteNewsCategory(ArrayList<Offers_News> favourite_shop_List) {

        HashMap<String, ArrayList<Offers_News>> mainDictionary
                = new HashMap<String, ArrayList<Offers_News>>();

        ArrayList<String> mainSectionArray = new ArrayList<String>();
        String current_section_header = null;
        ArrayList<Offers_News> shop_list = null;

        favourite_shop_List = sortOffersListCategory(favourite_shop_List);
        for (int i = 0; i < favourite_shop_List.size(); i++) {
            Offers_News rest_obj = favourite_shop_List.get(i);

            if (rest_obj != null
                    && rest_obj.getType().equals(Offers_News_Constants.AUDIENCE_FILTER_NEWS)) {

                Log.e("", "offer shop category   ......." + rest_obj.getShop_catagory() + " .. rest_obj.getType()...." + rest_obj.getType());

                String shop_category = rest_obj.getShop_catagory();

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

                        shop_list = new ArrayList<Offers_News>();
                        shop_list.add(rest_obj);
                        mainSectionArray.add(shop_category);
                        current_section_header = shop_category;

                        if (i + 1 == favourite_shop_List.size()) {
                            Log.e("", "shop_list of 3 ... " + current_section_header + " = " + shop_list.size());
                            mainDictionary.put(current_section_header, shop_list);
                        }
                    } else {
                        shop_list = new ArrayList<Offers_News>();
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


    private static ArrayList<Offers_News> sortOffersListCategory(ArrayList<Offers_News> favourite_shop_List) {

        Collections.sort(favourite_shop_List, new Comparator<Offers_News>() {
            @Override
            public int compare(Offers_News lhs, Offers_News rhs) {
                return lhs.getShop_catagory().compareTo(rhs.getShop_catagory());
            }
        });
        return favourite_shop_List;
    }

}

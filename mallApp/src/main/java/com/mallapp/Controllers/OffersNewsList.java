package com.mallapp.Controllers;

import android.content.Context;

import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Constants.Offers_News_Constants;
import com.mallapp.Model.Offers_News;
import com.mallapp.View.MallApp_Application;
import com.mallapp.cache.AppCacheManager;
import com.mallapp.utils.Log;

import java.io.IOException;
import java.util.ArrayList;

public class OffersNewsList {
	
	static ArrayList<Offers_News> offer_objects =  new ArrayList<Offers_News>();
	
	public static void saveOffersNewsData(Context context) {
		//  add data in list and save
		offer_objects =  new ArrayList<Offers_News>();
		Offers_News offer= new Offers_News();

		offer= new Offers_News();
		offer.setId(0);
		offer.setCenter_name("Lyngby Storcenter");
		offer.setTitle("Cool gear til ham");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_01");
		offer.setShop_catagory("Convenience");
		offer.setShop_logo_image("shop_logo1");
		offer.setShop_name("A Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_NEWS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(1);
		offer.setCenter_name("Centrum");
		offer.setTitle("En rigtig slipseknude");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_02");
		offer.setShop_catagory("Gifts");
		offer.setShop_logo_image("shop_logo2");
		offer.setShop_name("B Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_NEWS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(2);
		offer.setCenter_name("Waves");
		offer.setTitle("Vedh�ng til julen");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_03");
		offer.setShop_catagory("Hobby");
		offer.setShop_logo_image("shop_logo3");
		offer.setShop_name("C Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_NEWS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(3);
		offer.setCenter_name("Amager Centret");
		offer.setTitle("Bedstes gaver");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_04");
		offer.setShop_catagory("Jewelry");
		offer.setShop_logo_image("shop_logo4");
		offer.setShop_name("D Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_NEWS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(4);
		offer.setCenter_name("Svedige julegaver");
		offer.setTitle("Svedige julegaver");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_05");
		offer.setShop_catagory("Fast Food");
		offer.setShop_logo_image("shop_logo5");
		offer.setShop_name("E Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_NEWS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(5);
		offer.setCenter_name("Athletic Apparel");
		offer.setTitle("Det er helt sort");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_06");
		offer.setShop_catagory("Mens Apparel");
		offer.setShop_logo_image("shop_logo6");
		offer.setShop_name("F Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_NEWS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(6);
		offer.setCenter_name("Ballerup Centret");
		offer.setTitle("Den lille sorte");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_07");
		offer.setShop_catagory("Childrens Apparel");
		offer.setShop_logo_image("shop_logo7");
		offer.setShop_name("G Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_NEWS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(7);
		offer.setCenter_name("Galleri K");
		offer.setTitle("Blik for briller?");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_08");
		offer.setShop_catagory("Art Gallery");
		offer.setShop_logo_image("shop_logo8");
		offer.setShop_name("H Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_NEWS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(8);
		offer.setCenter_name("Waterfront");
		offer.setTitle("Ren fork�lelse");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_01");
		offer.setShop_catagory("Health Beauty");
		offer.setShop_logo_image("shop_logo1");
		offer.setShop_name("I Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_NEWS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(9);
		offer.setCenter_name("Amager Centret");
		offer.setTitle("Hip Hip Hurra..");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_04");
		offer.setShop_catagory("Music Electronics");
		offer.setShop_logo_image("shop_logo2");
		offer.setShop_name("J Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_NEWS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(10);
		offer.setCenter_name("Lyngby Storcenter");
		offer.setTitle("Hip Hip Hurra..");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_03");
		offer.setShop_catagory("Arts Crafts");
		offer.setShop_logo_image("shop_logo3");
		offer.setShop_name("K Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_NEWS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(11);
		offer.setCenter_name("Galleri K");
		offer.setTitle("Hip Hip Hurra..");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_02");
		offer.setShop_catagory("Home Furnishings");
		offer.setShop_logo_image("shop_logo4");
		offer.setShop_name("L Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_NEWS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(12);
		offer.setCenter_name("R�dovre Centrum");
		offer.setTitle("Trendy og maskulin");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_05");
		offer.setShop_catagory("Arts Crafts");
		offer.setShop_logo_image("shop_logo5");
		offer.setShop_name("M Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_OFFERS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(13);
		offer.setCenter_name("Lyngby Storcenter");
		offer.setTitle("Trendy og maskulin");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_01");
		offer.setShop_catagory("Department Stores");
		offer.setShop_logo_image("shop_logo6");
		offer.setShop_name("N Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_OFFERS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(14);
		offer.setCenter_name("R�dovre Centrum");
		offer.setTitle("Trendy og maskulin");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_02");
		offer.setShop_catagory("Health Beauty");
		offer.setShop_logo_image("shop_logo7");
		offer.setShop_name("O Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_OFFERS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(15);
		offer.setCenter_name("Waves");
		offer.setTitle("Trendy og maskulin");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_03");
		offer.setShop_catagory("Luggage");
		offer.setShop_logo_image("shop_logo8");
		offer.setShop_name("P Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_OFFERS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(16);
		offer.setCenter_name("Amager Centret");
		offer.setTitle("Svedige julegaver");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_04");
		offer.setShop_catagory("Books Stationery");
		offer.setShop_logo_image("shop_logo1");
		offer.setShop_name("Q Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_OFFERS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(17);
		offer.setCenter_name("Athletic Apparel");
		offer.setTitle("Svedige julegaver");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_05");
		offer.setShop_catagory("Mens Apparel");
		offer.setShop_logo_image("shop_logo2");
		offer.setShop_name("R Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_OFFERS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(18);
		offer.setCenter_name("Ballerup Centret");
		offer.setTitle("Efter�rsl�b for m�nd");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_06");
		offer.setShop_catagory("Department Stores");
		offer.setShop_logo_image("shop_logo3");
		offer.setShop_name("S Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_OFFERS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(19);
		offer.setCenter_name("Galleri K");
		offer.setTitle("Efter�rsl�b for m�nd");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_07");
		offer.setShop_catagory("Art Gallery");
		offer.setShop_logo_image("shop_logo4");
		offer.setShop_name("T Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_OFFERS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		offer= new Offers_News();
		offer.setId(20);
		offer.setTitle("Vedh�ng til julen");
		offer.setCenter_name("Waterfront");
		offer.setDetail("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		offer.setHeadline("This is very good offer so you can save your money etc...");
		offer.setImage("img_08");
		offer.setShop_catagory("Convenience");
		offer.setShop_logo_image("shop_logo5");
		offer.setShop_name("U Shop");
		offer.setType(Offers_News_Constants.AUDIENCE_FILTER_OFFERS);
		offer.setBookmark(false);
		offer.setFav(false);
		offer_objects.add(offer);
		
		Log.e("offers news list class", ""+offer_objects.size());
		
		if(offer_objects.size()>0 && offer_objects != null){
			
			writeOffersNews(context, offer_objects);
		}
	}
	
	
	public static void writeOffersNews(Context context, ArrayList<Offers_News> offer_objects){
		try{
			AppCacheManager.writeObjectList(context, offer_objects);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.w(MallApp_Application.TAG, "write:"+offer_objects.size());		
	}
	
	public static ArrayList<Offers_News> readOffersNews(Context context){
		try {
			offer_objects = AppCacheManager.readObjectList(context);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.w(MallApp_Application.TAG, "read:"+offer_objects.size());
		return offer_objects;
	}
	
	
	
	public static ArrayList<Offers_News> readOffersNewsCentered(Context context){
		try {
			offer_objects = AppCacheManager.readObjectListCentered(context , MainMenuConstants.SELECTED_CENTER_NAME);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.w(MallApp_Application.TAG, "read:"+offer_objects.size());
		return offer_objects;
	}

}

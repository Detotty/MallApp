package com.mallapp.Controllers;

import android.content.Context;

import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Model.Restaurant;
import com.mallapp.Model.RestaurantMenu;
import com.mallapp.View.MallApp_Application;
import com.mallapp.cache.RestaurantCacheManager;
import com.mallapp.utils.Log;

import java.io.IOException;
import java.util.ArrayList;


public class RestaurantList {

	static ArrayList<Restaurant> rest_objects = new ArrayList<Restaurant>();
	static ArrayList<RestaurantMenu> rest_menu_objects= new ArrayList<RestaurantMenu>();
	
	public static void saveRestaurantData(Context context) {
		rest_objects =  new ArrayList<Restaurant>();
		Restaurant restaurent= new Restaurant();
		
		restaurent= new Restaurant();
		restaurent.setId(0);
		restaurent.setName("A Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("A");
		restaurent.setCenter_name("Ballerup Centret");
		restaurent.setCatagory("Chinese");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 1");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo1");
		restaurent.setRest_image("img_05");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(1);
		restaurent.setName("AEEXA PHILLY STEAK");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("A");
		restaurent.setCenter_name("Lyngby Storcenter");
		restaurent.setCatagory("Continental");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 1");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo2");
		restaurent.setRest_image("img_01");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(2);
		restaurent.setName("AEEXA PHILLY STEAK");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("A");
		restaurent.setCenter_name("Lyngby Storcenter");
		restaurent.setCatagory("Sea Food");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 1");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo3");
		restaurent.setRest_image("img_02");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(8);
		restaurent.setName("BROW ART 23");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("B");
		restaurent.setCenter_name("Lyngby Storcenter");
		restaurent.setCatagory("Fast Food");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 3");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo8");
		restaurent.setRest_image("img_08");
		rest_objects.add(restaurent);
		
		
		restaurent= new Restaurant();
		restaurent.setId(15);
		restaurent.setName("E Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("E");
		restaurent.setCenter_name("Lyngby Storcenter");
		restaurent.setCatagory("Italian");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 2");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo4");
		restaurent.setRest_image("img_04");
		rest_objects.add(restaurent);
		
		
		restaurent= new Restaurant();
		restaurent.setId(22);
		restaurent.setName("F Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("F");
		restaurent.setCenter_name("Lyngby Storcenter");
		restaurent.setCatagory("Chinese");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 4");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo5");
		restaurent.setRest_image("img_04");
		rest_objects.add(restaurent);
		
		
		restaurent= new Restaurant();
		restaurent.setId(23);
		restaurent.setName("G Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("G");
		restaurent.setCenter_name("Lyngby Storcenter");
		restaurent.setCatagory("Continental");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 4");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo7");
		restaurent.setRest_image("img_02");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(24);
		restaurent.setName("H Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("H");
		restaurent.setCenter_name("Lyngby Storcenter");
		restaurent.setCatagory("Sea Food");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 4");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo6");
		restaurent.setRest_image("img_04");
		rest_objects.add(restaurent);
		
		
		restaurent= new Restaurant();
		restaurent.setId(3);
		restaurent.setName("AEROPOSTALE");
		restaurent.setRest_name_first_alpha("A");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setCenter_name(" Centrum");
		restaurent.setCatagory("Fast Food");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 2");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo3");
		restaurent.setRest_image("img_03");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(4);
		restaurent.setName("ALMAAS JEWELERS");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("A");
		restaurent.setCenter_name("R�dovre Centrum");
		restaurent.setCatagory("Italian");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 2");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo4");
		restaurent.setRest_image("img_04");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(5);
		restaurent.setName("AMC THEATRES");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("A");
		restaurent.setCenter_name("Waves");
		restaurent.setCatagory("Continental");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 2");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo5");
		restaurent.setRest_image("img_05");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(6);
		restaurent.setName("B AMERICAN EAGLE OUTFITTERS");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("B");
		restaurent.setCenter_name("Amager Centret");
		restaurent.setCatagory("Sea Food");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 3");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo6");
		restaurent.setRest_image("img_06");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(7);
		restaurent.setName("BIG TYME AIRBRUSH");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("B");
		restaurent.setCenter_name("Athletic Apparel");
		restaurent.setCatagory("Fast Food");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 2");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo7");
		restaurent.setRest_image("img_07");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(9);
		restaurent.setName("CANCUN Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("C");
		restaurent.setCenter_name("R�dovre Centrum");
		restaurent.setCatagory("Italian");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 4");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo8");
		restaurent.setRest_image("img_05");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(10);
		restaurent.setName("STEAK Restaurant ");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("S");
		restaurent.setCenter_name("Amager Centret");
		restaurent.setCatagory("Fast Food");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 1");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo5");
		restaurent.setRest_image("img_04");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(11);
		restaurent.setName("Afghani Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("A");
		restaurent.setCenter_name("Waves");
		restaurent.setCatagory("Continental");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 2");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo3");
		restaurent.setRest_image("img_02");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(12);
		restaurent.setName("BBQ Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("B");
		restaurent.setCenter_name("Athletic Apparel");
		restaurent.setCatagory("Italian");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 3");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo7");
		restaurent.setRest_image("img_05");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(13);
		restaurent.setName("Chinies Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("C");
		restaurent.setCenter_name("Galleri K");
		restaurent.setCatagory("Chinese");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 4");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo6");
		restaurent.setRest_image("img_05");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(14);
		restaurent.setName("Hot dog Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("D");
		restaurent.setCenter_name("Waterfront");
		restaurent.setCatagory("Continental");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 1");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo4");
		restaurent.setRest_image("img_05");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(16);
		restaurent.setName("Elegent Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("E");
		restaurent.setCenter_name("Waves");
		restaurent.setCatagory("Sea Food");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 3");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo3");
		restaurent.setRest_image("img_03");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(17);
		restaurent.setName("Freddy's Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("F");
		restaurent.setCenter_name("Amager Centret");
		restaurent.setCatagory("Fast Food");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 4");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo2");
		restaurent.setRest_image("img_02");
		rest_objects.add(restaurent);
		
		
		restaurent= new Restaurant();
		restaurent.setId(18);
		restaurent.setName("Game Gala Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("G");
		restaurent.setCenter_name("Athletic Apparel");
		restaurent.setCatagory("Italian");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 1");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo1");
		restaurent.setRest_image("img_05");
		rest_objects.add(restaurent);
		
		
		restaurent= new Restaurant();
		restaurent.setId(19);
		restaurent.setName("Hutman Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("H");
		restaurent.setCenter_name("Ballerup Centret");
		restaurent.setCatagory("Continental");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 2");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo7");
		restaurent.setRest_image("img_07");
		rest_objects.add(restaurent);
		
		restaurent= new Restaurant();
		restaurent.setId(20);
		restaurent.setName("Ice bar Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("I");
		restaurent.setCenter_name("Waterfront");
		restaurent.setCatagory("Chinese");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 3");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo6");
		restaurent.setRest_image("img_06");
		rest_objects.add(restaurent);
		
		
		restaurent= new Restaurant();
		restaurent.setId(21);
		restaurent.setName("Jungle Restaurant");
		restaurent.setTitle("Our latest Open Store in this center");
		restaurent.setRest_name_first_alpha("J");
		restaurent.setCenter_name("R�dovre Centrum");
		restaurent.setCatagory("Continental");
		restaurent.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		restaurent.setFloor_no("Floor 4");
		restaurent.setFav(false);
		restaurent.setBookmark(false);
		restaurent.setLogo_image("rest_logo5");
		restaurent.setRest_image("img_05");
		rest_objects.add(restaurent);
		
		Log.e("restaurant list class", ""+rest_objects.size());
		if(rest_objects.size()>0 && rest_objects != null){
			writeRestaurantList(context, rest_objects);
		}
	}
	
	
	public static void writeRestaurantList(Context context, ArrayList<Restaurant> offer_objects){
		try{
			RestaurantCacheManager.writeObjectList(context, offer_objects);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.w(MallApp_Application.TAG, "write:"+offer_objects.size());		
	}
	
	public static ArrayList<Restaurant> readRestaurantList(Context context){
		try {
			rest_objects = RestaurantCacheManager.readObjectList(context, MainMenuConstants.SELECTED_CENTER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.w(MallApp_Application.TAG, "read:"+rest_objects.size());
		return rest_objects;
	}

	
	public static ArrayList<RestaurantMenu> readMenuList() {
		rest_menu_objects = new ArrayList<RestaurantMenu>();
		
		RestaurantMenu restaurent= new RestaurantMenu();
		restaurent.setId(0);
		restaurent.setTitle("Scrambled Cheese Egg");
		restaurent.setPrice("200 kr");
		restaurent.setType("Breakfast");
	
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Cheese Egg");
		restaurent.setPrice("400 kr");
		restaurent.setType("Breakfast");
		rest_menu_objects.add(restaurent);
		
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Fri Egg");
		restaurent.setPrice("100 kr");
		restaurent.setType("Breakfast");
		rest_menu_objects.add(restaurent);
		
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Vegitable Egg");
		restaurent.setPrice("100 kr");
		restaurent.setType("Breakfast");
		rest_menu_objects.add(restaurent);
		
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Scramblggs");
		restaurent.setPrice("300 kr");
		restaurent.setType("Breakfast");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Fries Toast");
		restaurent.setPrice("100 kr");
		restaurent.setType("Breakfast");
		rest_menu_objects.add(restaurent);
		
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Garlic Bread");
		restaurent.setPrice("200 kr");
		restaurent.setType("Breakfast");
		rest_menu_objects.add(restaurent);
		
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Garlic Rice");
		restaurent.setPrice("200 kr");
		restaurent.setType("Lunch");
		rest_menu_objects.add(restaurent);
		
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Cheese Egg Rice");
		restaurent.setPrice("400 kr");
		restaurent.setType("Lunch");
		rest_menu_objects.add(restaurent);
		
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Egg Fried Rice");
		restaurent.setPrice("100 kr");
		restaurent.setType("Lunch");
		rest_menu_objects.add(restaurent);
		
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Vegitable Rice");
		restaurent.setPrice("100 kr");
		restaurent.setType("Lunch");
		rest_menu_objects.add(restaurent);
		
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Scramblggs");
		restaurent.setPrice("300 kr");
		restaurent.setType("Lunch");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Fries Rice");
		restaurent.setPrice("100 kr");
		restaurent.setType("Lunch");
		rest_menu_objects.add(restaurent);
		
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Fries Rice");
		restaurent.setPrice("100 kr");
		restaurent.setType("Dinner");
		rest_menu_objects.add(restaurent);
		
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Fries");
		restaurent.setPrice("100 kr");
		restaurent.setType("Dinner");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Cutlas");
		restaurent.setPrice("100 kr");
		restaurent.setType("Dinner");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Garlic Rice");
		restaurent.setPrice("100 kr");
		restaurent.setType("Dinner");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Egg Fried Rice");
		restaurent.setPrice("100 kr");
		restaurent.setType("Dinner");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Fries Rice");
		restaurent.setPrice("100 kr");
		restaurent.setType("Dinner");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Vegitable Rice");
		restaurent.setPrice("100 kr");
		restaurent.setType("Dinner");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Soup");
		restaurent.setPrice("100 kr");
		restaurent.setType("Dinner");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Chiken Rice");
		restaurent.setPrice("100 kr");
		restaurent.setType("Dinner");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Chinees Rice");
		restaurent.setPrice("100 kr");
		restaurent.setType("Dinner");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Egg Fries Rice");
		restaurent.setPrice("100 kr");
		restaurent.setType("Italina");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Fries");
		restaurent.setPrice("100 kr");
		restaurent.setType("Italina");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Chiken Soup");
		restaurent.setPrice("100 kr");
		restaurent.setType("Italina");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Fries Vegirable Soup");
		restaurent.setPrice("100 kr");
		restaurent.setType("Italina");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Fries Vegirable");
		restaurent.setPrice("100 kr");
		restaurent.setType("Italina");
		rest_menu_objects.add(restaurent);
		
		restaurent= new RestaurantMenu();
		restaurent.setId(1);
		restaurent.setTitle("Fries Rice");
		restaurent.setPrice("100 kr");
		restaurent.setType("Italina");
		rest_menu_objects.add(restaurent);
		
		
		return rest_menu_objects;
		
	}
	
}

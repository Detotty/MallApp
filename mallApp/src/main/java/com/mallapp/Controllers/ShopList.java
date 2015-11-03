package com.mallapp.Controllers;

import android.content.Context;

import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Model.Shops;
import com.mallapp.View.MallApp_Application;
import com.mallapp.cache.ShopCacheManager;
import com.mallapp.utils.Log;

import java.io.IOException;
import java.util.ArrayList;

public class ShopList {

	static ArrayList<Shops> shop_objects = new ArrayList<Shops>();
	
	public static void saveOffersNewsData(Context context) {
		shop_objects =  new ArrayList<Shops>();
		Shops shop= new Shops();
		
		shop= new Shops();
		shop.setId(0);
		shop.setName("A Class");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("A");
		shop.setCenter_name("Ballerup Centret");
		shop.setCatagory("Garments");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 1");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo7");
		shop.setShop_image("img_05");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(1);
		shop.setName("A+ SCHOOL UNIFORMS");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("A");
		shop.setCenter_name("Lyngby Storcenter");
		shop.setCatagory("Garments");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 1");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo1");
		shop.setShop_image("img_01");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(2);
		shop.setName("AEEXA PHILLY STEAK");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("A");
		shop.setCenter_name("Lyngby Storcenter");
		shop.setCatagory("Food");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 1");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo2");
		shop.setShop_image("img_02");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(8);
		shop.setName("BROW ART 23");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("B");
		shop.setCenter_name("Lyngby Storcenter");
		shop.setCatagory("Shoes");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 3");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo8");
		shop.setShop_image("img_08");
		shop_objects.add(shop);
		
		
		shop= new Shops();
		shop.setId(15);
		shop.setName("E SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("E");
		shop.setCenter_name("Lyngby Storcenter");
		shop.setCatagory("Grocery");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 2");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo4");
		shop.setShop_image("img_04");
		shop_objects.add(shop);
		
		
		shop= new Shops();
		shop.setId(22);
		shop.setName("C SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("F");
		shop.setCenter_name("Lyngby Storcenter");
		shop.setCatagory("Grocery");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 4");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo4");
		shop.setShop_image("img_04");
		shop_objects.add(shop);
		
		
		shop= new Shops();
		shop.setId(23);
		shop.setName("G SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("G");
		shop.setCenter_name("Lyngby Storcenter");
		shop.setCatagory("Grocery");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 4");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo4");
		shop.setShop_image("img_04");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(24);
		shop.setName("H SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("H");
		shop.setCenter_name("Lyngby Storcenter");
		shop.setCatagory("Grocery");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 4");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo4");
		shop.setShop_image("img_04");
		shop_objects.add(shop);
		
		
		
		
		
		
		
		
		shop= new Shops();
		shop.setId(3);
		shop.setName("AEROPOSTALE");
		shop.setShop_name_first_alpha("A");
		shop.setTitle("Our latest Open Store in this center");
		shop.setCenter_name("Rdovre Centrum");
		shop.setCatagory("Garments");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 2");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo3");
		shop.setShop_image("img_03");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(4);
		shop.setName("ALMAAS JEWELERS");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("A");
		shop.setCenter_name("R�dovre Centrum");
		shop.setCatagory("Grocery");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 2");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo4");
		shop.setShop_image("img_04");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(5);
		shop.setName("AMC THEATRES");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("A");
		shop.setCenter_name("Waves");
		shop.setCatagory("Grocery");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 2");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo5");
		shop.setShop_image("img_05");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(6);
		shop.setName("B AMERICAN EAGLE OUTFITTERS");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("B");
		shop.setCenter_name("Amager Centret");
		shop.setCatagory("Garments");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 3");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo6");
		shop.setShop_image("img_06");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(7);
		shop.setName("BIG TYME AIRBRUSH");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("B");
		shop.setCenter_name("Athletic Apparel");
		shop.setCatagory("Food");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 2");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo7");
		shop.setShop_image("img_07");
		shop_objects.add(shop);
		
		
		
		shop= new Shops();
		shop.setId(9);
		shop.setName("CANCUN MARKET");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("C");
		shop.setCenter_name("R�dovre Centrum");
		shop.setCatagory("Shoes");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 4");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo6");
		shop.setShop_image("img_05");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(10);
		shop.setName("SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("S");
		shop.setCenter_name("Amager Centret");
		shop.setCatagory("Shoes");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 1");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo5");
		shop.setShop_image("img_04");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(11);
		shop.setName("A SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("A");
		shop.setCenter_name("Waves");
		shop.setCatagory("Shoes");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 2");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo3");
		shop.setShop_image("img_02");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(12);
		shop.setName("B SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("B");
		shop.setCenter_name("Athletic Apparel");
		shop.setCatagory("Shoes");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 3");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo7");
		shop.setShop_image("img_05");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(13);
		shop.setName("C SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("C");
		shop.setCenter_name("Galleri K");
		shop.setCatagory("Grocery");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 4");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo6");
		shop.setShop_image("img_05");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(14);
		shop.setName("D SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("D");
		shop.setCenter_name("Waterfront");
		shop.setCatagory("Grocery");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 1");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo5");
		shop.setShop_image("img_05");
		shop_objects.add(shop);
		
		
		
		shop= new Shops();
		shop.setId(16);
		shop.setName("E SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("E");
		shop.setCenter_name("Waves");
		shop.setCatagory("Grocery");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 3");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo3");
		shop.setShop_image("img_03");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(17);
		shop.setName("F SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("F");
		shop.setCenter_name("Amager Centret");
		shop.setCatagory("Food");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 4");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo2");
		shop.setShop_image("img_02");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(18);
		shop.setName("G SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("G");
		shop.setCenter_name("Athletic Apparel");
		shop.setCatagory("Garments");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 1");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo1");
		shop.setShop_image("img_05");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(19);
		shop.setName("H SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("H");
		shop.setCenter_name("Ballerup Centret");
		shop.setCatagory("Garments");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 2");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo7");
		shop.setShop_image("img_07");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(20);
		shop.setName("I SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("I");
		shop.setCenter_name("Waterfront");
		shop.setCatagory("Food");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 3");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo6");
		shop.setShop_image("img_06");
		shop_objects.add(shop);
		
		shop= new Shops();
		shop.setId(21);
		shop.setName("J SALON");
		shop.setTitle("Our latest Open Store in this center");
		shop.setShop_name_first_alpha("J");
		shop.setCenter_name("R�dovre Centrum");
		shop.setCatagory("Food");
		shop.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only. Quantities limited. Not valid on previous orders. If you choose the ship to home delivery option for your gift card, it will be sent to the address associated with your order and will ship separately. If you choose to pick up your item at the store, your gift card will be delivered after you have picked up the qualifying item.");
		shop.setFloor_no("Floor 4");
		shop.setFav(false);
		shop.setBookmark(false);
		shop.setLogo_image("shop_logo5");
		shop.setShop_image("img_05");
		shop_objects.add(shop);
		
		Log.e("offers news list class", ""+shop_objects.size());
		if(shop_objects.size()>0 && shop_objects != null){
			writeShopsList(context, shop_objects);
		}
	}
	
	
	public static void writeShopsList(Context context, ArrayList<Shops> offer_objects){
		try{
			ShopCacheManager.writeObjectList(context, offer_objects);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.w(MallApp_Application.TAG, "write:"+offer_objects.size());		
	}
	
	public static ArrayList<Shops> readShopsList(Context context){
		try {
			shop_objects = ShopCacheManager.readObjectList(context, MainMenuConstants.SELECTED_CENTER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.w(MallApp_Application.TAG, "read:"+shop_objects.size());
		return shop_objects;
	}
}
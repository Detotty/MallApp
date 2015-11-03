package com.mallapp.Controllers;

import android.content.Context;

import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Model.Services;
import com.mallapp.View.MallApp_Application;
import com.mallapp.cache.ServicesCacheManager;
import com.mallapp.utils.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ServicesList {
	
	static ArrayList<Services> services_objects =  new ArrayList<Services>();
	
	public static void saveServicesList(Context context) {
		services_objects =  new ArrayList<Services>();
		Services services= new Services();

		services.setId(0);
		services.setCenter_name("Lyngby Storcenter");
		services.setTitle("A Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 1");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		
		services= new Services();
		services.setId(1);
		services.setCenter_name(" Centrum");
		services.setTitle("B Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 1");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(2);
		services.setCenter_name("Ballerup Centret");
		services.setTitle("C Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 1");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(3);
		services.setCenter_name("Waves");
		services.setTitle("D Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 1");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(4);
		services.setCenter_name("Amager Centret");
		services.setTitle("E Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 2");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(5);
		services.setCenter_name("Athletic Apparel");
		services.setTitle("F Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 1");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		
		services= new Services();
		services.setId(6);
		services.setCenter_name("Ballerup Centret");
		services.setTitle("G Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 1");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(7);
		services.setCenter_name("R�dovre Centrum");
		services.setTitle("H Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 3");
		services.setPhone_no("11 22 33 44 55");
		services.setOpened(false);
		services.setServices_image("");
		services_objects.add(services);
		
		services= new Services();
		services.setId(8);
		services.setCenter_name("Galleri K");
		services.setTitle("I Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 1");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(9);
		services.setCenter_name("R�dovre Centrum");
		services.setTitle("J Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 4");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(10);
		services.setCenter_name("Waves");
		services.setTitle("K Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 3");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(11);
		services.setCenter_name("Waterfront");
		services.setTitle("L Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 2");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(12);
		services.setCenter_name("Amager Centret");
		services.setTitle("M Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 1");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(13);
		services.setCenter_name("Lyngby Storcenter");
		services.setTitle("N Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 4");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(14);
		services.setCenter_name("R�dovre Centrum");
		services.setTitle("O Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 3");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(15);
		services.setCenter_name("Galleri K");
		services.setTitle("P Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 2");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(16);
		services.setCenter_name("Lyngby Storcenter");
		services.setTitle("Q Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 1");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(17);
		services.setCenter_name("Amager Centret");
		services.setTitle("R Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 4");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(18);
		services.setCenter_name("Svedige julegaver");
		services.setTitle("S Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 3");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(19);
		services.setCenter_name("R�dovre Centrum");
		services.setTitle("T Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 2");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(20);
		services.setCenter_name("Waterfront");
		services.setTitle("U Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 1");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(21);
		services.setCenter_name("Galleri K");
		services.setTitle("V Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 4");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(22);
		services.setCenter_name("Athletic Apparel");
		services.setTitle("W Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 3");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(23);
		services.setCenter_name("Ballerup Centret");
		services.setTitle("X Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 2");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(24);
		services.setCenter_name("Svedige julegaver");
		services.setTitle("Y Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 1");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(25);
		services.setCenter_name("R�dovre Centrum");
		services.setTitle("Aa Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 3");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(26);
		services.setCenter_name("Amager Centret");
		services.setTitle("Bb Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 2");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		services= new Services();
		services.setId(27);
		services.setCenter_name("Waves");
		services.setTitle("Cc Services");
		services.setDescription("Receive a $25 gift card with purchase of the Margaritaville Bahamas Frozen Concoction Maker. Offer available online only.");
		services.setLogo_image("img_01");
		services.setAddress("Services Loung \nButik 222/Plan 1\nArne Jacobsens Alle 12\n2300 Copenhagen");
		services.setFloor_no("Floor 2");
		services.setPhone_no("11 22 33 44 55");
		services.setServices_image("");
		services.setOpened(false);
		services_objects.add(services);
		
		Log.e("services news list class", ""+services_objects.size());
		if(services_objects.size()>0 && services_objects != null){
			writeServices(context, services_objects);
		}
	}

	public static void writeServices(Context context, ArrayList<Services> services_objects){
		try{
			ServicesCacheManager.saveServices(context, services_objects);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.w(MallApp_Application.TAG, "write:"+services_objects.size());		
	}
	
	public static ArrayList<Services> readServicesList(Context context){
		ArrayList<Services> rest_objects = new ArrayList<Services>();
		try {
			rest_objects = ServicesCacheManager.readObjectList(context, MainMenuConstants.SELECTED_CENTER_NAME);
			rest_objects= sortServicesList(rest_objects);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.w(MallApp_Application.TAG, "read:"+rest_objects.size());
		return rest_objects;
	}
	
	private static ArrayList<Services> sortServicesList(ArrayList<Services> favourite_shop_List) {
		Collections.sort(favourite_shop_List, new Comparator<Services>() {
			@Override
			public int compare(Services lhs, Services rhs) {
				return lhs.getTitle().compareTo(rhs.getTitle());
			}
		});
		return favourite_shop_List;
	}
	
	
}
package com.mallapp.Model;

public class NavDrawerItem {
	String navigation_title;
	int navigation_image;

	public NavDrawerItem(String navigation_title, int navigation_image) {
		super();
		this.navigation_title = navigation_title;
		this.navigation_image = navigation_image;
	}
	
	public String getNavigation_title() {
		return navigation_title;
	}
	public void setNavigation_title(String navigation_title) {
		this.navigation_title = navigation_title;
	}
	public int getNavigation_image() {
		return navigation_image;
	}
	public void setNavigation_image(int navigation_image) {
		this.navigation_image = navigation_image;
	}
}
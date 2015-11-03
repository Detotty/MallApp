package com.mallapp.Model;

import java.io.Serializable;

public class FavouriteCenters implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6667100359976390130L;
	int id;
	String center_logo, center_image;
	String center_title, center_location, center_city;
	boolean isfav;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCenter_logo() {
		return center_logo;
	}
	public void setCenter_logo(String center_logo) {
		this.center_logo = center_logo;
	}
	
	public String getCenter_image() {
		return center_image;
	}
	public void setCenter_image(String center_image) {
		this.center_image = center_image;
	}
	
	public String getCenter_title() {
		return center_title;
	}
	public void setCenter_title(String center_title) {
		this.center_title = center_title;
	}
	
	public String getCenter_location() {
		return center_location;
	}
	public void setCenter_location(String center_location) {
		this.center_location = center_location;
	}
	public String getCenter_city() {
		return center_city;
	}
	public void setCenter_city(String center_city) {
		this.center_city = center_city;
	}
	public boolean isIsfav() {
		return isfav;
	}
	public void setIsfav(boolean isfav) {
		this.isfav = isfav;
	}
	
}
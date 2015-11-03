package com.mallapp.Model;

import java.io.Serializable;

public class Restaurant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7119621927912043213L;
	
	int id;
	String title;
	String description;
	String name, rest_name_first_alpha;
	String center_name;
	String logo_image, rest_image, catagory;
	String floor_no;
	boolean isBookmark, isFav;
	
	
	public boolean isBookmark() {
		return isBookmark;
	}
	public void setBookmark(boolean isBookmark) {
		this.isBookmark = isBookmark;
	}
	public boolean isFav() {
		return isFav;
	}
	public void setFav(boolean isFav) {
		this.isFav = isFav;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRest_name_first_alpha() {
		return rest_name_first_alpha;
	}
	public void setRest_name_first_alpha(String rest_name_first_alpha) {
		this.rest_name_first_alpha = rest_name_first_alpha;
	}
	public String getCenter_name() {
		return center_name;
	}
	public void setCenter_name(String center_name) {
		this.center_name = center_name;
	}
	public String getLogo_image() {
		return logo_image;
	}
	public void setLogo_image(String logo_image) {
		this.logo_image = logo_image;
	}
	public String getRest_image() {
		return rest_image;
	}
	public void setRest_image(String rest_image) {
		this.rest_image = rest_image;
	}
	public String getCatagory() {
		return catagory;
	}
	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}
	public String getFloor_no() {
		return floor_no;
	}
	public void setFloor_no(String floor_no) {
		this.floor_no = floor_no;
	}
	

}

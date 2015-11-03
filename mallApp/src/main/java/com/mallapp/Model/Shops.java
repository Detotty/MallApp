package com.mallapp.Model;

import java.io.Serializable;

public class Shops implements Serializable {

	
	private static final long serialVersionUID = -792289423973305542L;
	int id;
	String title;
	String description;
	String name, shop_name_first_alpha;
	String center_name;
	String logo_image, shop_image, catagory;
	String floor_no;

	boolean isBookmark, isFav;

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
	public String getShop_name_first_alpha() {
		return shop_name_first_alpha;
	}
	public void setShop_name_first_alpha(String shop_name_first_alpha) {
		this.shop_name_first_alpha = shop_name_first_alpha;
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
	public String getShop_image() {
		return shop_image;
	}
	public void setShop_image(String shop_image) {
		this.shop_image = shop_image;
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
}

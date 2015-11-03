package com.mallapp.Model;

import java.io.Serializable;


public class Offers_News implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4694768551186151871L;
	int id;
	String image;
	String title;
	String headline;
	String detail;
	String type; // ofer or news  audience filter
	String center_name; // place
	String shop_name, shop_logo_image;
	String shop_catagory;// type of shop? is clothes , shoes etc
	boolean isFav, isBookmark;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCenter_name() {
		return center_name;
	}
	public void setCenter_name(String center_name) {
		this.center_name = center_name;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public String getShop_logo_image() {
		return shop_logo_image;
	}
	public void setShop_logo_image(String shop_logo_image) {
		this.shop_logo_image = shop_logo_image;
	}
	public String getShop_catagory() {
		return shop_catagory;
	}
	public void setShop_catagory(String shop_catagory) {
		this.shop_catagory = shop_catagory;
	}
	public boolean isFav() {
		return isFav;
	}
	public void setFav(boolean isFav) {
		this.isFav = isFav;
	}
	public boolean isBookmark() {
		return isBookmark;
	}
	public void setBookmark(boolean isBookmark) {
		this.isBookmark = isBookmark;
	}

}
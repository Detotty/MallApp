package com.mallapp.Model;

import android.graphics.Bitmap;

public class UserProfile {

	private String name, date_birth, education, location, gender, picture_path, Email, DefaultLocationName, DeviceType;
	private String country, countryCode, phone_no;
	private  boolean push_notification;
	long UnixDob;
	Bitmap imageBitmap;

	

	public UserProfile(String name, String date_birth, String education,
			String location, String gender) {
		super();
		this.name = name;
		this.date_birth = date_birth;
		this.education = education;
		this.location = location;
		this.gender= gender;
	}

	public String getEmail ()
	{
		return Email;
	}

	public void setEmail (String Email)
	{
		this.Email = Email;
	}
	public String getDefaultLocationName ()
	{
		return DefaultLocationName;
	}

	public void setDefaultLocationName (String DefaultLocationName)
	{
		this.DefaultLocationName = DefaultLocationName;
	}

	public String getDeviceType ()
	{
		return DeviceType;
	}

	public void setDeviceType (String DeviceType)
	{
		this.DeviceType = DeviceType;
	}
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getPhone_no() {
		return phone_no;
	}
	
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public UserProfile(String country, String countryCode, String phone_no, boolean push_notification) {
		super();
		this.country = country;
		this.countryCode = countryCode;
		this.phone_no = phone_no;
		this.push_notification= push_notification;
	}
	
	public UserProfile() {
		super();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate_birth() {
		return date_birth;
	}
	
	public void setDate_birth(String date_birth) {
		this.date_birth = date_birth;
	}
	
	public String getEducation() {
		return education;
	}
	
	public void setEducation(String education) {
		this.education = education;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getPicture_path() {
		return picture_path;
	}
	
	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public boolean isPush_notification() {
		return push_notification;
	}
	
	public void setPush_notification(boolean push_notification) {
		this.push_notification = push_notification;
	}

	public long getUnixDob ()
	{
		return UnixDob;
	}

	public void setUnixDob (long UnixDob)
	{
		this.UnixDob = UnixDob;
	}

	public Bitmap getImageBitmap(){
		return imageBitmap;
	}

	public void setImageBitmap(Bitmap bitmap){
		this.imageBitmap = bitmap;
	}
}
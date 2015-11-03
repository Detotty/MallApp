package com.mallapp.Model;

import android.graphics.Bitmap;

public class Parking {
	
	private String mLabel;
    private String mIcon;
    private Bitmap bitmap;
    private Double mLatitude;
    private Double mLongitude;
    
    
	public String getmLabel() {
		return mLabel;
	}
	public void setmLabel(String mLabel) {
		this.mLabel = mLabel;
	}
	public String getmIcon() {
		return mIcon;
	}
	public void setmIcon(String mIcon) {
		this.mIcon = mIcon;
	}
	public Double getmLatitude() {
		return mLatitude;
	}
	public void setmLatitude(Double mLatitude) {
		this.mLatitude = mLatitude;
	}
	public Double getmLongitude() {
		return mLongitude;
	}
	public void setmLongitude(Double mLongitude) {
		this.mLongitude = mLongitude;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}


	
}
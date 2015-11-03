package com.mallapp.Model;

import java.io.Serializable;

public class Interst_Selection implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5338581095986714242L;
	int id;
	String interest_title;
	boolean isInterested;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInterest_title() {
		return interest_title;
	}
	public void setInterest_title(String interest_title) {
		this.interest_title = interest_title;
	}
	public boolean isInterested() {
		return isInterested;
	}
	public void setInterested(boolean isInterested) {
		this.isInterested = isInterested;
	}
}
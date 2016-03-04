/**
 * 
 */
package com.mallapp.utils;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author VAIO Fit14 E
 *
 */
public class RegisterContact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2771439642143325220L;

	
	@SerializedName("id")
	private String contactId="";
	@SerializedName("number")
	private String number="";
	@SerializedName("sipUsername")
	private String sipUsername="";
	
	
	
	
	/**
	 * @return the contactId
	 */
	public String getContactId() {
		return contactId;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return the sipUsername
	 */
	public String getSipUsername() {
		return sipUsername;
	}
	/**
	 * @param sipUsername the sipUsername to set
	 */
	public void setSipUsername(String sipUsername) {
		this.sipUsername = sipUsername;
	}
	
	
	
	
	
	
}

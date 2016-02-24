package com.mallapp.Model;

import java.util.List;

/**
 * Created by Sharjeel on 24/02/2016.
 */
public class ContactBean {

    private String contactId = "";
    private String displayName;
    private String phoneNumber;
    private String homeNumber;
    private String workNumber;
    private String contactImage;
    private boolean onWaveCall;
    private List<String> numberList = null;

    public ContactBean() {

    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }


    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }


    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    /**
     * @return the homeNumber
     */
    public String getHomeNumber() {
        return homeNumber;
    }


    /**
     * @param homeNumber the homeNumber to set
     */
    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }


    /**
     * @return the workNumber
     */
    public String getWorkNumber() {
        return workNumber;
    }


    /**
     * @param workNumber the workNumber to set
     */
    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    /**
     * @return the contactImage
     */
    public String getContactImage() {
        return contactImage;
    }

    /**
     * @param contactImage the contactImage to set
     */
    public void setContactImage(String contactImage) {
        this.contactImage = contactImage;
    }

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
     * @return the onWaveCall
     */
    public boolean isOnWaveCall() {
        return onWaveCall;
    }

    /**
     * @param onWaveCall the onWaveCall to set
     */
    public void setOnWaveCall(boolean onWaveCall) {
        this.onWaveCall = onWaveCall;
    }

    /**
     * @return the numberList
     */
    public List<String> getNumberList() {
        return numberList;
    }

    /**
     * @param numberList the numberList to set
     */
    public void setNumberList(List<String> numberList) {
        this.numberList = numberList;
    }


}

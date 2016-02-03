package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 2/3/2016.
 */
public class LoyaltyCardModel implements Serializable {

    String UserId;

    String CardTitle;

    String ProviderName;

    String MallId;

    String StoreId;

    String CardHolderName;

    String IssueDate;

    String ExpiryDate;

    String FrontImageUrl;

    String BackImageUrl;

    String ImageMimeType;

    String Barcode;

    String BarcodeType;

    String UserNotes;

    String CreatedUTCDateTime;

    String LastUpdatedUTCDateTime;

    String BarcodeImageURL;

    String FrontBase64ImageString;

    String BacksideBase64ImageString;

    public String getUserId(){
        return UserId;
    }

    public void setUserId (String UserId)
    {
        this.UserId = UserId;
    }

    public String getCardTitle(){
        return CardTitle;
    }

    public void setCardTitle (String CardTitle)
    {
        this.CardTitle = CardTitle;
    }

    public String getProviderName(){
        return ProviderName;
    }

    public void setProviderName (String ProviderName)
    {
        this.ProviderName = ProviderName;
    }

    public String getMallId(){
        return MallId;
    }

    public void setMallId (String MallId)
    {
        this.MallId = MallId;
    }

    public String getStoreId(){
        return StoreId;
    }

    public void setStoreId (String StoreId)
    {
        this.StoreId = StoreId;
    }

    public String getCardHolderName(){
        return CardHolderName;
    }

    public void setCardHolderName (String CardHolderName)
    {
        this.CardHolderName = CardHolderName;
    }

    public String getIssueDate(){
        return IssueDate;
    }

    public void setIssueDate (String IssueDate)
    {
        this.IssueDate = IssueDate;
    }

    public String getExpiryDate(){
        return ExpiryDate;
    }

    public void setExpiryDate (String ExpiryDate)
    {
        this.ExpiryDate = ExpiryDate;
    }

    public String getFrontImageUrl(){
        return FrontImageUrl;
    }

    public void setFrontImageUrl (String FrontImageUrl)
    {
        this.FrontImageUrl = FrontImageUrl;
    }

    public String getBackImageUrl(){
        return BackImageUrl;
    }

    public void setBackImageUrl (String BackImageUrl)
    {
        this.BackImageUrl = BackImageUrl;
    }

    public String getImageMimeType(){
        return ImageMimeType;
    }

    public void setImageMimeType (String ImageMimeType)
    {
        this.ImageMimeType = ImageMimeType;
    }

    public String getBarcode(){
        return Barcode;
    }

    public void setBarcode (String Barcode)
    {
        this.Barcode = Barcode;
    }

    public String getBarcodeType(){
        return BarcodeType;
    }

    public void setBarcodeType (String BarcodeType)
    {
        this.BarcodeType = BarcodeType;
    }

    public String getUserNotes(){
        return UserNotes;
    }

    public void setUserNotes (String UserNotes)
    {
        this.UserNotes = UserNotes;
    }

    public String getCreatedUTCDateTime(){
        return CreatedUTCDateTime;
    }

    public void setCreatedUTCDateTime (String CreatedUTCDateTime)
    {
        this.CreatedUTCDateTime = CreatedUTCDateTime;
    }

    public String getLastUpdatedUTCDateTime(){
        return LastUpdatedUTCDateTime;
    }

    public void setLastUpdatedUTCDateTime (String LastUpdatedUTCDateTime)
    {
        this.LastUpdatedUTCDateTime = LastUpdatedUTCDateTime;
    }

    public String getBarcodeImageURL(){
        return BarcodeImageURL;
    }

    public void setBarcodeImageURL (String BarcodeImageURL)
    {
        this.BarcodeImageURL = BarcodeImageURL;
    }

    public String getFrontBase64ImageString(){
        return FrontBase64ImageString;
    }

    public void setFrontBase64ImageString (String FrontBase64ImageString)
    {
        this.FrontBase64ImageString = FrontBase64ImageString;
    }

    public String getBacksideBase64ImageString(){
        return BacksideBase64ImageString;
    }

    public void setBacksideBase64ImageString (String BacksideBase64ImageString)
    {
        this.BacksideBase64ImageString = BacksideBase64ImageString;
    }

}

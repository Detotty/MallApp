package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 12/15/2015.
 */
public class ServicesModel implements Serializable
{
    private String FacilityImageURL;

    private String BriefText;

    private String Phone;

    private String MapImageURL;

    private String Address;

    private String Id;

    private String FacilityName;

    private String FacilityType;

    private String Floor;

    boolean isOpened;


    public String getFacilityImageURL ()
    {
        return FacilityImageURL;
    }

    public void setFacilityImageURL (String FacilityImageURL)
    {
        this.FacilityImageURL = FacilityImageURL;
    }

    public String getBriefText ()
    {
        return BriefText;
    }

    public void setBriefText (String BriefText)
    {
        this.BriefText = BriefText;
    }

    public String getPhone ()
    {
        return Phone;
    }

    public void setPhone (String Phone)
    {
        this.Phone = Phone;
    }

    public String getMapImageURL ()
{
    return MapImageURL;
}

    public void setMapImageURL (String MapImageURL)
    {
        this.MapImageURL = MapImageURL;
    }

    public String getAddress ()
    {
        return Address;
    }

    public void setAddress (String Address)
    {
        this.Address = Address;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getFacilityName ()
{
    return FacilityName;
}

    public void setFacilityName (String FacilityName)
    {
        this.FacilityName = FacilityName;
    }

    public String getFacilityType ()
    {
        return FacilityType;
    }

    public void setFacilityType (String FacilityType)
    {
        this.FacilityType = FacilityType;
    }

    public String getFloor ()
    {
        return Floor;
    }

    public void setFloor (String Floor)
    {
        this.Floor = Floor;
    }

    public boolean isOpened() {

        return isOpened;
    }
    public void setOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [FacilityImageURL = "+FacilityImageURL+", BriefText = "+BriefText+", Phone = "+Phone+", MapImageURL = "+MapImageURL+", Address = "+Address+", Id = "+Id+", FacilityName = "+FacilityName+", FacilityType = "+FacilityType+", Floor = "+Floor+"]";
    }
}
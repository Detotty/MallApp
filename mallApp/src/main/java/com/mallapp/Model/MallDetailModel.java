package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 12/16/2015.
 */
public class MallDetailModel implements Serializable
{
    private String CountryName;

    private String Phone;

    private String ContactPerson;

    private String WebURL;

    private String CityName;

    private String MallPlaceId;

    private String TwitterURL;

    private String MallId;

    private String ZipCode;

    private MallTimingsModel[] MallTimings;

    private String Name;

    private String BriefText;

    private String FacebookURL;

    private String AboutText;

    private String CorporateColor;

    private String Email;

    private String LogoUrl;

    private String Address;

    private String Latitude;

    private String Longitude;

    private String AppBackgroundImageUrl;

    private String PlaceName;

    private String LinkedInURL;

    public String getCountryName ()
    {
        return CountryName;
    }

    public void setCountryName (String CountryName)
    {
        this.CountryName = CountryName;
    }

    public String getPhone ()
    {
        return Phone;
    }

    public void setPhone (String Phone)
    {
        this.Phone = Phone;
    }

    public String getContactPerson ()
    {
        return ContactPerson;
    }

    public void setContactPerson (String ContactPerson)
    {
        this.ContactPerson = ContactPerson;
    }

    public String getWebURL ()
    {
        return WebURL;
    }

    public void setWebURL (String WebURL)
    {
        this.WebURL = WebURL;
    }

    public String getCityName ()
    {
        return CityName;
    }

    public void setCityName (String CityName)
    {
        this.CityName = CityName;
    }

    public String getMallPlaceId ()
    {
        return MallPlaceId;
    }

    public void setMallPlaceId (String MallPlaceId)
    {
        this.MallPlaceId = MallPlaceId;
    }

    public String getTwitterURL ()
    {
        return TwitterURL;
    }

    public void setTwitterURL (String TwitterURL)
    {
        this.TwitterURL = TwitterURL;
    }

    public String getMallId ()
    {
        return MallId;
    }

    public void setMallId (String MallId)
    {
        this.MallId = MallId;
    }

    public String getZipCode ()
    {
        return ZipCode;
    }

    public void setZipCode (String ZipCode)
    {
        this.ZipCode = ZipCode;
    }

    public MallTimingsModel[] getMallTimings ()
    {
        return MallTimings;
    }

    public void setMallTimings (MallTimingsModel[] MallTimings)
    {
        this.MallTimings = MallTimings;
    }

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getBriefText ()
    {
        return BriefText;
    }

    public void setBriefText (String BriefText)
    {
        this.BriefText = BriefText;
    }

    public String getFacebookURL ()
    {
        return FacebookURL;
    }

    public void setFacebookURL (String FacebookURL)
    {
        this.FacebookURL = FacebookURL;
    }

    public String getAboutText ()
    {
        return AboutText;
    }

    public void setAboutText (String AboutText)
    {
        this.AboutText = AboutText;
    }

    public String getCorporateColor ()
    {
        return CorporateColor;
    }

    public void setCorporateColor (String CorporateColor)
    {
        this.CorporateColor = CorporateColor;
    }

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public String getLogoUrl ()
    {
        return LogoUrl;
    }

    public void setLogoUrl (String LogoUrl)
    {
        this.LogoUrl = LogoUrl;
    }

    public String getAddress ()
    {
        return Address;
    }

    public void setAddress (String Address)
    {
        this.Address = Address;
    }

    public String getLatitude ()
    {
        return Latitude;
    }

    public void setLatitude (String Latitude)
    {
        this.Latitude = Latitude;
    }

    public String getLongitude ()
    {
        return Longitude;
    }

    public void setLongitude (String Longitude)
    {
        this.Longitude = Longitude;
    }

    public String getAppBackgroundImageUrl ()
{
    return AppBackgroundImageUrl;
}

    public void setAppBackgroundImageUrl (String AppBackgroundImageUrl)
    {
        this.AppBackgroundImageUrl = AppBackgroundImageUrl;
    }

    public String getPlaceName ()
    {
        return PlaceName;
    }

    public void setPlaceName (String PlaceName)
    {
        this.PlaceName = PlaceName;
    }

    public String getLinkedInURL ()
    {
        return LinkedInURL;
    }

    public void setLinkedInURL (String LinkedInURL)
    {
        this.LinkedInURL = LinkedInURL;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CountryName = "+CountryName+", Phone = "+Phone+", ContactPerson = "+ContactPerson+", WebURL = "+WebURL+", CityName = "+CityName+", MallPlaceId = "+MallPlaceId+", TwitterURL = "+TwitterURL+", MallId = "+MallId+", ZipCode = "+ZipCode+", MallTimings = "+MallTimings+", Name = "+Name+", BriefText = "+BriefText+", FacebookURL = "+FacebookURL+", AboutText = "+AboutText+", CorporateColor = "+CorporateColor+", Email = "+Email+", LogoUrl = "+LogoUrl+", Address = "+Address+", Latitude = "+Latitude+", Longitude = "+Longitude+", AppBackgroundImageUrl = "+AppBackgroundImageUrl+", PlaceName = "+PlaceName+", LinkedInURL = "+LinkedInURL+"]";
    }
}

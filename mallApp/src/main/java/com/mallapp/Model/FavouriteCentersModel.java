package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 11/10/2015.
 */
public class FavouriteCentersModel implements Serializable
{
    private String CountryName;

    private String WebURL;

    private String CityName;

    private String MallPlaceId;

    private String LinkedIn;

    private String TwitterURL;

    private String MallId;

    private String Name;

    private String FacebookURL;

    private String BriefText;

    private String AboutText;

    private String CorporateColor;

    private String LogoUrl;

    private String Address;

    private String Latitude;

    private String Longitude;

    private String AppBackgroundImageUrl;

    private String PlaceName;

    private boolean isfav;

    public String getCountryName ()
    {
        return CountryName;
    }

    public void setCountryName (String CountryName)
    {
        this.CountryName = CountryName;
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

    public String getLinkedIn ()
    {
        return LinkedIn;
    }

    public void setLinkedIn (String LinkedIn)
    {
        this.LinkedIn = LinkedIn;
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

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getFacebookURL ()
    {
        return FacebookURL;
    }

    public void setFacebookURL (String FacebookURL)
    {
        this.FacebookURL = FacebookURL;
    }

    public String getBriefText ()
    {
        return BriefText;
    }

    public void setBriefText (String BriefText)
    {
        this.BriefText = BriefText;
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

    public boolean isIsfav() {
        return isfav;
    }

    public void setIsfav(boolean isfav) {
        this.isfav = isfav;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CountryName = "+CountryName+", WebURL = "+WebURL+", CityName = "+CityName+", MallPlaceId = "+MallPlaceId+", LinkedIn = "+LinkedIn+", TwitterURL = "+TwitterURL+", MallId = "+MallId+", Name = "+Name+", FacebookURL = "+FacebookURL+", BriefText = "+BriefText+", AboutText = "+AboutText+", CorporateColor = "+CorporateColor+", LogoUrl = "+LogoUrl+", Address = "+Address+", Latitude = "+Latitude+", Longitude = "+Longitude+", AppBackgroundImageUrl = "+AppBackgroundImageUrl+", PlaceName = "+PlaceName+"]";
    }
}
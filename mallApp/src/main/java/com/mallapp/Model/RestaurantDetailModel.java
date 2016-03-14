package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 12/11/2015.
 */
public class RestaurantDetailModel implements Serializable
{
    private String Phone;

    private BannerImagesModel[] BannerImages;

    private String WebURL;

    private String ContactPerson;

    private String SiteMapURL;

    private String[] ResturantMenu;

    private RestaurantTimingsModel[] ResturantTimings;

    private String ResturantId;

    private String TwitterURL;

    private RestaurantOffersModel[] RestaurantOffers;

    private String Floor;

    private String LogoURL;

    private String Name;

    private String FacebookURL;

    private String BriefText;

    private String AboutText;

    private String Email;

    private boolean SiteMapActive;

    private String Address;

    private String Latitude;

    private String Longitude;

    private String LinkedInURL;

    private boolean isFav;

    private boolean IsFavorite;

    public String getPhone ()
    {
        return Phone;
    }

    public void setPhone (String Phone)
    {
        this.Phone = Phone;
    }

    public BannerImagesModel[] getBannerImages ()
    {
        return BannerImages;
    }

    public void setBannerImages (BannerImagesModel[] BannerImages)
    {
        this.BannerImages = BannerImages;
    }

    public String getWebURL ()
    {
        return WebURL;
    }

    public void setWebURL (String WebURL)
    {
        this.WebURL = WebURL;
    }

    public String getContactPerson ()
    {
        return ContactPerson;
    }

    public void setContactPerson (String ContactPerson)
    {
        this.ContactPerson = ContactPerson;
    }

    public String getSiteMapURL ()
{
    return SiteMapURL;
}

    public void setSiteMapURL (String SiteMapURL)
    {
        this.SiteMapURL = SiteMapURL;
    }

    public String[] getResturantMenu ()
    {
        return ResturantMenu;
    }

    public void setResturantMenu (String[] ResturantMenu)
    {
        this.ResturantMenu = ResturantMenu;
    }

    public RestaurantTimingsModel[] getResturantTimings ()
    {
        return ResturantTimings;
    }

    public void setResturantTimings (RestaurantTimingsModel[] ResturantTimings)
    {
        this.ResturantTimings = ResturantTimings;
    }

    public String getResturantId ()
    {
        return ResturantId;
    }

    public void setResturantId (String ResturantId)
    {
        this.ResturantId = ResturantId;
    }

    public String getTwitterURL ()
    {
        return TwitterURL;
    }

    public void setTwitterURL (String TwitterURL)
    {
        this.TwitterURL = TwitterURL;
    }

    public RestaurantOffersModel[] getRestaurantOffers ()
    {
        return RestaurantOffers;
    }

    public void setRestaurantOffers (RestaurantOffersModel[] RestaurantOffers)
    {
        this.RestaurantOffers = RestaurantOffers;
    }

    public String getFloor ()
    {
        return Floor;
    }

    public void setFloor (String Floor)
    {
        this.Floor = Floor;
    }

    public String getLogoURL ()
    {
        return LogoURL;
    }

    public void setLogoURL (String LogoURL)
    {
        this.LogoURL = LogoURL;
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

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public boolean getSiteMapActive ()
    {
        return SiteMapActive;
    }

    public void setSiteMapActive (boolean SiteMapActive)
    {
        this.SiteMapActive = SiteMapActive;
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

    public String getLinkedInURL ()
    {
        return LinkedInURL;
    }

    public void setLinkedInURL (String LinkedInURL)
    {
        this.LinkedInURL = LinkedInURL;
    }

    public boolean isFav ()
    {
        return IsFavorite;
    }

    public void setFav (boolean IsFavorite)
    {
        this.IsFavorite = IsFavorite;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Phone = "+Phone+", BannerImages = "+BannerImages+", WebURL = "+WebURL+", ContactPerson = "+ContactPerson+", SiteMapURL = "+SiteMapURL+", ResturantMenu = "+ResturantMenu+", ResturantTimings = "+ResturantTimings+", ResturantId = "+ResturantId+", TwitterURL = "+TwitterURL+", RestaurantOffers = "+RestaurantOffers+", Floor = "+Floor+", LogoURL = "+LogoURL+", Name = "+Name+", FacebookURL = "+FacebookURL+", BriefText = "+BriefText+", AboutText = "+AboutText+", Email = "+Email+", SiteMapActive = "+SiteMapActive+", Address = "+Address+", Latitude = "+Latitude+", Longitude = "+Longitude+", LinkedInURL = "+LinkedInURL+"]";
    }
}
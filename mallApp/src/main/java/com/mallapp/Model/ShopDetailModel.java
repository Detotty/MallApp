package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 11/25/2015.
 */
public class ShopDetailModel implements Serializable
{
    private String Phone;

    private BannerImagesModel[] BannerImages;

    private String WebURL;

    private String TwitterURL;

    private String LogoURL;

    private String FacebookURL;

    private String StoreId;

    private String Latitude;

    private String[] StoreOffers;

    private StoreCategoriesModel[] StoreCategories;

    private StoreTimingsModel[] StoreTimings;

    private String ContactPerson;

    private String SiteMapURL;

    private String ExternalShopId;

    private String ZipCode;

    private String Floor;

    private String Name;

    private String BriefText;

    private String AboutText;

    private String Email;

    private String SiteMapActive;

    private String Address;

    private String Longitude;

    private String LinkedInURL;

    private String[] StorePhones;

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

    public String getTwitterURL ()
    {
        return TwitterURL;
    }

    public void setTwitterURL (String TwitterURL)
    {
        this.TwitterURL = TwitterURL;
    }

    public String getLogoURL ()
    {
        return LogoURL;
    }

    public void setLogoURL (String LogoURL)
    {
        this.LogoURL = LogoURL;
    }

    public String getFacebookURL ()
    {
        return FacebookURL;
    }

    public void setFacebookURL (String FacebookURL)
    {
        this.FacebookURL = FacebookURL;
    }

    public String getStoreId ()
    {
        return StoreId;
    }

    public void setStoreId (String StoreId)
    {
        this.StoreId = StoreId;
    }

    public String getLatitude ()
    {
        return Latitude;
    }

    public void setLatitude (String Latitude)
    {
        this.Latitude = Latitude;
    }

    public String[] getStoreOffers ()
    {
        return StoreOffers;
    }

    public void setStoreOffers (String[] StoreOffers)
    {
        this.StoreOffers = StoreOffers;
    }

    public StoreCategoriesModel[] getStoreCategories ()
    {
        return StoreCategories;
    }

    public void setStoreCategories (StoreCategoriesModel[] StoreCategories)
    {
        this.StoreCategories = StoreCategories;
    }

    public StoreTimingsModel[] getStoreTimings ()
    {
        return StoreTimings;
    }

    public void setStoreTimings (StoreTimingsModel[] StoreTimings)
    {
        this.StoreTimings = StoreTimings;
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

    public String getExternalShopId ()
    {
        return ExternalShopId;
    }

    public void setExternalShopId (String ExternalShopId)
    {
        this.ExternalShopId = ExternalShopId;
    }

    public String getZipCode ()
    {
        return ZipCode;
    }

    public void setZipCode (String ZipCode)
    {
        this.ZipCode = ZipCode;
    }

    public String getFloor ()
    {
        return Floor;
    }

    public void setFloor (String Floor)
    {
        this.Floor = Floor;
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

    public String getSiteMapActive ()
    {
        return SiteMapActive;
    }

    public void setSiteMapActive (String SiteMapActive)
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

    public String[] getStorePhones ()
    {
        return StorePhones;
    }

    public void setStorePhones (String[] StorePhones)
    {
        this.StorePhones = StorePhones;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Phone = "+Phone+", BannerImages = "+BannerImages+", WebURL = "+WebURL+", TwitterURL = "+TwitterURL+", LogoURL = "+LogoURL+", FacebookURL = "+FacebookURL+", StoreId = "+StoreId+", Latitude = "+Latitude+", StoreOffers = "+StoreOffers+", StoreCategories = "+StoreCategories+", StoreTimings = "+StoreTimings+", ContactPerson = "+ContactPerson+", SiteMapURL = "+SiteMapURL+", ExternalShopId = "+ExternalShopId+", ZipCode = "+ZipCode+", Floor = "+Floor+", Name = "+Name+", BriefText = "+BriefText+", AboutText = "+AboutText+", Email = "+Email+", SiteMapActive = "+SiteMapActive+", Address = "+Address+", Longitude = "+Longitude+", LinkedInURL = "+LinkedInURL+", StorePhones = "+StorePhones+"]";
    }
}

package com.mallapp.Model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Sharjeel on 11/25/2015.
 */
public class ShopDetailModel implements Serializable
{
    @DatabaseField(columnName = "Phone")
    private String Phone;

    private BannerImagesModel[] BannerImages;

    @DatabaseField(columnName = "WebURL")
    private String WebURL;

    @DatabaseField(columnName = "TwitterURL")
    private String TwitterURL;

    @DatabaseField(columnName = "LogoURL")
    private String LogoURL;

    @DatabaseField(columnName = "FacebookURL")
    private String FacebookURL;

    @DatabaseField(id = true, columnName = "StoreId")
    private String StoreId;

    @DatabaseField(columnName = "Latitude")
    private String Latitude;

    private StoreCategoriesModel[] StoreCategories;

    private StoreTimingsModel[] StoreTimings;

    @DatabaseField(columnName = "ContactPerson")
    private String ContactPerson;

    @DatabaseField(columnName = "SiteMapURL")
    private String SiteMapURL;

    @DatabaseField(columnName = "ExternalShopId")
    private String ExternalShopId;

    @DatabaseField(columnName = "ZipCode")
    private String ZipCode;

    @DatabaseField(columnName = "Floor")
    private String Floor;

    @DatabaseField(columnName = "Name")
    private String Name;

    @DatabaseField(columnName = "BriefText")
    private String BriefText;

    @DatabaseField(columnName = "AboutText")
    private String AboutText;

    @DatabaseField(columnName = "Email")
    private String Email;

    @DatabaseField(columnName = "SiteMapActive")
    private boolean SiteMapActive;

    @DatabaseField(columnName = "Address")
    private String Address;

    @DatabaseField(columnName = "Longitude")
    private String Longitude;

    @DatabaseField(columnName = "LinkedInURL")
    private String LinkedInURL;

    private String[] StorePhones;

    private StoreOffersModel[] StoreOffers;

    @DatabaseField(columnName = "isFav")
    private boolean isFav;

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


    public void setStoreOffers (StoreOffersModel[] StoreOffers)
    {
        this.StoreOffers = StoreOffers;
    }

    public StoreOffersModel[] getStoreOffers ()
    {
        return StoreOffers;
    }


    public boolean isFav ()
    {
        return isFav;
    }

    public void setFav (boolean isFav)
    {
        this.isFav = isFav;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Phone = "+Phone+", BannerImages = "+BannerImages+", WebURL = "+WebURL+", TwitterURL = "+TwitterURL+", LogoURL = "+LogoURL+", FacebookURL = "+FacebookURL+", StoreId = "+StoreId+", Latitude = "+Latitude+", StoreOffers = "+StoreOffers+", StoreCategories = "+StoreCategories+", StoreTimings = "+StoreTimings+", ContactPerson = "+ContactPerson+", SiteMapURL = "+SiteMapURL+", ExternalShopId = "+ExternalShopId+", ZipCode = "+ZipCode+", Floor = "+Floor+", Name = "+Name+", BriefText = "+BriefText+", AboutText = "+AboutText+", Email = "+Email+", SiteMapActive = "+SiteMapActive+", Address = "+Address+", Longitude = "+Longitude+", LinkedInURL = "+LinkedInURL+", StorePhones = "+StorePhones+"]";
    }
}

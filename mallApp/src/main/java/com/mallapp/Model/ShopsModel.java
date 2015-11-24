package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 11/24/2015.
 */
public class ShopsModel implements Serializable
{
    private String BriefText;

    private String WebURL;

    private String ContactPerson;

    private String StoreId;

    private String Address;

    private String StoreName;

    private String FaceBookURL;

    private String TwitterURL;

    private String LinkedInURL;

    private String Floor;

    private String MallStoreId;

    private ShopCategoriesModel[] ShopCategories;

    private String LogoURL;

    public String getBriefText ()
    {
        return BriefText;
    }

    public void setBriefText (String BriefText)
    {
        this.BriefText = BriefText;
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

    public String getStoreId ()
    {
        return StoreId;
    }

    public void setStoreId (String StoreId)
    {
        this.StoreId = StoreId;
    }

    public String getAddress ()
    {
        return Address;
    }

    public void setAddress (String Address)
    {
        this.Address = Address;
    }

    public String getStoreName ()
    {
        return StoreName;
    }

    public void setStoreName (String StoreName)
    {
        this.StoreName = StoreName;
    }

    public String getFaceBookURL ()
    {
        return FaceBookURL;
    }

    public void setFaceBookURL (String FaceBookURL)
    {
        this.FaceBookURL = FaceBookURL;
    }

    public String getTwitterURL ()
    {
        return TwitterURL;
    }

    public void setTwitterURL (String TwitterURL)
    {
        this.TwitterURL = TwitterURL;
    }

    public String getLinkedInURL ()
    {
        return LinkedInURL;
    }

    public void setLinkedInURL (String LinkedInURL)
    {
        this.LinkedInURL = LinkedInURL;
    }

    public String getFloor ()
    {
        return Floor;
    }

    public void setFloor (String Floor)
    {
        this.Floor = Floor;
    }

    public String getMallStoreId ()
    {
        return MallStoreId;
    }

    public void setMallStoreId (String MallStoreId)
    {
        this.MallStoreId = MallStoreId;
    }

    public ShopCategoriesModel[] getShopCategories ()
    {
        return ShopCategories;
    }

    public void setShopCategories (ShopCategoriesModel[] ShopCategories)
    {
        this.ShopCategories = ShopCategories;
    }

    public String getLogoURL ()
    {
        return LogoURL;
    }

    public void setLogoURL (String LogoURL)
    {
        this.LogoURL = LogoURL;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [BriefText = "+BriefText+", WebURL = "+WebURL+", ContactPerson = "+ContactPerson+", StoreId = "+StoreId+", Address = "+Address+", StoreName = "+StoreName+", FaceBookURL = "+FaceBookURL+", TwitterURL = "+TwitterURL+", LinkedInURL = "+LinkedInURL+", Floor = "+Floor+", MallStoreId = "+MallStoreId+", ShopCategories = "+ShopCategories+", LogoURL = "+LogoURL+"]";
    }
}

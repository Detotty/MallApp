package com.mallapp.Model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Sharjeel on 11/24/2015.
 */
public class ShopsModel implements Serializable
{
    @DatabaseField(columnName = "BriefText")
    private String BriefText;

    @DatabaseField(columnName = "WebURL")
    private String WebURL;

    @DatabaseField(columnName = "ContactPerson")
    private String ContactPerson;

    @DatabaseField(columnName = "StoreId")
    private String StoreId;

    @DatabaseField(columnName = "Address")
    private String Address;

    @DatabaseField(columnName = "StoreName")
    private String StoreName;

    @DatabaseField(columnName = "FaceBookURL")
    private String FaceBookURL;

    @DatabaseField(columnName = "TwitterURL")
    private String TwitterURL;

    @DatabaseField(columnName = "LinkedInURL")
    private String LinkedInURL;

    @DatabaseField(columnName = "Floor")
    private String Floor;

    @DatabaseField(id = true ,columnName = "MallStoreId")
    private String MallStoreId;

    private ShopCategoriesModel[] ShopCategories;

    @DatabaseField(columnName = "LogoURL")
    private String LogoURL;

    @DatabaseField(columnName = "IsFav")
    private boolean IsFav;

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

    public boolean isFav ()
    {
        return IsFav;
    }

    public void setFav (boolean IsFav)
    {
        this.IsFav = IsFav;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [BriefText = "+BriefText+", WebURL = "+WebURL+", ContactPerson = "+ContactPerson+", StoreId = "+StoreId+", Address = "+Address+", StoreName = "+StoreName+", FaceBookURL = "+FaceBookURL+", TwitterURL = "+TwitterURL+", LinkedInURL = "+LinkedInURL+", Floor = "+Floor+", MallStoreId = "+MallStoreId+", ShopCategories = "+ShopCategories+", LogoURL = "+LogoURL+"]";
    }
}

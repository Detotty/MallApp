package com.mallapp.Model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Sharjeel on 12/9/2015.
 */
public class RestaurantModel implements Serializable
{
    @DatabaseField(columnName = "RestaurantName")
    private String RestaurantName;

    @DatabaseField(columnName = "BriefText")
    private String BriefText;

    @DatabaseField(columnName = "ContactPerson")
    private String ContactPerson;

    @DatabaseField(columnName = "WebURL")
    private String WebURL;

    @DatabaseField(columnName = "Address")
    private String Address;

    @DatabaseField(columnName = "RowNumber")
    private String RowNumber;

    private RestaurantCategoriesModel[] RestaurantCategories;

    private String Categories;

    @DatabaseField(columnName = "FaceBookURL")
    private String FaceBookURL;

    @DatabaseField(columnName = "TwitterURL")
    private String TwitterURL;

    @DatabaseField(columnName = "ResturantId")
    private String ResturantId;

    @DatabaseField(columnName = "LinkedInURL")
    private String LinkedInURL;

    @DatabaseField(columnName = "Floor")
    private String Floor;

    @DatabaseField(columnName = "LogoURL")
    private String LogoURL;

    @DatabaseField(id = true ,columnName = "MallResturantId")
    private String MallResturantId;

    @DatabaseField(columnName = "IsFav")
    private boolean IsFav;

    public String getRestaurantName ()
    {
        return RestaurantName;
    }

    public void setRestaurantName (String RestaurantName)
    {
        this.RestaurantName = RestaurantName;
    }

    public String getBriefText ()
    {
        return BriefText;
    }

    public void setBriefText (String BriefText)
    {
        this.BriefText = BriefText;
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

    public String getAddress ()
    {
        return Address;
    }

    public void setAddress (String Address)
    {
        this.Address = Address;
    }

    public String getRowNumber ()
    {
        return RowNumber;
    }

    public void setRowNumber (String RowNumber)
    {
        this.RowNumber = RowNumber;
    }

    public RestaurantCategoriesModel[] getRestaurantCategories ()
    {
        return RestaurantCategories;
    }

    public void setRestaurantCategories (RestaurantCategoriesModel[] RestaurantCategories)
    {
        this.RestaurantCategories = RestaurantCategories;
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

    public String getResturantId ()
    {
        return ResturantId;
    }

    public void setResturantId (String ResturantId)
    {
        this.ResturantId = ResturantId;
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

    public String getLogoURL ()
    {
        return LogoURL;
    }

    public void setLogoURL (String LogoURL)
    {
        this.LogoURL = LogoURL;
    }

    public String getMallResturantId ()
    {
        return MallResturantId;
    }

    public void setMallResturantId (String MallResturantId) {
        this.MallResturantId = MallResturantId;
    }

    public String getCat ()
    {
        return Categories;
    }

    public void setCat (String Categories)    {
        this.Categories = Categories;
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
        return "ClassPojo [RestaurantName = "+RestaurantName+", BriefText = "+BriefText+", ContactPerson = "+ContactPerson+", WebURL = "+WebURL+", Address = "+Address+", RowNumber = "+RowNumber+", RestaurantCategories = "+RestaurantCategories+", FaceBookURL = "+FaceBookURL+", TwitterURL = "+TwitterURL+", ResturantId = "+ResturantId+", LinkedInURL = "+LinkedInURL+", Floor = "+Floor+", LogoURL = "+LogoURL+", MallResturantId = "+MallResturantId+"]";
    }
}

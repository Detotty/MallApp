package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 12/22/2015.
 */
public class FavoritesModel implements Serializable
{
    private String ActivityTextTitle;

    private String BriefText;

    private String AboutText;

    private String CategoryName;

    private String Address;

    private String EntityType;

    private String ActivityTextName;

    private String EntityName;

    private String FavoriteId;

    private String Floor;

    private String EntityId;

    private String LogoURL;

    private String LogoSquareURL;

    private String StartDate;

    private String EndDate;

    private String PlaceName;

    public String getActivityTextTitle ()
    {
        return ActivityTextTitle;
    }

    public void setActivityTextTitle (String ActivityTextTitle)
    {
        this.ActivityTextTitle = ActivityTextTitle;
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

    public String getCategoryName ()
    {
        return CategoryName;
    }

    public void setCategoryName (String CategoryName)
    {
        this.CategoryName = CategoryName;
    }

    public String getAddress ()
    {
        return Address;
    }

    public void setAddress (String Address)
    {
        this.Address = Address;
    }

    public String getEntityType ()
    {
        return EntityType;
    }

    public void setEntityType (String EntityType)
    {
        this.EntityType = EntityType;
    }

    public String getActivityTextName ()
    {
        return ActivityTextName;
    }

    public void setActivityTextName (String ActivityTextName)
    {
        this.ActivityTextName = ActivityTextName;
    }

    public String getEntityName ()
    {
        return EntityName;
    }

    public void setEntityName (String EntityName)
    {
        this.EntityName = EntityName;
    }

    public String getFavoriteId ()
    {
        return FavoriteId;
    }

    public void setFavoriteId (String FavoriteId)
    {
        this.FavoriteId = FavoriteId;
    }

    public String getFloor ()
    {
        return Floor;
    }

    public void setFloor (String Floor)
    {
        this.Floor = Floor;
    }

    public String getEntityId ()
    {
        return EntityId;
    }

    public void setEntityId (String EntityId)
    {
        this.EntityId = EntityId;
    }

    public String getLogoURL ()
    {
        return LogoURL;
    }

    public void setLogoURL (String LogoURL)
    {
        this.LogoURL = LogoURL;
    }

    public String getLogoSquareURL ()
    {
        return LogoSquareURL;
    }

    public void setLogoSquareURL (String LogoSquareURL)
    {
        this.LogoSquareURL = LogoSquareURL;
    }

    public String getStartDate ()
    {
        return StartDate;
    }

    public void setStartDate (String StartDate)
    {
        this.StartDate = StartDate;
    }

    public String getEndDate ()
    {
        return EndDate;
    }

    public void setEndDate (String EndDate)
    {
        this.EndDate = EndDate;
    }

    public String getPlaceName ()
    {
        return PlaceName;
    }

    public void setPlaceName (String PlaceName)
    {
        this.PlaceName = PlaceName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ActivityTextTitle = "+ActivityTextTitle+", BriefText = "+BriefText+", AboutText = "+AboutText+", CategoryName = "+CategoryName+", Address = "+Address+", EntityType = "+EntityType+", ActivityTextName = "+ActivityTextName+", EntityName = "+EntityName+", FavoriteId = "+FavoriteId+", Floor = "+Floor+", EntityId = "+EntityId+", LogoURL = "+LogoURL+"]";
    }
}

package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 11/11/2015.
 */
public class InterestSelectionModel implements Serializable
{
    private String ParentCategoryId;

    private String Description;

    private String CategoryId;

    private String ParentDescription;

    private String CategoryTextId;

    private String CategoryText;

    boolean isInterested;

    public String getParentCategoryId ()
    {
        return ParentCategoryId;
    }

    public void setParentCategoryId (String ParentCategoryId)
    {
        this.ParentCategoryId = ParentCategoryId;
    }

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getCategoryId ()
    {
        return CategoryId;
    }

    public void setCategoryId (String CategoryId)
    {
        this.CategoryId = CategoryId;
    }

    public String getParentDescription ()
    {
        return ParentDescription;
    }

    public void setParentDescription (String ParentDescription)
    {
        this.ParentDescription = ParentDescription;
    }

    public String getCategoryTextId ()
    {
        return CategoryTextId;
    }

    public void setCategoryTextId (String CategoryTextId)
    {
        this.CategoryTextId = CategoryTextId;
    }

    public String getCategoryText ()
    {
        return CategoryText;
    }

    public void setCategoryText (String CategoryText)
    {
        this.CategoryText = CategoryText;
    }

    public boolean isInterested() {
        return isInterested;
    }
    public void setInterested(boolean isInterested) {
        this.isInterested = isInterested;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ParentCategoryId = "+ParentCategoryId+", Description = "+Description+", CategoryId = "+CategoryId+", ParentDescription = "+ParentDescription+", CategoryTextId = "+CategoryTextId+", CategoryText = "+CategoryText+"]";
    }
}
package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 11/25/2015.
 */
public class StoreCategoriesModel implements Serializable {
    private String Description;

    private String CategoryId;

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

    @Override
    public String toString()
    {
        return "ClassPojo [Description = "+Description+", CategoryId = "+CategoryId+"]";
    }
}

package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by hpp on 12/9/2015.
 */
public class RestaurantCategoriesModel implements Serializable
{
    private String CategoryName;

    private String CategoryId;

    public String getCategoryName ()
    {
        return CategoryName;
    }

    public void setCategoryName (String CategoryName)
    {
        this.CategoryName = CategoryName;
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
        return "ClassPojo [CategoryName = "+CategoryName+", CategoryId = "+CategoryId+"]";
    }
}

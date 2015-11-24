package com.mallapp.Model;

/**
 * Created by Sharjeel on 11/24/2015.
 */
public class ShopCategoriesModel
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

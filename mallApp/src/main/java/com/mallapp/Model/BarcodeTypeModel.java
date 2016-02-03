package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 2/2/2016.
 */
public class BarcodeTypeModel implements Serializable
{
    private String BarcodeImageURL;

    private String BarcodeType1;

    private String Id;

    public String getBarcodeImageURL ()
    {
        return BarcodeImageURL;
    }

    public void setBarcodeImageURL (String BarcodeImageURL)
    {
        this.BarcodeImageURL = BarcodeImageURL;
    }

    public String getBarcodeType1 ()
    {
        return BarcodeType1;
    }

    public void setBarcodeType1 (String BarcodeType1)
    {
        this.BarcodeType1 = BarcodeType1;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [BarcodeImageURL = "+BarcodeImageURL+", BarcodeType1 = "+BarcodeType1+", Id = "+Id+"]";
    }
}

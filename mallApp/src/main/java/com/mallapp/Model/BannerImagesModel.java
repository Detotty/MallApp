package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 11/25/2015.
 */
public class BannerImagesModel implements Serializable {
    private String BannerImageURL;

    private String BannerImageId;

    public String getBannerImageURL ()
    {
        return BannerImageURL;
    }

    public void setBannerImageURL (String BannerImageURL)
    {
        this.BannerImageURL = BannerImageURL;
    }

    public String getBannerImageId ()
    {
        return BannerImageId;
    }

    public void setBannerImageId (String BannerImageId)
    {
        this.BannerImageId = BannerImageId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [BannerImageURL = "+BannerImageURL+", BannerImageId = "+BannerImageId+"]";
    }
}

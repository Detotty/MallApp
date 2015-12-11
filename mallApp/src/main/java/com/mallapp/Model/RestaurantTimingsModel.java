package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 12/11/2015.
 */
public class RestaurantTimingsModel implements Serializable {

    private String ClosingTimings;

    private String FromDay;

    private String OpeningTimings;

    private String Id;

    private String ToDay;

    public String getClosingTimings ()
    {
        return ClosingTimings;
    }

    public void setClosingTimings (String ClosingTimings)
    {
        this.ClosingTimings = ClosingTimings;
    }

    public String getFromDay ()
    {
        return FromDay;
    }

    public void setFromDay (String FromDay)
    {
        this.FromDay = FromDay;
    }

    public String getOpeningTimings ()
    {
        return OpeningTimings;
    }

    public void setOpeningTimings (String OpeningTimings)
    {
        this.OpeningTimings = OpeningTimings;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getToDay ()
    {
        return ToDay;
    }

    public void setToDay (String ToDay)
    {
        this.ToDay = ToDay;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ClosingTimings = "+ClosingTimings+", FromDay = "+FromDay+", OpeningTimings = "+OpeningTimings+", Id = "+Id+", ToDay = "+ToDay+"]";
    }
}

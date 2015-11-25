package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 11/25/2015.
 */
public class StoreTimingsModel implements Serializable {
    private String OpeningTiming;

    private String FromDay;

    private String Id;

    private String ToDay;

    private String ClosingTiming;

    public String getOpeningTiming ()
    {
        return OpeningTiming;
    }

    public void setOpeningTiming (String OpeningTiming)
    {
        this.OpeningTiming = OpeningTiming;
    }

    public String getFromDay ()
    {
        return FromDay;
    }

    public void setFromDay (String FromDay)
    {
        this.FromDay = FromDay;
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

    public String getClosingTiming ()
    {
        return ClosingTiming;
    }

    public void setClosingTiming (String ClosingTiming)
    {
        this.ClosingTiming = ClosingTiming;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [OpeningTiming = "+OpeningTiming+", FromDay = "+FromDay+", Id = "+Id+", ToDay = "+ToDay+", ClosingTiming = "+ClosingTiming+"]";
    }
}

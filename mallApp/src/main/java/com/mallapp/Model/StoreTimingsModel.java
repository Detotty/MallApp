package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 11/25/2015.
 */
public class StoreTimingsModel implements Serializable {
    private String Description;

    private String OpeningTiming;

    private String FromDay;

    private String Id;

    private String ToDay;

    private boolean IsException;

    private String ClosingTiming;

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

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

    public boolean getIsException ()
    {
        return IsException;
    }

    public void setIsException (boolean IsException)
    {
        this.IsException = IsException;
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
        return "ClassPojo [Description = "+Description+", OpeningTiming = "+OpeningTiming+", FromDay = "+FromDay+", Id = "+Id+", ToDay = "+ToDay+", IsException = "+IsException+", ClosingTiming = "+ClosingTiming+"]";
    }
}

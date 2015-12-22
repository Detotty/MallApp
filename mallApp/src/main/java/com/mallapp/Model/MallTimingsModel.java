package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 12/16/2015.
 */
public class MallTimingsModel implements Serializable {
    private String FromDay;

    private String EndTime;

    private String StartTime;

    private String ToDay;

    private String TimingId;

    public String getFromDay ()
    {
        return FromDay;
    }

    public void setFromDay (String FromDay)
    {
        this.FromDay = FromDay;
    }

    public String getEndTime ()
    {
        return EndTime;
    }

    public void setEndTime (String EndTime)
    {
        this.EndTime = EndTime;
    }

    public String getStartTime ()
    {
        return StartTime;
    }

    public void setStartTime (String StartTime)
    {
        this.StartTime = StartTime;
    }

    public String getToDay ()
    {
        return ToDay;
    }

    public void setToDay (String ToDay)
    {
        this.ToDay = ToDay;
    }

    public String getTimingId ()
    {
        return TimingId;
    }

    public void setTimingId (String TimingId)
    {
        this.TimingId = TimingId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [FromDay = "+FromDay+", EndTime = "+EndTime+", StartTime = "+StartTime+", ToDay = "+ToDay+", TimingId = "+TimingId+"]";
    }
}

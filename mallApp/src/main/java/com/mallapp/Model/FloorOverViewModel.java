package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 1/20/2016.
 */
public class FloorOverViewModel implements Serializable
{
    private String Name;

    private String FloorImageURL;

    private String FloorId;

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getFloorImageURL ()
    {
        return FloorImageURL;
    }

    public void setFloorImageURL (String FloorImageURL)
    {
        this.FloorImageURL = FloorImageURL;
    }

    public String getFloorId ()
    {
        return FloorId;
    }

    public void setFloorId (String FloorId)
    {
        this.FloorId = FloorId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Name = "+Name+", FloorImageURL = "+FloorImageURL+", FloorId = "+FloorId+"]";
    }
}

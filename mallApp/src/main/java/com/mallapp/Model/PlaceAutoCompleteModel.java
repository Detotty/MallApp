package com.mallapp.Model;

/**
 * Created by Sharjeel on 11/6/2015.
 */
public class PlaceAutoCompleteModel {

    //private Photos[] photos;

    private String id;

    private String place_id;

    private String icon;

    private String vicinity;

    private String scope;

    private AltIds[] alt_ids;

    private String name;

    private String[] types;

    private String reference;

    private String description;

    //private Opening_hours opening_hours;

    private Geometry geometry;


//	public Photos[] getPhotos ()
//	{
//		return photos;
//	}
//
//	public void setPhotos (Photos[] photos)
//	{
//		this.photos = photos;
//	}

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getPlace_id ()
    {
        return place_id;
    }

    public void setPlace_id (String place_id)
    {
        this.place_id = place_id;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getVicinity ()
    {
        return vicinity;
    }

    public void setVicinity (String vicinity)
    {
        this.vicinity = vicinity;
    }

    public String getScope ()
    {
        return scope;
    }

    public void setScope (String scope)
    {
        this.scope = scope;
    }

    public AltIds[] getAltIds ()
    {
        return alt_ids;
    }

    public void setAltIds (AltIds[] alt_ids)
    {
        this.alt_ids = alt_ids;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String[] getTypes ()
    {
        return types;
    }

    public void setTypes (String[] types)
    {
        this.types = types;
    }

    public String getReference ()
    {
        return reference;
    }

    public void setReference (String reference)
    {
        this.reference = reference;
    }

//	public Opening_hours getOpening_hours ()
//	{
//		return opening_hours;
//	}
//
//	public void setOpening_hours (Opening_hours opening_hours)
//	{
//		this.opening_hours = opening_hours;
//	}

    public Geometry getGeometry ()
    {
        return geometry;
    }

    public void setGeometry (Geometry geometry)
    {
        this.geometry = geometry;
    }

    public class AltIds {
        private String place_id;

        private String scope;

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }
    }

//	public class Photos {
//		private String photo_reference;
//
//		private String height;
//
//		private String[] html_attributions;
//
//		private String width;
//
//		public String getPhoto_reference() {
//			return photo_reference;
//		}
//
//		public void setPhoto_reference(String photo_reference) {
//			this.photo_reference = photo_reference;
//		}
//
//		public String getHeight() {
//			return height;
//		}
//
//		public void setHeight(String height) {
//			this.height = height;
//		}
//
//		public String[] getHtml_attributions() {
//			return html_attributions;
//		}
//
//		public void setHtml_attributions(String[] html_attributions) {
//			this.html_attributions = html_attributions;
//		}
//
//		public String getWidth() {
//			return width;
//		}
//
//		public void setWidth(String width) {
//			this.width = width;
//		}
//	}


    public class Geometry {
        private Location location;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }

    public class Location {
        private String lng;

        private String lat;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }

//	public class Opening_hours {
//		private String open_now;
//
//		public String getOpen_now() {
//			return open_now;
//		}
//
//		public void setOpen_now(String open_now) {
//			this.open_now = open_now;
//		}
//	}

}


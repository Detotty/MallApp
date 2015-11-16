package com.mallapp.Model;

/**
 * Created by Sharjeel on 11/16/2015.
 */
public class UserProfileModel
{
    private String CityId;

    private String Street;

    private String Phone;

    private String ImageURL;

    private String DOB;

    private String CityName;

    private String Password;

    private String DeviceType;

    private String Zipcode;

    private String LastLoginUTCDateTime;

    private String IsActive;

    private String FullName;

    private String Image;

    private String Latitude;

    private String CountryCode;

    private String MobilePhone;

    private String LastUpdateUTCDateTime;

    private String ImageBase64String;

    private String DefaultLocationName;

    private String UserId;

    private String LanguageId;

    private String PersonIdentificationNumber;

    private String Email;

    private String Username;

    private String Gender;

    private String CreatedUTCDateTime;

    private String IsDeleted;

    private String Longitude;

    private String FileName;

    private boolean push_notification;


    public UserProfileModel() {
        super();
    }

    public UserProfileModel(String country, String countryCode, String phone_no, boolean push_notification) {
        super();
        this.CountryCode = countryCode;
        this.MobilePhone= phone_no;
        this.push_notification= push_notification;
    }

    public String getCityId ()
    {
        return CityId;
    }

    public void setCityId (String CityId)
    {
        this.CityId = CityId;
    }

    public String getStreet ()
{
    return Street;
}

    public void setStreet (String Street)
    {
        this.Street = Street;
    }

    public String getPhone ()
{
    return Phone;
}

    public void setPhone (String Phone)
    {
        this.Phone = Phone;
    }

    public String getImageURL ()
    {
        return ImageURL;
    }

    public void setImageURL (String ImageURL)
    {
        this.ImageURL = ImageURL;
    }

    public String getDOB ()
{
    return DOB;
}

    public void setDOB (String DOB)
    {
        this.DOB = DOB;
    }

    public String getCityName ()
    {
        return CityName;
    }

    public void setCityName (String CityName)
    {
        this.CityName = CityName;
    }

    public String getPassword ()
    {
        return Password;
    }

    public void setPassword (String Password)
    {
        this.Password = Password;
    }

    public String getDeviceType ()
    {
        return DeviceType;
    }

    public void setDeviceType (String DeviceType)
    {
        this.DeviceType = DeviceType;
    }

    public String getZipcode ()
    {
        return Zipcode;
    }

    public void setZipcode (String Zipcode)
    {
        this.Zipcode = Zipcode;
    }

    public String getLastLoginUTCDateTime ()
    {
        return LastLoginUTCDateTime;
    }

    public void setLastLoginUTCDateTime (String LastLoginUTCDateTime)
    {
        this.LastLoginUTCDateTime = LastLoginUTCDateTime;
    }

    public String getIsActive ()
    {
        return IsActive;
    }

    public void setIsActive (String IsActive)
    {
        this.IsActive = IsActive;
    }

    public String getFullName ()
    {
        return FullName;
    }

    public void setFullName (String FullName)
    {
        this.FullName = FullName;
    }

    public String getImage ()
    {
        return Image;
    }

    public void setImage (String Image)
    {
        this.Image = Image;
    }

    public String getLatitude ()
    {
        return Latitude;
    }

    public void setLatitude (String Latitude)
    {
        this.Latitude = Latitude;
    }

    public String getCountryCode ()
    {
        return CountryCode;
    }

    public void setCountryCode (String CountryCode)
    {
        this.CountryCode = CountryCode;
    }

    public String getMobilePhone ()
    {
        return MobilePhone;
    }

    public void setMobilePhone (String MobilePhone)
    {
        this.MobilePhone = MobilePhone;
    }

    public String getLastUpdateUTCDateTime ()
    {
        return LastUpdateUTCDateTime;
    }

    public void setLastUpdateUTCDateTime (String LastUpdateUTCDateTime)
    {
        this.LastUpdateUTCDateTime = LastUpdateUTCDateTime;
    }

    public String getImageBase64String ()
    {
        return ImageBase64String;
    }

    public void setImageBase64String (String ImageBase64String)
    {
        this.ImageBase64String = ImageBase64String;
    }

    public String getDefaultLocationName ()
    {
        return DefaultLocationName;
    }

    public void setDefaultLocationName (String DefaultLocationName)
    {
        this.DefaultLocationName = DefaultLocationName;
    }

    public String getUserId ()
    {
        return UserId;
    }

    public void setUserId (String UserId)
    {
        this.UserId = UserId;
    }

    public String getLanguageId ()
    {
        return LanguageId;
    }

    public void setLanguageId (String LanguageId)
    {
        this.LanguageId = LanguageId;
    }

    public String getPersonIdentificationNumber ()
    {
        return PersonIdentificationNumber;
    }

    public void setPersonIdentificationNumber (String PersonIdentificationNumber)
    {
        this.PersonIdentificationNumber = PersonIdentificationNumber;
    }

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public String getUsername ()
    {
        return Username;
    }

    public void setUsername (String Username)
    {
        this.Username = Username;
    }

    public String getGender ()
    {
        return Gender;
    }

    public void setGender (String Gender)
    {
        this.Gender = Gender;
    }

    public String getCreatedUTCDateTime ()
    {
        return CreatedUTCDateTime;
    }

    public void setCreatedUTCDateTime (String CreatedUTCDateTime)
    {
        this.CreatedUTCDateTime = CreatedUTCDateTime;
    }

    public String getIsDeleted ()
    {
        return IsDeleted;
    }

    public void setIsDeleted (String IsDeleted)
    {
        this.IsDeleted = IsDeleted;
    }

    public String getLongitude ()
    {
        return Longitude;
    }

    public void setLongitude (String Longitude)
    {
        this.Longitude = Longitude;
    }

    public String getFileName ()
    {
        return FileName;
    }

    public void setFileName (String FileName)
    {
        this.FileName = FileName;
    }

    public boolean isPush_notification() {
        return push_notification;
    }

    public void setPush_notification(boolean push_notification) {
        this.push_notification = push_notification;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CityId = "+CityId+", Street = "+Street+", Phone = "+Phone+", ImageURL = "+ImageURL+", DOB = "+DOB+", CityName = "+CityName+", Password = "+Password+", DeviceType = "+DeviceType+", Zipcode = "+Zipcode+", LastLoginUTCDateTime = "+LastLoginUTCDateTime+", IsActive = "+IsActive+", FullName = "+FullName+", Image = "+Image+", Latitude = "+Latitude+", CountryCode = "+CountryCode+", MobilePhone = "+MobilePhone+", LastUpdateUTCDateTime = "+LastUpdateUTCDateTime+", ImageBase64String = "+ImageBase64String+", DefaultLocationName = "+DefaultLocationName+", UserId = "+UserId+", LanguageId = "+LanguageId+", PersonIdentificationNumber = "+PersonIdentificationNumber+", Email = "+Email+", Username = "+Username+", Gender = "+Gender+", CreatedUTCDateTime = "+CreatedUTCDateTime+", IsDeleted = "+IsDeleted+", Longitude = "+Longitude+" , FileName = "+FileName+"]";
    }
}

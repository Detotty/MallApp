package com.chatdbserver.xmpp.model;

import android.graphics.Bitmap;

public class UserProfileModel {

	private String Phone;

	private String Street;

	private String Password;

	private String CurrentLoctionName;

	private String LastUpdateUnixDatetime;

	private String LastName;

	private String DeviceType;

	private String Zipcode;

	private String LastLoginUTCDateTime;

	private String Dob;

	private String IsActive;

	private String Image;

	private String LastLoginUnixDateTime;

	private String FirstName;

	private String MobilePhone;

	private String LastUpdateUTCDateTime;

	private String ImageBase64String;

	private String DefaultLocationName;

	private String CreatedUnixDatetime;

	private String UserId;

	private String LanguageId;

	private String DefaultLocationLatLong;

	private String Country;

	private String City;

	private String Title;

	private String Summary;

	private long UnixDob;

	private String CurrentLocationLatLong;

	private String PersonIdentificationNumber;

	private String Email;

	private String FileName;

	private String Gender;

	private String Username;

	private String CreatedUTCDateTime;

	private String IsDeleted;

	private Bitmap imageBitmap;

	private String TrustedCount;

	private String EndorsementsCount;

	private boolean IsTrusted = false;

	private String TrustedByCount;

	private boolean NotificationStatus = false;

	public void setNotificationStatus(boolean status) {
		this.NotificationStatus = status;
	}

	public boolean getNotificationsStatus() {
		return this.NotificationStatus;
	}

	public void setIsTrusted(boolean trusted) {
		this.IsTrusted = trusted;
	}

	public boolean getIsTrusted() {
		return IsTrusted;
	}

	public String getTrustedCount() {
		return TrustedCount;
	}

	public void setTrustedCount(String trustedCount) {
		this.TrustedCount = trustedCount;
	}

	public String getEndorsementsCount() {
		return EndorsementsCount;
	}

	public void setEndorsementsCount(String endorsementsCount) {
		this.EndorsementsCount = endorsementsCount;
	}

	public void setTrustedByCount(String count) {
		this.TrustedByCount = count;
	}

	public String getTrustedByCount() {
		return this.TrustedByCount;
	}


	public Bitmap getImageBitmap() {
		return imageBitmap;
	}

	public void setImageBitmap(Bitmap bitmap) {
		this.imageBitmap = bitmap;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String Phone) {
		this.Phone = Phone;
	}

	public String getStreet() {
		return Street;
	}

	public void setStreet(String Street) {
		this.Street = Street;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String Password) {
		this.Password = Password;
	}

	public String getCurrentLoctionName() {
		return CurrentLoctionName;
	}

	public void setCurrentLoctionName(String CurrentLoctionName) {
		this.CurrentLoctionName = CurrentLoctionName;
	}

	public String getLastUpdateUnixDatetime() {
		return LastUpdateUnixDatetime;
	}

	public void setLastUpdateUnixDatetime(String LastUpdateUnixDatetime) {
		this.LastUpdateUnixDatetime = LastUpdateUnixDatetime;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String LastName) {
		this.LastName = LastName;
	}

	public String getDeviceType() {
		return DeviceType;
	}

	public void setDeviceType(String DeviceType) {
		this.DeviceType = DeviceType;
	}

	public String getZipcode() {
		return Zipcode;
	}

	public void setZipcode(String Zipcode) {
		this.Zipcode = Zipcode;
	}

	public String getLastLoginUTCDateTime() {
		return LastLoginUTCDateTime;
	}

	public void setLastLoginUTCDateTime(String LastLoginUTCDateTime) {
		this.LastLoginUTCDateTime = LastLoginUTCDateTime;
	}

	public String getDob() {
		return Dob;
	}

	public void setDob(String Dob) {
		this.Dob = Dob;
	}

	public String getIsActive() {
		return IsActive;
	}

	public void setIsActive(String IsActive) {
		this.IsActive = IsActive;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String Image) {
		this.Image = Image;
	}

	public String getLastLoginUnixDateTime() {
		return LastLoginUnixDateTime;
	}

	public void setLastLoginUnixDateTime(String LastLoginUnixDateTime) {
		this.LastLoginUnixDateTime = LastLoginUnixDateTime;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String FirstName) {
		this.FirstName = FirstName;
	}

	public String getMobilePhone() {
		return MobilePhone;
	}

	public void setMobilePhone(String MobilePhone) {
		this.MobilePhone = MobilePhone;
	}

	public String getLastUpdateUTCDateTime() {
		return LastUpdateUTCDateTime;
	}

	public void setLastUpdateUTCDateTime(String LastUpdateUTCDateTime) {
		this.LastUpdateUTCDateTime = LastUpdateUTCDateTime;
	}

	public String getImageBase64String() {
		return ImageBase64String;
	}

	public void setImageBase64String(String ImageBase64String) {
		this.ImageBase64String = ImageBase64String;
	}

	public String getDefaultLocationName() {
		return DefaultLocationName;
	}

	public void setDefaultLocationName(String DefaultLocationName) {
		this.DefaultLocationName = DefaultLocationName;
	}

	public String getCreatedUnixDatetime() {
		return CreatedUnixDatetime;
	}

	public void setCreatedUnixDatetime(String CreatedUnixDatetime) {
		this.CreatedUnixDatetime = CreatedUnixDatetime;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String UserId) {
		this.UserId = UserId;
	}

	public String getLanguageId() {
		return LanguageId;
	}

	public void setLanguageId(String LanguageId) {
		this.LanguageId = LanguageId;
	}

	public String getDefaultLocationLatLong() {
		return DefaultLocationLatLong;
	}

	public void setDefaultLocationLatLong(String DefaultLocationLatLong) {
		this.DefaultLocationLatLong = DefaultLocationLatLong;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String Country) {
		this.Country = Country;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String City) {
		this.City = City;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String Title) {
		this.Title = Title;
	}

	public String getSummary() {
		return Summary;
	}

	public void setSummary(String Summary) {
		this.Summary = Summary;
	}

	public long getUnixDob() {
		return UnixDob;
	}

	public void setUnixDob(long UnixDob) {
		this.UnixDob = UnixDob;
	}

	public String getCurrentLocationLatLong() {
		return CurrentLocationLatLong;
	}

	public void setCurrentLocationLatLong(String CurrentLocationLatLong) {
		this.CurrentLocationLatLong = CurrentLocationLatLong;
	}

	public String getPersonIdentificationNumber() {
		return PersonIdentificationNumber;
	}

	public void setPersonIdentificationNumber(String PersonIdentificationNumber) {
		this.PersonIdentificationNumber = PersonIdentificationNumber;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String Email) {
		this.Email = Email;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String FileName) {
		this.FileName = FileName;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String Gender) {
		this.Gender = Gender;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String Username) {
		this.Username = Username;
	}

	public String getCreatedUTCDateTime() {
		return CreatedUTCDateTime;
	}

	public void setCreatedUTCDateTime(String CreatedUTCDateTime) {
		this.CreatedUTCDateTime = CreatedUTCDateTime;
	}

	public String getIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(String IsDeleted) {
		this.IsDeleted = IsDeleted;
	}

    public PhoneBookContacts getPhoneBookModel(){

        PhoneBookContacts phoneBookContacts = new PhoneBookContacts();
        phoneBookContacts.setUserId(getUserId());
        phoneBookContacts.setFirstName(getFirstName());
        phoneBookContacts.setFileName(getFileName());
        phoneBookContacts.setEndorsementsCount(getEndorsementsCount());
        phoneBookContacts.setAppUser(true);
        phoneBookContacts.setIsContact(false);
        phoneBookContacts.setStatus(false);
        phoneBookContacts.setIsTrusted(getIsTrusted());
        phoneBookContacts.setTrustedByCount(getTrustedByCount());
        phoneBookContacts.setTrustedCount(getTrustedCount());

        return  phoneBookContacts;
    }

}
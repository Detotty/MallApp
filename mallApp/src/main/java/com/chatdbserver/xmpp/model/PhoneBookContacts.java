package com.chatdbserver.xmpp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "phonebookcontacts")
public class PhoneBookContacts implements Serializable{

    @DatabaseField(id = true)
    private String UserId;

    @DatabaseField
    private String EndorsementsCount  = "0";
    @DatabaseField
	private boolean IsContact;
    @DatabaseField
	private String FileName;
    @DatabaseField
	private String Username;
    @DatabaseField
	private String FirstName;
    @DatabaseField
	private String ContactId;


    @DatabaseField
	private String TrustedCount = "0";
    @DatabaseField
	private String MobilePhone;
    @DatabaseField
	private String LastName;
    @DatabaseField
	private boolean IsTrusted;
    @DatabaseField
	private String TrustedByCount  = "0";
    @DatabaseField
	private String ThumbnailURI;
    @DatabaseField
    private boolean AppUser = false;
    @DatabaseField
    private boolean status = false;

    public PhoneBookContacts(){

    }

    public boolean getStatus(){
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public void setAppUser(boolean appUser){
        this.AppUser = appUser;
    }

    public boolean getAppUser(){
        return AppUser;
    }

	public String getThumbnailURI(){
		return ThumbnailURI;
	}

	public void setThumbnailURI(String uri){
		this.ThumbnailURI = uri;
	}

	public String getEndorsementsCount ()
	{
		return EndorsementsCount;
	}

	public void setEndorsementsCount (String EndorsementsCount)
	{
		this.EndorsementsCount = EndorsementsCount;
	}

	public boolean getIsContact ()
	{
		return IsContact;
	}

	public void setIsContact (boolean IsContact)
	{
		this.IsContact = IsContact;
	}

	public String getFileName ()
	{
		return FileName;
	}

	public void setFileName (String FileName)
	{
		this.FileName = FileName;
	}

	public String getUsername ()
	{
		return Username;
	}

	public void setUsername (String Username)
	{
		this.Username = Username;
	}

	public String getFirstName ()
	{
		return FirstName;
	}

	public void setFirstName (String FirstName)
	{
		this.FirstName = FirstName;
	}

	public String getContactId ()
	{
		return ContactId;
	}

	public void setContactId (String ContactId)
	{
		this.ContactId = ContactId;
	}

	public String getUserId ()
	{
		return UserId;
	}

	public void setUserId (String UserId)
	{
		this.UserId = UserId;
	}

	public String getTrustedCount ()
	{
		return TrustedCount;
	}

	public void setTrustedCount (String TrustedCount)
	{
		this.TrustedCount = TrustedCount;
	}

	public String getMobilePhone ()
	{
		return MobilePhone;
	}

	public void setMobilePhone (String MobilePhone)
	{
		this.MobilePhone = MobilePhone;
	}

	public String getLastName ()
	{
		return LastName;
	}

	public void setLastName (String LastName)
	{
		this.LastName = LastName;
	}

	public boolean getIsTrusted ()
	{
		return IsTrusted;
	}

	public void setIsTrusted (boolean IsTrusted)
	{
		this.IsTrusted = IsTrusted;
	}

	public String getTrustedByCount ()
	{
		return TrustedByCount;
	}

	public void setTrustedByCount (String TrustedByCount)
	{
		this.TrustedByCount = TrustedByCount;
	}

//	public static void setOnlineStatus(String userId) {
//
//        List<PhoneBookContacts>  savedList = PhoneBookContacts.find(PhoneBookContacts.class,
//                "USER_ID=?", userId);
//
//        if(savedList.size() >0 && savedList.size() == 1 ){
//            PhoneBookContacts phoneBookContacts = savedList.get(0);
//            phoneBookContacts.setStatus(true);
//
//            phoneBookContacts.update();
//        }
//
//	}


//	private ChatConversationModel conversationModel;
//
//	public ChatConversationModel getConversationModel(){
//        setConversationModel(ChatConversationModel.findById(ChatConversationModel.class, conversationModel.getId()));
//		return conversationModel;
//	}
//
//	public void setConversationModel(ChatConversationModel conversationModel){
//		this.conversationModel = conversationModel;
//	}

}
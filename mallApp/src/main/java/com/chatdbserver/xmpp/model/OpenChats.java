package com.chatdbserver.xmpp.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "openchats")
public class OpenChats implements Serializable {
    @DatabaseField(id = true)
    private String jid;
    private SingleChat lastMessage;
    private int numOfUnreadMsgs;
    @ForeignCollectionField(eager = false)
    ForeignCollection<SingleChat> singleChat;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    PhoneBookContacts phoneBookContacts;

    public OpenChats(){

    }

    public int getNumOfUnreadMsgs() {
        return this.numOfUnreadMsgs;
    }

    public void setNumOfUnreadMsgs(int numOfUnreadMsgs) {
        this.numOfUnreadMsgs = numOfUnreadMsgs;
    }

    public SingleChat getLastMessage() {
        return this.lastMessage;
    }

    public void setLastMessage(SingleChat lastMessage) {
        this.lastMessage = lastMessage;
    }

    public OpenChats(String jid) {
        this.jid = jid;
    }

    public String getJid() {
        return this.jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public ForeignCollection<SingleChat> getSingleChat() {
        return this.singleChat;
    }

    public void addSingleChat(SingleChat ssingleChat) {
        this.singleChat.add(ssingleChat);
    }

    public PhoneBookContacts getPhoneBookContacts() {
        return phoneBookContacts;
    }

    public void setPhoneBookContacts(PhoneBookContacts phoneBookContacts) {
        this.phoneBookContacts = phoneBookContacts;
    }

    private boolean isSwipeOpened;

    public boolean isSwipeOpened() {
        return isSwipeOpened;
    }

    public void setSwipeOpened(boolean isSwipeOpened) {
        this.isSwipeOpened = isSwipeOpened;
    }
}

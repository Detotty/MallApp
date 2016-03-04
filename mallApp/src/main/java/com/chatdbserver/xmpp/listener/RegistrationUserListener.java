package com.chatdbserver.xmpp.listener;

import com.chatdbserver.xmpp.model.PhoneBookContacts;
import com.chatdbserver.xmpp.model.SingleChat;

import org.jivesoftware.smack.chat.Chat;

/**
 * Created by Ayesha on 9/16/2015.
 */
public interface RegistrationUserListener {

    public void onDataReceived(PhoneBookContacts userProfileModel, SingleChat singleChat, Chat chat);
    public void onGrpDataReceived(PhoneBookContacts userProfileModel, SingleChat singleChat, Chat chat,String grp_id);
    public void onConnectionError();

}

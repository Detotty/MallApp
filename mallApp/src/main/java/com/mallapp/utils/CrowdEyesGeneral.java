package com.mallapp.utils;

import com.chatdbserver.xmpp.model.PhoneBookContacts;
import com.mallapp.Model.SingleChatModel;

import java.util.ArrayList;

/**
 * Created by Sharjeel on 24/02/2016.
 */
public class CrowdEyesGeneral {

    public static ArrayList<PhoneBookContacts> CEContactList;
    public static ArrayList<SingleChatModel> ChatMsgList = new ArrayList<>();
    public static ArrayList<String> arrayList_notification_chat = new ArrayList<>();
    public static ArrayList<String> arrayList_notification_activ = new ArrayList<>();

    public static ArrayList<String> getArrayList_notification_chat() {
        return arrayList_notification_chat;
    }

    public static void setArrayList_notification_chat(ArrayList<String> arrayList_notification_chat) {
        CrowdEyesGeneral.arrayList_notification_chat = arrayList_notification_chat;
    }

    public static ArrayList<String> getArrayList_notification_activ() {
        return arrayList_notification_activ;
    }

    public static void setArrayList_notification_activ(ArrayList<String> arrayList_notification_activ) {
        CrowdEyesGeneral.arrayList_notification_activ = arrayList_notification_activ;
    }

    public static ArrayList<PhoneBookContacts> getCEContactList() {
        return CEContactList;
    }

    public static void setCEContactList(ArrayList<PhoneBookContacts> CEContactList) {
        CrowdEyesGeneral.CEContactList = CEContactList;
    }



    public static ArrayList<SingleChatModel> getChatMsgList() {
        return ChatMsgList;
    }

    public static void setChatMsgList(ArrayList<SingleChatModel> chatMsgList) {
        ChatMsgList = chatMsgList;
    }

}

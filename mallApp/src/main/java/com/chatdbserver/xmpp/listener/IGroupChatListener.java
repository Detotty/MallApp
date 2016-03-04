package com.chatdbserver.xmpp.listener;

import com.chatdbserver.xmpp.model.SingleChat;

import org.jivesoftware.smack.packet.Message;

public interface IGroupChatListener {
    void groupJoined(String str);

    void leavegroup(String str);

    void newGroupMessageArrived(SingleChat singleChat,String jid);
}

package com.chatdbserver.xmpp.listener;

import org.jivesoftware.smack.packet.Message;

public interface IGroupChatListener {
    void groupJoined(String str);

    void leavegroup(String str);

    void newGroupMessageArrived(Message message);
}

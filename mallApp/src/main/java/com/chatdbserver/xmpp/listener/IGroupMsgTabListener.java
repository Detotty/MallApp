package com.chatdbserver.xmpp.listener;

import com.chatdbserver.xmpp.model.SingleChat;

public interface IGroupMsgTabListener {
    void groupJoined(String str);

    void leavegroup(String str);

    void newGroupMessageArrived(SingleChat singleChat, String jid);
}

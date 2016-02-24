package com.chatdbserver.xmpp.listener;

import com.chatdbserver.xmpp.model.SingleChat;

public interface IXMPPChatListener {

    void newMessageArrived(SingleChat singleChat);
    void messageDelivered(SingleChat singleChat);
}

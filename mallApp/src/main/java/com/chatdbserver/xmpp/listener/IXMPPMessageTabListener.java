package com.chatdbserver.xmpp.listener;

import com.chatdbserver.xmpp.model.SingleChat;

/**
 * Created by Adeel on 1/19/2016.
 */
public interface IXMPPMessageTabListener {


    void newMessageArrived(SingleChat singleChat);
    void messageDelivered(SingleChat singleChat);

}

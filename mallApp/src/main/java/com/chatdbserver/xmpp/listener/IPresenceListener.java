package com.chatdbserver.xmpp.listener;

import org.jivesoftware.smack.packet.Presence;

/**
 * Created by Adeel on 1/6/2016.
 */
public interface IPresenceListener {

    public void processPresence(Presence presence);
}

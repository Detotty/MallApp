package com.chatdbserver.xmpp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.chatdbserver.xmpp.listener.IGroupChatListener;
import com.chatdbserver.xmpp.listener.IPresenceListener;
import com.chatdbserver.xmpp.listener.IXMPPChatListener;
import com.chatdbserver.xmpp.listener.IXMPPMessageTabListener;
import com.chatdbserver.xmpp.listener.IXMPPTabCountListener;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PresenceListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.ParticipantStatusListener;

public class XmppService extends Service implements ConnectionListener, PresenceListener, MessageListener, ParticipantStatusListener {
    private static XmppService instance;
    boolean chatsLoaded;
    IXMPPChatListener ixmppChatListener;
    IGroupChatListener ixmppGroupChatListener;
    IPresenceListener ixmppPresenceListener;

    IXMPPMessageTabListener ixmppMessageTabListener;
    IXMPPTabCountListener ixmppTabCountListener;

    String tag;

    public XmppService() {
        this.tag = "XmppService---------";
        this.chatsLoaded = true;
    }

    public IGroupChatListener getIxmppGroupChatListener() {
        return this.ixmppGroupChatListener;
    }

    public void setIxmppGroupChatListener(IGroupChatListener ixmppGroupChatListener) {
        this.ixmppGroupChatListener = ixmppGroupChatListener;
    }

    public IXMPPChatListener getIxmppChatListener() {
        return this.ixmppChatListener;
    }

    public void setIxmppChatListener(IXMPPChatListener ixmppChatListener) {
        this.ixmppChatListener = ixmppChatListener;
    }

    public IPresenceListener getIxmppPresenceListener() {
        return ixmppPresenceListener;
    }

    public void setIxmppPresenceListener(IPresenceListener ixmppPresenceListener) {
        this.ixmppPresenceListener = ixmppPresenceListener;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static boolean isReady() {
        return instance != null;
    }

    public static XmppService instance() {
        if (isReady()) {
            return instance;
        }
        throw new RuntimeException("XmppService not instantiated yet");
    }

    public void onStart(Intent intent, int startId) {
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void authenticated(XMPPConnection xmppConnection, boolean arg1) {
        Log.i(this.tag, "Athenticated");

    }

    public void connected(XMPPConnection xmppConnection) {
        Log.d("XMPPClient", "[SettingsDialog] Connected to " + xmppConnection.getHost());
    }

    public void connectionClosed() {
        Log.d("server", "connectionClosed");
    }

    public void connectionClosedOnError(Exception arg0) {
        Log.i(this.tag, "connectionClosedOnError");
    }

    public void reconnectingIn(int arg0) {
        Log.i(this.tag, "reconnectingIn");
    }

    public void reconnectionFailed(Exception arg0) {
        Log.i(this.tag, "reconnectionFailed");
    }

    public void reconnectionSuccessful() {
        Log.i(this.tag, "reconnectionSuccessful");
    }


    public IXMPPMessageTabListener getIxmppMessageTabListener() {
        return ixmppMessageTabListener;
    }

    public void setIxmppMessageTabListener(IXMPPMessageTabListener ixmppMessageTabListener) {
        this.ixmppMessageTabListener = ixmppMessageTabListener;
    }

    public IXMPPTabCountListener getIxmppTabCountListener() {
        return ixmppTabCountListener;
    }

    public void setIxmppTabCountListener(IXMPPTabCountListener ixmppTabCountListener) {
        this.ixmppTabCountListener = ixmppTabCountListener;
    }

    public void processPresence(Presence presence) {
        Log.i(this.tag, "to:" + presence.getTo());
        Log.i(this.tag, "from:" + presence.getFrom());
        Log.i(this.tag, "Name:" + presence.getType().name());
        Log.i(this.tag, "Status:" + presence.getStatus());
    }

    public void processMessage(Message message) {
        this.ixmppGroupChatListener.newGroupMessageArrived(message);
        System.out.println("GroupMessage---------------->" + message.getBody());
    }

    public void adminGranted(String arg0) {
    }

    public void adminRevoked(String arg0) {
    }

    public void banned(String arg0, String arg1, String arg2) {
    }

    public void joined(String participant) {
        this.ixmppGroupChatListener.groupJoined(participant);
        System.out.println("GroupMessage------JOINED---------->" + participant);
    }

    public void kicked(String arg0, String arg1, String arg2) {
    }

    public void left(String participant) {
        this.ixmppGroupChatListener.leavegroup(participant);
        System.out.println("GroupMessage------LEFT---------->" + participant);
    }

    public void membershipGranted(String arg0) {
    }

    public void membershipRevoked(String arg0) {
    }

    public void moderatorGranted(String arg0) {
    }

    public void moderatorRevoked(String arg0) {
    }

    public void nicknameChanged(String arg0, String arg1) {
    }

    public void ownershipGranted(String arg0) {
    }

    public void ownershipRevoked(String arg0) {
    }

    public void voiceGranted(String arg0) {
    }

    public void voiceRevoked(String arg0) {
    }

}

package com.chatdbserver.xmpp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.chatdbserver.utils.IMUtils;
import com.chatdbserver.utils.IMessageStatus;
import com.chatdbserver.xmpp.db.IMDBManager;
import com.chatdbserver.xmpp.listener.IGroupChatListener;
import com.chatdbserver.xmpp.listener.IPresenceListener;
import com.chatdbserver.xmpp.listener.IXMPPChatListener;
import com.chatdbserver.xmpp.listener.IXMPPMessageTabListener;
import com.chatdbserver.xmpp.listener.IXMPPTabCountListener;
import com.chatdbserver.xmpp.listener.RegistrationUserListener;
import com.chatdbserver.xmpp.model.OpenChats;
import com.chatdbserver.xmpp.model.PhoneBookContacts;
import com.chatdbserver.xmpp.model.SingleChat;
import com.mallapp.Constants.GlobelWebURLs;
import com.mallapp.Constants.UserChatConstants;
import com.mallapp.utils.RegistrationController;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration.Builder;
import org.jivesoftware.smackx.chatstates.ChatState;
import org.jivesoftware.smackx.chatstates.ChatStateListener;
import org.jivesoftware.smackx.chatstates.ChatStateManager;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.offline.OfflineMessageManager;
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager;
import org.jivesoftware.smackx.receipts.DeliveryReceiptRequest;
import org.jivesoftware.smackx.receipts.ReceiptReceivedListener;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class IMManager implements ChatMessageListener, RosterListener, ReceiptReceivedListener,
        RegistrationUserListener, MessageListener, ChatStateListener, ChatManagerListener {

    private static IMManager imManager;
    private Context appContext;
    ChatManager chatmanager;
    boolean chatsLoaded;
    DiscussionHistory disccussionHistory;
    List<HostedRoom> hostedrooms;
    private IMDBManager imDBManager;
    MultiUserChatManager multiChatManager;
    MessageListener messageListener;
    private OfflineMessageManager offlineMessageManager;
    private DeliveryReceiptManager deliveryReceiptManager;
    MultiUserChat multiUserChat;
    private HashMap<String, Chat> openxmppChats;

    private Chat activeChat;
    private AbstractXMPPConnection xmppConnection;
    private Roster rosterManager;




    /* renamed from: com.apps.xmpp.IMManager.1 */
    class C00001 implements Runnable {
        C00001() {
        }

        public void run() {
            try {
                IMManager.this.xmppConnection.login();
                IMManager.this.xmppConnection.sendStanza(new Presence(Type.available));

                IMManager.this.chatmanager = ChatManager.getInstanceFor(IMManager.this.xmppConnection);
                IMManager.this.chatmanager.addChatListener(IMManager.this);

                IMManager.this.multiChatManager = MultiUserChatManager.getInstanceFor(IMManager.this.xmppConnection);
                IMManager.this.rosterManager = Roster.getInstanceFor(IMManager.this.xmppConnection);

                IMManager.this.rosterManager.addRosterListener(IMManager.this);

                ChatStateManager.getInstance(IMManager.this.xmppConnection);

                IMManager.this.deliveryReceiptManager = DeliveryReceiptManager.getInstanceFor(xmppConnection);
                IMManager.this.deliveryReceiptManager.addReceiptReceivedListener(IMManager.this);

//                IMManager.this.offlineMessageManager = new OfflineMessageManager(xmppConnection);

//                IMManager.this.retrieveOfflineMessages();

                if (IMManager.this.chatsLoaded) {
                    IMManager.this.chatsLoaded = false;
                    IMManager.this.loadOpenChatFromDB();
                }
            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (SmackException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    /* renamed from: com.apps.xmpp.IMManager.2 */
    class C00012 implements Runnable {
        private final /* synthetic */ String val$password;
        private final /* synthetic */ String val$serverName;
        private final /* synthetic */ String val$usrName;

        C00012(String str, String str2, String str3) {
            this.val$usrName = str;
            this.val$password = str2;
            this.val$serverName = str3;
        }

        public void run() {
            IMManager.this.xmppConnection = new XMPPTCPConnection(((Builder) ((Builder) ((Builder) ((Builder) ((Builder)
                    XMPPTCPConnectionConfiguration.builder().setUsernameAndPassword(
                            this.val$usrName, this.val$password))
                    .setServiceName(this.val$serverName) )
                    .setHost(this.val$serverName))
                    .setSecurityMode(SecurityMode.disabled))
                    .setPort(5222))
//                    .setSendPresence(false)
                    .build());
            try {
                IMManager.this.xmppConnection.connect();

                if (XmppService.isReady()) {
                    IMManager.this.xmppConnection.addConnectionListener(XmppService.instance());
                }
                IMManager.this.loginIM();
            } catch (SmackException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (XMPPException e3) {
                e3.printStackTrace();
            }
        }
    }

    public static IMManager getIMManager(Context appContext) {
        if (imManager == null) {
            imManager = new IMManager(appContext);
        }
        return imManager;
    }

    private IMManager(Context appContext) {
        this.chatsLoaded = true;
        this.appContext = appContext;
        appContext.startService(new Intent(appContext, XmppService.class));
        this.openxmppChats = new HashMap();
        this.imDBManager = new IMDBManager(appContext);
        try {
            this.imDBManager.loadDaos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setChatListener(IXMPPChatListener chatListner) {
        if (XmppService.instance() != null) {
            XmppService.instance().setIxmppChatListener(chatListner);
        }
    }

    public void setMessageListener(IXMPPMessageTabListener chatListner) {
        if (XmppService.instance() != null) {
            XmppService.instance().setIxmppMessageTabListener(chatListner);
        }
    }

    public void setTabCountListener(IXMPPTabCountListener chatListner) {
        if (XmppService.instance() != null) {
            XmppService.instance().setIxmppTabCountListener(chatListner);
        }
    }


    public void setPresenceListener(IPresenceListener presenceListener) {
        if (XmppService.instance() != null) {
            XmppService.instance().setIxmppPresenceListener(presenceListener);
        }
    }

//    public void setMessageListener() {
//        if (XmppService.instance() != null) {
//            XmppService.instance().m
//        }
//    }

    public void setGroupChatListener(IGroupChatListener groupchatListner) {
        if (XmppService.instance() != null) {
            XmppService.instance().setIxmppGroupChatListener(groupchatListner);
        }
    }

    public void loadOpenChatFromDB() {
        try {
            List<OpenChats> oChats = this.imDBManager.getOpenChatsfromDB();
            if (this.chatmanager != null) {
                for (int i = 0; i < oChats.size(); i++) {
                    createNewChat(((OpenChats) oChats.get(i)).getJid(), false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Chat createNewChat(String jid, boolean savetoDB) throws SQLException {
//        if(jid.contains("@"))
        String id = new StringBuilder(String.valueOf(jid)).append("@").append(this.xmppConnection.getServiceName()).toString();//+"/mobile";
        Log.i("create new chat", id);
        Chat chat = this.chatmanager.createChat(id);//, this);
        this.openxmppChats.put(jid, chat);

        if (savetoDB) {
            this.imDBManager.saveChat(jid, null);
        }
        return chat;
    }

    public void loginIM() {
        new Thread(new C00001()).start();
    }

    public int getUnreadMessageCount(String jid) {
        return 0;
    }

    /*
            This method should only be called from endorsement_user_chat class
     */
    public Vector<SingleChat> getAllChatsByJid(String jid) {
        Vector<SingleChat> sChats = null;
        try {

            sChats = this.imDBManager.getAllChatsOfId(jid);
            Chat openChats = this.openxmppChats.get(jid);
            activeChat = openChats;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sChats;
    }

    public int getUnreadChatCount() {
        try {
            return this.imDBManager.getNoOfUnreadChat();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<PhoneBookContacts> getAppContacts(){
        List<PhoneBookContacts> phoneBookContacts = null;
        try{
            phoneBookContacts = this.imDBManager.getAllContacts();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  phoneBookContacts;
    }

    public PhoneBookContacts getContactById(String userId){
        PhoneBookContacts phoneBookContacts = null;
        try{
            phoneBookContacts = this.imDBManager.getAppContactOfId(userId);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return phoneBookContacts;
    }

    public void saveContactById(PhoneBookContacts phoneBookContacts){
        try{
            this.imDBManager.saveContact(phoneBookContacts);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public List<OpenChats> getOpenChat() {
        List<OpenChats> oChats = null;
        try {
            oChats = this.imDBManager.getOpenChatsfromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oChats;
    }

    public void disConnect() throws NotConnectedException {
        if (this.xmppConnection!=null && this.xmppConnection.isConnected()) {
            this.xmppConnection.disconnect(new Presence(Type.unavailable));
        }
    }

    public boolean isXmmpConnected() {
        if (this.xmppConnection == null) {
            return false;
        }
        return this.xmppConnection.isAuthenticated();
    }

    public AbstractXMPPConnection getXmppConnection(){
        return this.xmppConnection;
    }

    public void InitIMManager(String serverName, String usrName, String password) {
        new Thread(new C00012(usrName, password, serverName)).start();
    }

    public SingleChat sendIMMessage(String toAddress, String msge) {
        SQLException e;
        NotConnectedException e2;
        SingleChat singleChat1 = null;
        if (!this.xmppConnection.isAuthenticated()) {
            return null;
        }
        try {
            Chat ochat = (Chat) this.openxmppChats.get(toAddress);
            Message msg = new Message();
            msg.setBody(msge);

//            PhoneBookContacts contacts = getContactById(toAddress.);
//            if(contacts==null) // save the user to the db
//                saveContactById(contacts);


            if (ochat == null) {
                ochat = createNewChat(toAddress, true);
            }
            DeliveryReceiptRequest.addTo(msg);

            ochat.sendMessage(msg);

            SingleChat singleChat12 = new SingleChat();
            try {
                singleChat12.setPacketId(msg.getStanzaId());
                singleChat12.setMessage(msge);
                singleChat12.setMsgtime(getCurrentTimeStamp());
                singleChat12.setMsgStatus(IMessageStatus.MESSAGESENT);
                singleChat12.setMessageType(UserChatConstants.User_CHAT_RIGHT);
                this.imDBManager.saveChat(toAddress, singleChat12);
                return singleChat12;
            } catch (SQLException e3) {
                e = e3;
                singleChat1 = singleChat12;
                e.printStackTrace();
                return singleChat1;
            } catch (Exception e4) {
                //e2 = e4;
                singleChat1 = singleChat12;
                e4.printStackTrace();
                return singleChat1;
            }
        } catch (SQLException e5) {
            e = e5;
            e.printStackTrace();
            return singleChat1;
        } catch (NotConnectedException e6) {
            e2 = e6;
            e2.printStackTrace();
            return singleChat1;
        }
    }

    public void unactiveChat(){
            try {

                activeChat = null;

            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    @Override
    public void processMessage(Chat chat, Message message) {
        String from = IMUtils.getUserFromJID(message.getFrom());

        Log.i("Incoming message", "messsage:"+ from +":"+ message.getBody() + ": Participant:"+ chat.getParticipant().toString());

        if(message.getBody()!=null) {

            SingleChat singleChat = new SingleChat();
            singleChat.setMessage(message.getBody());
            singleChat.setMsgStatus(IMessageStatus.MESSAGEUNREAD);
            singleChat.setMessageType(UserChatConstants.User_CHAT_LEFT);
            singleChat.setMsgtime(getCurrentTimeStamp());

            PhoneBookContacts contact = getContactById(from);
            if(contact == null){

                String userId = from;
                if( userId.contains("_") )
                    userId = userId.split("_")[1];

                RegistrationController registrationController = new RegistrationController(appContext);
                registrationController.getUserProfile(userId,this,singleChat,chat);

            }else {

                if (((Chat) this.openxmppChats.get(from)) == null) {
                    this.openxmppChats.put(from, chat);
                }
                if (singleChat.getMessage().startsWith("(ce_template_photomsg)")) {
                    String cleanurl = singleChat.getMessage()
                            .replace("(ce_template_photomsg)", "")
                            .trim();
                    Log.e("IM Url", "" + cleanurl);
                    singleChat.setLocalUrl(cleanurl.trim());
                    updateMsgToDB(from, singleChat);
                }
                saveMsgToDB(from, singleChat);


                if (XmppService.instance().getIxmppChatListener() != null && IMUtils.getUserFromJID(activeChat.getParticipant()).equals(from) ) {
                    XmppService.instance().getIxmppChatListener().newMessageArrived(singleChat);
                }
                else if (XmppService.instance().getIxmppMessageTabListener() != null) {
                    XmppService.instance().getIxmppMessageTabListener().newMessageArrived(singleChat);
                }
                else if(XmppService.instance().getIxmppTabCountListener() != null){
                    XmppService.instance().getIxmppTabCountListener().updateUnreadCount(0);
                }
                else{
//                    Intent intent = new Intent(UserChatConstants.Broadcast_Update_Tab_Count);
//                    LocalBroadcastManager.getInstance(appContext).sendBroadcast(intent);
//
////					List<String> unreadMessages = getUnreadMessages();
//                    ChatDBController.getInstance(appContext).messageNotification(singleChat);
                }

            }
        }


    }

    public boolean sendGroupMessage(String message) {
//        if (!this.multiUserChat.isJoined()) {
//            return false;
//        }
//        Message msg = this.multiUserChat.createMessage();
//        msg.setBody(message);
//        try {
//            this.multiUserChat.sendMessage(msg);
//        } catch (NotConnectedException e) {
//            e.printStackTrace();
//        }
        return true;
    }

//    @Override
//    public void processPresence(Presence presence) {
//        if (XmppService.instance().getIxmppPresenceListener() != null) {
//            XmppService.instance().getIxmppPresenceListener().processPresence(presence);
//        }
//    }


    public int deleteOpenChat(OpenChats openchat) {
        int i = 0;
        try {
            i = this.imDBManager.deleteOpenChat(openchat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int deleteAllRecords() {
        int i = 0;
        try {
            this.imDBManager.deleteAllRecords();
            i = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public List<HostedRoom> getPublicGroups(boolean refresh) throws NoResponseException, XMPPErrorException, NotConnectedException {
        if (refresh || this.hostedrooms == null) {
            this.hostedrooms = this.multiChatManager.getHostedRooms((String) this.multiChatManager.getServiceNames().get(0));
        }
        return this.hostedrooms;
    }

    public void leaveGroup() {
        try {
            this.multiUserChat.leave();
        } catch (NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void joinRoom(String roomNameJid, String nickname) {
        try {
            this.multiUserChat = this.multiChatManager.getMultiUserChat(roomNameJid);
            this.multiUserChat.addMessageListener(XmppService.instance());
            this.multiUserChat.addParticipantListener(XmppService.instance());
            this.multiUserChat.addParticipantStatusListener(XmppService.instance());
            if (this.disccussionHistory == null) {
                this.disccussionHistory = new DiscussionHistory();
                this.disccussionHistory.setMaxStanzas(10);
            }
            this.multiUserChat.join(nickname, "", this.disccussionHistory, 500);
        } catch (NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPErrorException e2) {
            e2.printStackTrace();
        } catch (NotConnectedException e3) {
            e3.printStackTrace();
        }
    }



    public void updateMsgToDB(String jid, SingleChat singleChat) {
        try {
            this.imDBManager.updateChat(jid, singleChat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SingleChat getMsgByPacketId(String packetId) {
        try {
            return this.imDBManager.getMsgByPacketId(packetId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	public List<String> getUnreadMessages() {
		try {
			return this.imDBManager.getListOfUnreadMsges();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

    private void saveMsgToDB(String jid, SingleChat singleChat) {
        try {
            this.imDBManager.saveChat(jid, singleChat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addConnectionListener(ConnectionListener listener){

        this.xmppConnection.addConnectionListener(listener);




    }

    public void readChatMessages(String jid){
        try{
            this.imDBManager.readOpenChatMessages(jid);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void entriesAdded(Collection<String> collection) {
        Log.d("TAG", "enteries added: " + collection.size());
    }

    @Override
    public void entriesUpdated(Collection<String> collection) {
        Log.d("TAG", "enteries added: " + collection.size());
    }

    @Override
    public void entriesDeleted(Collection<String> collection) {
        Log.d("TAG", "entriesDeleted: " + collection.size());
    }

    @Override
    public void presenceChanged(Presence presence) {
        Log.d("TAG", "presenceChanged: " + presence.getFrom() + ":" + presence.getStatus());

        System.out.println("User:" + presence.getFrom());
        System.out.println("Name:"+ presence.getType().name() );
        System.out.println("Status:" + presence.getStatus() );
        System.out.println("Mode:" + presence.getMode());
        System.out.println("Proriy:" + presence.getPriority());
        System.out.println("To:" + presence.getTo());

                String id = presence.getFrom().split("@")[0];
        id = id.split("_")[1];
        PhoneBookContacts contacts = new PhoneBookContacts();
        contacts.setUserId(id);

        contacts = imManager.getContactById(id);

        if(contacts == null){
            // fetch user details

        }else {

            if (presence.getType() == Type.available)
                contacts.setStatus(true);
            else
                contacts.setStatus(false);


            imManager.saveContactById(contacts);

            if (XmppService.instance().getIxmppPresenceListener() != null)
                XmppService.instance().getIxmppPresenceListener().processPresence(presence);
        }


    }

    @Override
    public void processMessage(Message message) {

         Log.i("processMessage","Incoming message :"+ message.getFrom() +":"+ message.getBody());

    }

    @Override
    public void chatCreated(Chat chat, boolean b) {

        Log.i("chatCreated", "" + chat.getParticipant());
        chat.addMessageListener(this);

    }

    @Override
    public void stateChanged(Chat chat, ChatState chatState) {

        Log.i("stateChanged", "" + chat.getParticipant() + ":" + chatState.name());
    }

    @Override
    public void onReceiptReceived(String s, String s1, String s2, Stanza stanza) {

                Log.i("Message Delivered  To=", s + ", From =  " + s1 + " , ID= " + s2 +", Ext:"+stanza.getExtensions().get(0));

                System.out.println("to:" + stanza.getTo() + ":" + stanza.getFrom());

        String id = s.split("@")[0];


        SingleChat singleChat = getMsgByPacketId(s2);

        if(singleChat!=null){



            singleChat.setMsgStatus(IMessageStatus.MESSAGEDELIVERED);
            singleChat.setMessageType(UserChatConstants.User_CHAT_RIGHT);

            updateMsgToDB(id, singleChat);


            if(XmppService.instance().getIxmppChatListener()!=null)
                XmppService.instance().getIxmppChatListener().messageDelivered(singleChat);

            else if(XmppService.instance().getIxmppTabCountListener() != null){
                XmppService.instance().getIxmppTabCountListener().updateUnreadCount(0);
            }


        }

    }


    private void retrieveOfflineMessages(){

        try {

        Iterator<Message> it =  offlineMessageManager.getMessages().iterator();
        System.out.println(offlineMessageManager.supportsFlexibleRetrieval());

        System.out.println("Number of offline messages:: " + offlineMessageManager.getMessageCount());

        Map<String,ArrayList<Message>> offlineMsgs = new HashMap<String,ArrayList<Message>>();
        while (it.hasNext()) {
            org.jivesoftware.smack.packet.Message message = it.next();
            System.out
                    .println("receive offline messages, the Received from [" + message.getFrom()
                            + "] the message:" + message.getBody());
            String fromUser = message.getFrom().split("/")[0];

            if(offlineMsgs.containsKey(fromUser))
            {
                offlineMsgs.get(fromUser).add(message);
            }else{
                ArrayList<Message> temp = new ArrayList<Message>();
                temp.add(message);
                offlineMsgs.put(fromUser, temp);
            }
        }
        // Deal with a collection of offline messages ...

            offlineMessageManager.deleteMessages();
            xmppConnection.sendStanza(new Presence(Type.available));

        } catch (NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPErrorException e) {
            e.printStackTrace();
        } catch (NotConnectedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDataReceived(PhoneBookContacts contacts,SingleChat singleChat, Chat chat) {

        String from = GlobelWebURLs.ce_user+contacts.getUserId();

          if (((Chat) this.openxmppChats.get(from)) == null) {
            this.openxmppChats.put(from, chat);
        }


            saveContactById(contacts);

        saveMsgToDB(from, singleChat);


        if (XmppService.instance().getIxmppMessageTabListener() != null) {
            XmppService.instance().getIxmppMessageTabListener().newMessageArrived(singleChat);
        }
        else if(XmppService.instance().getIxmppTabCountListener() != null){
            XmppService.instance().getIxmppTabCountListener().updateUnreadCount(0);
        }
        else{
//            Intent intent = new Intent(UserChatConstants.Broadcast_Update_Tab_Count);
//            LocalBroadcastManager.getInstance(appContext).sendBroadcast(intent);
//
////			List<String> unreadMessages = getUnreadMessages();
//            ChatDBController.getInstance(appContext).messageNotification(singleChat);
        }

    }

    @Override
    public void onConnectionError() {

    }
    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }


}

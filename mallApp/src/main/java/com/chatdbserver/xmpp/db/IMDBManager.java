package com.chatdbserver.xmpp.db;

import android.content.Context;
import android.util.Log;

import com.chatdbserver.utils.IMessageStatus;
import com.chatdbserver.xmpp.model.OpenChats;
import com.chatdbserver.xmpp.model.PhoneBookContacts;
import com.chatdbserver.xmpp.model.SingleChat;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.QueryBuilder;
import com.mallapp.db.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class IMDBManager {
    Context appContext;
    DatabaseHelper databaseHelper;
    Dao<OpenChats, String> openchatsDao;
    Dao<SingleChat, Integer> singlechatDao;
    Dao<PhoneBookContacts, String> appcontactDao;


    public IMDBManager(Context context) {
        this.openchatsDao = null;
        this.singlechatDao = null;
        this.appContext = context;
    }

    public void loadDaos() throws SQLException {
        if (this.openchatsDao == null) {
            this.openchatsDao = getDBHelper().getOpenchatsDao();
        }
        if (this.singlechatDao == null) {
            this.singlechatDao = getDBHelper().getSinglechatDao();
        }

        if (this.appcontactDao == null) {
            this.appcontactDao = getDBHelper().getContactsDao();
        }
    }

    private DatabaseHelper getDBHelper() throws SQLException {
        if (this.databaseHelper == null) {
            this.databaseHelper = (DatabaseHelper) OpenHelperManager.getHelper(this.appContext, DatabaseHelper.class);
        }
        return this.databaseHelper;
    }

    public int deleteOpenChat(OpenChats openChat) throws SQLException {
        return this.openchatsDao.delete(openChat);
    }

    public void deleteAllRecords() throws SQLException {
        this.openchatsDao.deleteBuilder().delete();
        this.singlechatDao.deleteBuilder().delete();
        this.appcontactDao.deleteBuilder().delete();


    }

    public List<OpenChats> getOpenChatsfromDB() throws SQLException {

        List<OpenChats> oChats = this.openchatsDao.queryForAll();
        for (int i = 0; i < oChats.size(); i++) {
            ForeignCollection<SingleChat> singleChats = ((OpenChats) oChats.get(i)).getSingleChat();
            CloseableIterator<SingleChat> iterator = singleChats.closeableIterator();
            ((OpenChats) oChats.get(i)).setLastMessage((SingleChat) iterator.moveRelative(singleChats.size() - 1));
            iterator.close();
            ((OpenChats) oChats.get(i)).setNumOfUnreadMsgs(readOpenChatMessages(oChats.get(i).getJid()).size());
        }
        return oChats;
    }


    public int getNoOfUnreadChat() throws SQLException {

        List<OpenChats> openChatsList = getOpenChatsfromDB();

        int i = 0;
        for (OpenChats openChats : openChatsList) {
            if (openChats.getNumOfUnreadMsgs() > 0)
                i++;
        }

        return i;

    }

    public List<String> getListOfUnreadMsges() throws SQLException {
        List<OpenChats> openChatsList = getOpenChatsfromDB();

        List<String> unreaList = new ArrayList<String>();

        for (OpenChats openChats : openChatsList) {
            if (openChats.getLastMessage().getMsgStatus() == IMessageStatus.MESSAGEUNREAD) {
                unreaList.add(openChats.getPhoneBookContacts().getFirstName() + " " +
                        openChats.getLastMessage().getMessage());
            }
        }

        return unreaList;
    }

    public Vector<SingleChat> getAllChatsOfId(String jid) throws SQLException {
        Vector<SingleChat> chatVector = new Vector();
        OpenChats oChats = (OpenChats) this.openchatsDao.queryForId(jid);
        if (oChats != null) {
            CloseableIterator<SingleChat> iterator = oChats.getSingleChat().closeableIterator();
            try {
                while (iterator.hasNext()) {
                    SingleChat singleChat = iterator.next();

                    if (singleChat.getMsgStatus() == IMessageStatus.MESSAGEUNREAD) {
                        singleChat.setMsgStatus(IMessageStatus.MESSAGEREAD);
                        this.singlechatDao.update(singleChat);
                    }
                    chatVector.add(singleChat);

                }
            } finally {
                iterator.close();
            }
        }
        return chatVector;
    }

    public boolean updateChat(String jid, SingleChat singleChat) throws SQLException {
        OpenChats openChat1 = (OpenChats) this.openchatsDao.queryForId(jid);
        if (openChat1 == null) {
            return false;
        }
        if (singleChat != null) {
            singleChat.setOpenchats(openChat1);
            this.singlechatDao.update(singleChat);
        }
        return true;
    }


    public SingleChat getMsgByPacketId(String packetId) throws SQLException {
        QueryBuilder<OpenChats, String> openChats = this.openchatsDao.queryBuilder();
        QueryBuilder<SingleChat, Integer> singleChats = this.singlechatDao.queryBuilder();


        singleChats.where().eq("packetId", packetId);

        SingleChat sinlge = this.singlechatDao.queryForFirst(singleChats.prepare());


        //List<OpenChats> op = this.openchatsDao.query(openChats.prepare());

        return sinlge;

    }

    public boolean updateOpenChats(String jid, PhoneBookContacts phoneBookContacts) throws SQLException {
        OpenChats openChat1 = (OpenChats) this.openchatsDao.queryForId(jid);
        if (openChat1 != null) {

            openChat1.setPhoneBookContacts(phoneBookContacts);
            this.appcontactDao.update(phoneBookContacts);
            this.openchatsDao.update(openChat1);
        }

        return true;
    }

    public void saveChat(String jid, SingleChat singleChat) throws SQLException {
        OpenChats openChat1 = (OpenChats) this.openchatsDao.queryForId(jid);
        PhoneBookContacts phoneBookContacts = null;
        if (openChat1 == null) {
            openChat1 = new OpenChats(jid);
            Log.d("saving chat", "saved chat jid:" + jid);

            phoneBookContacts = getAppContactOfId(jid);
            openChat1.setPhoneBookContacts(phoneBookContacts);
            this.openchatsDao.createOrUpdate(openChat1);
        }
        if (singleChat != null) {
            singleChat.setOpenchats(openChat1);
            phoneBookContacts = getAppContactOfId(jid);
            singleChat.setPhoneBookContacts(phoneBookContacts);
            this.singlechatDao.createOrUpdate(singleChat);
        }
    }

    public void saveGroupChat(String jid, SingleChat singleChat, PhoneBookContacts phoneBookContacts) throws SQLException {
        OpenChats openChat1 = (OpenChats) this.openchatsDao.queryForId(jid);
        if (openChat1 == null) {
            openChat1 = new OpenChats(jid);
            Log.d("saving chat", "saved chat jid:" + jid);
//            openChat1.setParticipants((ForeignCollection<ParticipantsModel>) arrayList);
            openChat1.setPhoneBookContacts(phoneBookContacts);
            this.openchatsDao.createOrUpdate(openChat1);
        }
        if (singleChat != null) {
            singleChat.setOpenchats(openChat1);
            singleChat.setPhoneBookContacts(phoneBookContacts);
            this.singlechatDao.createOrUpdate(singleChat);
        }
    }


    public void saveContact(PhoneBookContacts contact) throws SQLException {
        PhoneBookContacts phoneBookContacts = (PhoneBookContacts) this.appcontactDao.queryForId(contact.getUserId());
        if (phoneBookContacts == null) {
            Log.d("saving chat", "saving new contact:" + contact.getUserId());
            this.appcontactDao.createOrUpdate(contact);
        }
        if (contact != null) {
            Log.d("updating chat", "updating contact:" + contact.getUserId());
            this.appcontactDao.update(contact);
        }
    }


    public List<PhoneBookContacts> getAllContacts() throws SQLException {
        List<PhoneBookContacts> oChats;
        QueryBuilder<PhoneBookContacts, String> queryBuilder = this.appcontactDao.queryBuilder();
        queryBuilder.where().eq("IsContact", true);

        oChats = this.appcontactDao.query(queryBuilder.prepare());

        return oChats;
    }


    public PhoneBookContacts getAppContactOfId(String userId) throws SQLException {
        if (userId.contains("_"))
            userId = userId.split("_")[1];
        if (userId.contains("@"))
            userId = userId.split("@")[0];

        PhoneBookContacts savedContact = (PhoneBookContacts) this.appcontactDao.queryForId(userId);

//        if(savedContact == null && phoneBookContacts.getFirstName()!=null){
//            saveContact(phoneBookContacts);
//            savedContact = phoneBookContacts;
//        }

        return savedContact;
    }

    public List<SingleChat> readOpenChatMessages(String jid) throws SQLException {

        Vector<SingleChat> chatVector = new Vector();
        OpenChats oChats = (OpenChats) this.openchatsDao.queryForId(jid);
        if (oChats != null) {
            CloseableIterator<SingleChat> iterator = oChats.getSingleChat().closeableIterator();
            try {
                while (iterator.hasNext()) {
                    SingleChat singleChat = iterator.next();

                    if (singleChat.getMsgStatus() == IMessageStatus.MESSAGEUNREAD) {
                        chatVector.add(singleChat);
                    }

                }
            } finally {
                iterator.close();
            }
        }
        return chatVector;

//        QueryBuilder<SingleChat, Integer> sChats = this.singlechatDao.queryBuilder();
//        QueryBuilder<OpenChats, String> sOpen = this.openchatsDao.queryBuilder();
//        sChats.join(sOpen);
//        sChats.where()
//                .eq("jid", jid)
//                .eq("msgStatus", Integer.valueOf(IMessageStatus.MESSAGEUNREAD));
//
//        List<SingleChat> iterator = new ArrayList<SingleChat>();
//
//        if (sChats != null) {
//            iterator = this.singlechatDao.query(sChats.prepare());
//
//
//            for(SingleChat openChats: iterator){
//                openChats.setMsgStatus(IMessageStatus.MESSAGEUNREAD);
////                this.singlechatDao.update(openChats);
//            }
//
//            return iterator;
//        }
//
//        return iterator;
    }


}

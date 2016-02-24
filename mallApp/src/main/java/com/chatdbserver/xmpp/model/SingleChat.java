package com.chatdbserver.xmpp.model;

import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "singlechat")
public class SingleChat implements Serializable {
    private Bitmap bitmap;
    @DatabaseField
    private String lat;
    @DatabaseField
    private String localUrl;
    @DatabaseField
    private String longg;
    @DatabaseField
    String message;
    @DatabaseField
    int messageType;
    @DatabaseField(generatedId = true)
    int msgId;
    @DatabaseField
    int msgStatus;
    @DatabaseField
    private String msgtime;
    @DatabaseField(canBeNull = false, foreign = true)
    OpenChats openchats;
    @DatabaseField
    private String removeUrl;
    @DatabaseField
    String packetId;

    public String getPacketId(){
        return packetId;
    }

    public void setPacketId(String packetId){
        this.packetId = packetId;
    }

    public Spannable getSpannablemessage() {
        return new SpannableString(getMessage());
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getRemoveUrl() {
        return this.removeUrl;
    }

    public void setRemoveUrl(String removeUrl) {
        this.removeUrl = removeUrl;
    }

    public String getLocalUrl() {
        return this.localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongg() {
        return this.longg;
    }

    public void setLongg(String longg) {
        this.longg = longg;
    }

    public int getMsgId() {
        return this.msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getMessageType() {
        return this.messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getMsgStatus() {
        return this.msgStatus;
    }

    public void setMsgStatus(int msgStatus) {
        this.msgStatus = msgStatus;
    }

    public OpenChats getOpenchats() {
        return this.openchats;
    }

    public void setOpenchats(OpenChats openchats) {
        this.openchats = openchats;
    }

    public int getId() {
        return this.msgId;
    }

    public void setId(int id) {
        this.msgId = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgtime() {
        return this.msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }
}

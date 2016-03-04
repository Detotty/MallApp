package com.chatdbserver.xmpp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Sharjeel on 2/23/2016.
 */
@DatabaseTable(tableName = "participantsmodel")
public class ParticipantsModel implements Serializable {

    @DatabaseField(id = true)
    String participant_id;
    @DatabaseField
    private String participant;
    @DatabaseField(canBeNull = false, foreign = true)
    OpenChats openchats;
    public ParticipantsModel() {

    }

    public OpenChats getOpenchats() {
        return openchats;
    }

    public void setOpenchats(OpenChats openchats) {
        this.openchats = openchats;
    }

    public String getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(String participant_id) {
        this.participant_id = participant_id;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}

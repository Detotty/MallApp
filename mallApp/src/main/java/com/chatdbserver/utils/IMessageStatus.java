package com.chatdbserver.utils;


public interface IMessageStatus {
    public static final int MESSAGEDELIVERED = 1003;
    public static final int MESSAGEIN = 1;
    public static final int MESSAGEOUT = 2;
    public static final int MESSAGEREAD = 1001;
    public static final int MESSAGESEEN = 1004;
    public static final int MESSAGESENT = 1002;
    public static final int MESSAGEUNREAD = 100;
}

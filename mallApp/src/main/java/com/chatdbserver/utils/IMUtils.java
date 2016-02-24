package com.chatdbserver.utils;

public class IMUtils {
    public static String getUserFromJID(String jid) {
        return jid.split("@")[0];
    }
}

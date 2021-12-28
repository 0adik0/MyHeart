package com.health.my_heart.utils;

public class StringDecoder {
    public static String encodeEMail(String email) {
        return email.replace(".", "%2E").replace("@", "%4E");
    }
    public static String decodeEMail(String encodedEmail) {
        return encodedEmail.replace("%2E", ".").replace("%4E", "@");
    }
}

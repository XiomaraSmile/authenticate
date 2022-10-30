package com.lxm.auth.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Random;

public class MyUtils {
    public static  final int  RANDOM_LENGTH = 16;

    private static final int VALID_TOKEN_HOUR = 2;

    /**
     * getRandomString with JDK
     * @param length length of str
     * @return random str
     */
    public static String getUserToken(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=0; i < length; i++){
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * get the md5 result for source
     * @param source  origin str
     * @return   Md5 result
     * @throws NoSuchAlgorithmException when get md5 instance fail
     */
    public static String getMdsStr (String source) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("md5");
        byte[] bytes = source.getBytes();
        byte[] targetBytes = digest.digest(bytes);
        char [] characters = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        StringBuilder builder = new StringBuilder();
        for (byte b : targetBytes) {
            int high = (b >> 4) & 15;
            int low = b & 15;
            char highChar = characters[high];
            char lowChar = characters[low];
            builder.append(highChar).append(lowChar);
        }

        return builder.toString();
    }


    public static boolean isTokenValid(long ttl) {
        Calendar c1=Calendar.getInstance();
        long hour=(c1.getTimeInMillis() - ttl)/(60*60*1000);
        return hour < VALID_TOKEN_HOUR;
    }

    /**
     * to judge one str is empty or not
     * @param str
     * @return true if empty str false else
     */
    public static boolean isEmptyStr(String str) {
        return str == null || "".equals(str);
    }
}

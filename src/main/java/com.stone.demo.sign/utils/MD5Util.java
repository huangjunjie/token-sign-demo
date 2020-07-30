package com.stone.demo.sign.utils;


import java.security.MessageDigest;

public class MD5Util {

    private static final String hexDigitals[] = {"0", "1", "2",
            "3", "4", "5",
            "6", "7", "8",
            "9", "a", "b",
            "c", "d", "e",
            "f"};


    private static String byteArrayToHexString(byte [] b){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i =0 ;i<b.length; i++) {
            stringBuffer.append(byteToHexString(b[i]));
        }
        return stringBuffer.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if(n<0) {
            n+=256;
        }
        int d1 = n /16;
        int d2 = n%16;
        return  hexDigitals[d1]+hexDigitals[d2];
    }


    public static String encode(String originalString) {
        return encode(originalString,"UTF-8");
    }

    private static String encode(String originalString, String charsetname) {
        String resultString  = null;
        try {
            resultString = new String(originalString);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if( null == charsetname  || "".equals(charsetname)) {
                resultString =byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception e) {

        }
        return resultString;
    }
}

package com.tung7.ex.repository.base.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Tung
 * @version 1.0
 * @date 2016/12/18
 * @update
 */
public class Utils {
    /**
     * 数组转成十六进制字符串
     * @return HexString
     */
    public static String toHexString(byte[] b){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i){
            buffer.append(toHexString(b[i]) + " ");
        }
        return buffer.toString();
    }

    public static String toHexString(byte b){
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1){
            return "0" + s;
        }else{
            return s;
        }
    }

    public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
        StringBuffer md5 = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];

        int nread = 0;
        while ((nread = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };
        byte[] mdbytes = md.digest();

        // convert the byte to hex format
        for (int i = 0; i < mdbytes.length; i++) {
            md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return md5.toString();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        File f = new File("f:\\sss.apk");
        System.out.println(Utils.getMD5(new FileInputStream(f)));
        System.out.println(Utils.getMD5(new FileInputStream(f)).length());
    }
}

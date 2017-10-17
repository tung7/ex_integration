package com.tung7.ex.repository.base;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/14.
 * @update
 */

public class UnGzip {
    public static void main(String[] args) throws IOException {

//        FileOutputStream fos = new FileOutputStream("f:\\res.dat");
//        GZIPOutputStream gos = new GZIPOutputStream(fos);
//        gos.write("caonima".getBytes(Charset.forName("UTF-8")));
//        gos.flush();
//        gos.close();

        FileInputStream is = new FileInputStream("f:\\res.dat");
        int len = is.available();
        byte[] bytes = new byte[is.available()];
        System.out.println(is.read(bytes));
        System.out.println(uncompress(bytes));
    }

    public static String uncompress(byte[] bytes) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer))>= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)
        return out.toString();
    }
}

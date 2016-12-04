package com.tung7.ex.repository.markdown;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pegdown.PegDownProcessor;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * Created by Tung on 2016/12/5.
 */
public class PegdownTest {
    long start;
    public static byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    @Test public void testFileChannel() {
        start = System.currentTimeMillis();
        PegDownProcessor processor = new PegDownProcessor();
        String path = "d:\\uml_relation.md";
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile(path, "rw");
            FileChannel fileChannel = aFile.getChannel();
//            ByteBuffer buffer = ByteBuffer.allocate((int)fileChannel.size());
            ByteBuffer buffer = ByteBuffer.allocate(204800);
            byte[] bytes = new byte[0];
            buffer.clear();
            StringBuilder builder = new StringBuilder();
            while (fileChannel.read(buffer) != -1) {
                System.out.println( fileChannel.position());
                byte[] b = buffer.array();
                bytes = concat(bytes, b);
                buffer.clear(); // 必须clear
            }
            System.out.println("read uses " + (System.currentTimeMillis() - start) + "ms");
            builder.append(new String(bytes, "UTF-8"));
            System.out.println(processor.markdownToHtml(builder.toString()));
            System.out.println("all uses " + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test public void testFile() {
        try {
            start = System.currentTimeMillis();
            PegDownProcessor processor = new PegDownProcessor();
            String path = "d:\\uml_relation.md";
            File f = new File(path);
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s = bufferedReader.readLine();
            StringBuilder builder = new StringBuilder();
            while(s != null) {
                builder.append(s).append("\r\n");
                s = bufferedReader.readLine();
            }
            System.out.println(processor.markdownToHtml(builder.toString()));
            System.out.println("uses " + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test public void testPegDownProcessor() {
        String source = "## Headline with ID ## {# headid}";
        PegDownProcessor processor = new PegDownProcessor();
        String target = processor.markdownToHtml(source);
        System.out.println(target);

    }



    @Test
    public void testFirst () {
        System.out.println("first");
    }


    @Before
    public void before() {
        System.out.println("================ START ==================");

    }

    @After
    public void after() {
        System.out.println("================ END ==================");

    }

}


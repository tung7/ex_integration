package com.tung7.ex.repository.application;

import java.io.*;
import java.util.Random;

/**
 * TODO Complete The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/3/31.
 * @update
 */

/**
 * C:\Users\Tung\AppData\Local\Temp
 * 演示了流不关闭，导致的file.delete不能删除
 */
public class TempFileDeleteApp {
    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());

        int times = 10;
        try {
            int i = 0;
            while(i < times) {

                String name = String.valueOf(random.nextInt(Integer.MAX_VALUE));
                File f = File.createTempFile("tung_", name);
                System.out.println(f.getAbsolutePath());

                OutputStream os = new FileOutputStream(f);
                os.write(new byte[]{31,2,3,4,5});
                InputStream is = new FileInputStream(f);
                int b = is.read();
                System.out.println(b);

                Thread.sleep(500L);
                i++;
//                os.close();
//                is.close();
                System.out.println(f.delete());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

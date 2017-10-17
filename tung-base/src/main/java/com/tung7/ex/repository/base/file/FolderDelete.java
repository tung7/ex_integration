package com.tung7.ex.repository.base.file;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 删除目录树过深的文件夹
 *
 * @author Tung
 * @version 1.0
 * @date 2017/6/7.
 * @update
 */

public class FolderDelete {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("请输入要删除的文件目录");
        }
        for (String arg : args) {
            File file = new File(arg);
            try {
                del(file);
            } catch (FileNotFoundException e) {
                System.out.println("找不到文件: " + arg);
            }
        }
    }

    /**
     * 删除
     * @param f
     * @throws FileNotFoundException
     */
    private static void del(File f) throws FileNotFoundException {
        if (!f.exists()) {
            throw new FileNotFoundException("找不到文件");
        }

        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (File f1 : files) {
                del(f1);
            }
        }
        f.delete();
    }
}

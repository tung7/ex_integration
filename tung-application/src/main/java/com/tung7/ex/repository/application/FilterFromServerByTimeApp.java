package com.tung7.ex.repository.application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TODO Complete The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/3/29.
 * @update
 */

public class FilterFromServerByTimeApp {
    public static ExecutorService es = Executors.newFixedThreadPool(15);

    public static List<String> getFilesFromList(File f) throws IOException {
        List<String> fileList = new ArrayList<String>();
        FileReader reader = new FileReader(f);
        BufferedReader bf = new BufferedReader(reader);

        String line = null;
        while((line = bf.readLine()) != null) {
            fileList.add(line);
        }


        return fileList;
    }
    /**
     * 第一步 获取文件列表
     * 第二步 运行程序
     * 14:04
     * find `pwd`  -type f -mtime -90 > file.list
     * java FilterFromServerByTimeApp > log.out 2>&1
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("[DEBUG] - 开始");
        String pwd = pwd();
        System.out.println("[DEBUG] - pwd" + pwd);
        //    List<String> fileList = proccess("find ./  -type f -mtime -90");

        //List<String> fileList =  proccess(args[0]); //java FilterFromServerByTimeApp "find `pwd`  -type f -mtime -90"
       // System.out.println("length:" + fileList.size());
        System.out.println("[DEBUG] - 获取文件列表start");
        List<String> fileList = null;
        try {
            fileList = getFilesFromList(new File(pwd, "file.list"));
        } catch (IOException e) {
            System.out.println("[ERROR] - 获取文件列表异常");
        }
        System.out.println("[DEBUG] - 获取文件列表end， length:" + fileList.size());

        File cpDist = new File(new File("pwd").getParentFile(), "cpDist");
        cpDist.mkdirs();
        System.out.println(cpDist.getAbsolutePath());

        System.out.println("[DEBUG] - COPY文件 start");

        final String disPath = cpDist.getAbsolutePath();
        for(final String f  : fileList) {
            es.submit( new Runnable() {
                @Override
                public void run() {
                    Process process = null;
                    try {
                        File file = new File(f);
                        File disFile = new File(disPath + "/" + file.getName().substring(0,2));
                        disFile.mkdirs();
                        String cpCmd = "\\cp " + f + " " + disFile.getAbsolutePath();
                        String[] cmd = new String[]{"/bin/sh", "-c", cpCmd};
                        System.out.println("cp cmd:" + cpCmd);
                        process = Runtime.getRuntime().exec(cmd);
                        process.waitFor();
                    } catch (IOException e) {
                        System.out.println("[ERROR] - 文件复制异常： " + f);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if (process != null) process.destroy();
                    }
                }
            });

        }
        System.out.println("[DEBUG] - COPY文件 end");
        es.shutdown();
        es.awaitTermination(1000, TimeUnit.SECONDS);
    }

    public static String pwd() {
        String[] cmd = new String[]{"/bin/sh", "-c", "pwd"};
        String res = "";
        BufferedReader bis = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bis = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = bis.readLine()) != null) {
                res = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) process.destroy();
        }
        return res;
    }

    public static List<String> proccess(String c) {
        List<String> fileList = new ArrayList<String>();
        String cmd = c;

        BufferedReader bis = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bis = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = bis.readLine()) != null) {
                fileList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) process.destroy();
        }
        return fileList;
    }
}

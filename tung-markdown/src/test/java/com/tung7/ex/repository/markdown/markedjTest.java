package com.tung7.ex.repository.markdown;

import io.github.gitbucket.markedj.Marked;
import io.github.gitbucket.markedj.Options;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Tung on 2016/12/5.
 */
//https://github.com/gitbucket/markedj
public class MarkedjTest {
    long start;

    public boolean isMetaLine(String s) {
        return s != null && s.trim().startsWith("---");
    }

    public boolean checkMeta(BufferedReader bufferedReader) throws IOException{
        String s = bufferedReader.readLine();
        boolean isMetaLine = isMetaLine(s); //判断是否是---开头
        if (!isMetaLine) { // 不是则退出，返回false.
            return false;
        }
        // 解析meta头
        StringBuilder sb = new StringBuilder();
        while (isMetaLine) {
            s = bufferedReader.readLine();
            // 到达文件结尾，或者再次出现---，表示meta头获取完成。改变isMetaLine标志
            isMetaLine = !(s == null || s.trim().startsWith("---"));
            if (isMetaLine)
                sb.append(s).append("\r\n");
        }
        s = sb.toString();
        System.out.println(s);
        MetaInfo metaInfo = parseMeta(s);
//        JSONObject object = JSONObject.fromObject(metaInfo);
//        System.out.println(object.toString());
        return true;
    }

    public MetaInfo parseMeta(String s) {
        MetaInfo metaInfo = new MetaInfo();
        String[] lines = s.split("\r\n");
        for (String line: lines) {
            String[] propLine = line.split(":");
            if (propLine.length > 1) {
                int splitIdx = line.indexOf(":");
                String key = line.substring(0, splitIdx);
                String val = line.substring(splitIdx+1).trim();
                metaInfo.put(key, val);
            }
        }
        return metaInfo;
    }


    @Test
    public void testFile() {
        try {
            start = System.currentTimeMillis();
            String path = "d:\\http_protocol_conclusion.md";
//            String path = "d:\\detail_docker_study_note.md";
            File f = new File(path);
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            boolean hasMeta = checkMeta(bufferedReader);
            if (!hasMeta) {
                System.out.println("No meta exit");
                return;
            }
            StringBuilder builder = new StringBuilder();
            String aline = bufferedReader.readLine();
            while(aline != null) {
                builder.append(aline).append("\r\n");
                aline = bufferedReader.readLine();
            }
            Options options = new  Options();
            options.setLangPrefix("");
            String result = Marked.marked(builder.toString(), options);
            System.out.println(result);
//            System.out.println("uses " + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
//        String source = "[$PROFILE$]: extended \n ## Headline with ID ## {# headid}";
        String source = "[$PROFILE$]: extended\n```bash\n# This is code!\n```";
        Options options = new  Options();
        options.setSanitize(true);
        options.setGfm(false);
        options.setBreaks(true);
        String result = Marked.marked(source, options);
        System.out.println(result);
    }

    @Test
    public void testFirst () {
        System.out.println("first");
    }


    @Before
    public void before() {
//        System.out.println("================ START ==================");

    }

    @After
    public void after() {
//        System.out.println("================ END ==================");

    }
}

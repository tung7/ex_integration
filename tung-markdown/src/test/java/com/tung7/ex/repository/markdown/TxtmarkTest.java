package com.tung7.ex.repository.markdown;

import com.github.rjeschke.txtmark.Processor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pegdown.PegDownProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Tung on 2016/12/5.
 */
public class TxtmarkTest {
    long start;

    @Test
    public void testFile() {
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
            String result = Processor.process("[$PROFILE$]: extended\r\n" + builder.toString());
            System.out.println(processor.markdownToHtml(builder.toString()));
            System.out.println(result);
            System.out.println("uses " + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        String source = "[$PROFILE$]: extended \n ## Headline with ID ## {# headid}";
        String result = Processor.process(source);
        System.out.println(result);
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

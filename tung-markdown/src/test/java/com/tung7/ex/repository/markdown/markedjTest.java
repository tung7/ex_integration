package com.tung7.ex.repository.markdown;

import com.github.rjeschke.txtmark.Processor;
import io.github.gitbucket.markedj.Marked;
import io.github.gitbucket.markedj.Options;
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
//https://github.com/gitbucket/markedj
public class MarkedjTest {
    long start;

    @Test
    public void testFile() {
        try {
            start = System.currentTimeMillis();
            String path = "d:\\uml_relation.md";
//            String path = "d:\\detail_docker_study_note.md";
            File f = new File(path);
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s = bufferedReader.readLine();
            StringBuilder builder = new StringBuilder();
            while(s != null) {
                builder.append(s).append("\r\n");
                s = bufferedReader.readLine();
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

package com.tung7.ex.repository.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

/**
 * TODO Complete The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/3/3.
 * @update
 */

public class TestToDel {
    @Value("${JAVA_HOME}")
    String javaHome;

//    @Value("${jvm_num}")
    int JvmNum;

    @Value("${test_value2}")
    String testVal;

    public static void main(String[] args) {
        ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TestToDel ttd = (TestToDel) cxt.getBean("testToDel");
        System.out.println(ttd.javaHome); // E:\jdk1.8.0_31
        System.out.println(ttd.JvmNum); // 1234321
        System.out.println(ttd.testVal); // 1234321
    }
}

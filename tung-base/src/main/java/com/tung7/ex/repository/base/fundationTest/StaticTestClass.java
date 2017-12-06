package com.tung7.ex.repository.base.fundationTest;

/**
 * Created by tung on 17-12-1.
 */
public class StaticTestClass {
    /**
     * 注意他们的声明顺序
     */
    private static String staticString2;
    private static String staticString = staticString2;
    static {
        System.out.println("staticString:" + staticString);
    }


}

package com.tung7.ex.repository.base.utils;

/**
 * TODO Complete The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/1/31.
 * @update
 */

public class ParentBean {
    static String parentApp1 = parentStaticApp();

    static {
        System.out.println("parent static area");
    }

    public ParentBean() {
        System.out.println("Parent Constructor");
    }

    public static String parentStaticApp() {
        System.out.println("parent StaticApp");
        return "parent_app1";
    }

}

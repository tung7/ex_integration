package com.tung7.ex.repository.base.utils;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/1/31.
 * @update
 */

public class SubBean extends ParentBean {
    static {
        System.out.println("SubBean static area1");
   }
    static String subStaticApp1 = subStaticApp() ;

    static {
        System.out.println("SubBean static area2");
    }
    public SubBean() {
        System.out.println("SubBean Constructor");
    }



    static String subStaticApp() {
        System.out.println("SubBean Static App");
        return "sub_app1";
    }
}

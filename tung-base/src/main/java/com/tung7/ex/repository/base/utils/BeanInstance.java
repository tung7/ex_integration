package com.tung7.ex.repository.base.utils;

import org.springframework.context.annotation.Bean;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/1/31.
 * @update
 */

public class BeanInstance {


    static String app1 = "old_static_prop";
    static String app2 = initStaticPropMethod();
    static String app4 = "app444444";
    String app5 = "app5555555555";
    String app3 = initPorp();

    static {
        System.out.print("static area           ");
        app1="new_static_prop";
//        System.out.println(app1);
    }

    public BeanInstance() {
        System.out.println("Constructor");
        normalStaticMethodInvokeInCons();
        normalMethodInvokeInCons();
    }

    public static void normalStaticMethodInvokeInCons() {
        System.out.println("normalStaticMethodInvokeInCons");
    }

    public static void normalMethodInvokeInCons() {
        System.out.println("normalMethodInvokeInCons");
    }

    public static String initStaticPropMethod() {
        System.out.println("initStaticPropMethod");
        app4 = "modified in static prop method"; // 与静态属性声明的顺序有关。。static成员变量的初始化，是按声明顺序来的。
        // 所以，如果该静态方法在app4之前调用，就不能修改app4的值。
        /****
         * 可以理解成，在类加载的时候，先把static成员属性按顺序声明，然后再按声明顺序进行初始化
         */
        return "2222";
    }
    public  String initPorp() {
        System.out.println("initPorp");
        app2 = "modified in noraml method"; // that's ok, alawys modified the static approperty.
        app5 = "new app5";
        return "3333";
    }

    public static void main(String[] args) {
//        System.out.println("gogogogogoggogogogogo");
//        String sas = "adfa";
//        System.out.println(sas);
        BeanInstance instance = new BeanInstance();
//        System.out.println("app2   " + BeanInstance.app2);
//        System.out.println("app4   " + BeanInstance.app4);
        System.out.println("app5  " + instance.app5);
        System.out.println("================");
        BeanInstance instance2 = new BeanInstance();
        /**
         initStaticPropMethod
         static area           new_static_prop
         initPorp
         Constructor
         normalStaticMethodInvokeInCons
         normalMethodInvokeInCons
         ================
         initPorp
         Constructor
         normalStaticMethodInvokeInCons
         normalMethodInvokeInCons
         */
        /********************/
        // JVM首次使用到这个类时，【加载】这个类后会先执行类级别的一些操作（哪怕根本没有进行实例化操作）。 1. 静态成员变量的【依次】声明与初始化， 2. 执行类的static代码. (1,2的顺序与代码的编写顺序有关。)
        // 然后就是 实例级别的操作。3. 非静态成员变量的【依次】声明与初始化。 4. 构造方法。

        /********************/

    }

}

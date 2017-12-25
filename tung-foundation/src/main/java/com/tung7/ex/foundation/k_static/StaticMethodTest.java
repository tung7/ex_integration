package com.tung7.ex.foundation.k_static;

/**
 * 类方法为了便于访问(无需实例)。
 *
 * 因为无需实例化，所以，abstract关键字不能与static关键字一起修饰方法。
 */
public class StaticMethodTest {
    static void foo() {
        System.out.println();
    }

//    abstract static foo2();

    abstract static class InnerTest {
        abstract void test();
    }
}

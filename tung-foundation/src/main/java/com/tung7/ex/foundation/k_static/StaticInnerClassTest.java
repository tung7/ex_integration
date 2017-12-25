package com.tung7.ex.foundation.k_static;

/**
 * 嵌套类（静态内部类）, static是不能修饰外部class的。
 * <p>R大说，Java里没有“静态内部类”这种东西，它叫做嵌套类。<a>https://www.zhihu.com/question/36471825/answer/67623002</a></p>
 *
 * 静态内部类不依赖于外部，相当于重新建立的一个新类，所以不能引用外部非静态成员（不依赖外部却要使用外部还没有生成的数据肯定不行）。
 */
public class StaticInnerClassTest {
    private static int i = 0;

    static class StaticInnerClass {
        public static void foo() {
            System.out.println(i);
        }
        public void foo2() {
            System.out.println(i);
        }
    }

    /**
     * todo @Tung 解释非static内部类，不能声明static member, 但是加上final后又可以。
     * 参考德问：http://www.dewen.net.cn/q/13793
     *
     */
    class InnerClass {
        // The field x cannot be declared static in a non-static inner type, unless initialized with a constant expression
       // public static int d = 0;
        public static final int f = 0;
        // The field x cannot be declared static in a non-static inner type, unless initialized with a constant expression
       // public static void foo() {}

        public void foo2() {
            System.out.println(i);
        }

    }
}

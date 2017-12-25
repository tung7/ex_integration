package com.tung7.ex.foundation.k_static;

/**
 *
 * <p>static 修饰变量有两个目的，一是共享变量（多实例同一个变量），二是便于访问（无需实例）</p>
 *
 * <p>static 变量引用是作为GC Root的</p>
 *
 * <p>
 * 这里的类变量会被<clinit>()方法收集起来，并在类加载阶段进行初始化。
 * 除了类变量，静态块也会被<clinit>()方法收集，其收集顺序就是代码中的声明顺序。所以，这些static变量/块的声明是需要一定顺序的
 * </p>
 */
public class StaticFieldTest {

    /**
     * 这里的声明顺序必须是这样的。
     */
    private static String staticString2;

    private static String staticString = staticString2;

    static {

        System.out.println("staticString:" + staticString);
    }

    //如果staticString变量放到这里声明，那么编译会报错。
//    private static String staticString = staticString2;

}


/**
 * 这里有点奇怪 todo
 */
class StaticField2Test {
    static {
        i = 10; //这里赋值的动作可以通过编译
//        System.out.println(i); //@Tung 这里使用到i变量的时候，报illegal forward reference(非法向前引用)
    }

    static int i;

}

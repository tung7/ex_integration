package com.tung7.ex.foundation.f_casting;

/**
 * 在java中， 除了boolean，所有基本类型都能互相转换
 */
public class PrimitiveCastingTest {

    public static void foo() {
        int i = 200;
        long lng = i; //自动转为更加widening的long
        lng = i;   //自动

        long lng2 = 2000; //自动
        lng2 = 2000; //自动

        i = (int) lng2;  //转为narrowing的需要 强制转换

        System.out.println((byte)127);//127
        System.out.println((byte)0);//0
        System.out.println((byte)128);//-128
        System.out.println((byte)129);//-127
        System.out.println((byte)-127);//-127

    }


    public static void main(String[] args) {
        foo();
    }
}

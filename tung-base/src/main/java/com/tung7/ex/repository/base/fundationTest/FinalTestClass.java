package com.tung7.ex.repository.base.fundationTest;

/**
 * Created by tung on 17-12-1.
 */
public class FinalTestClass {
    private final String final_01 = "chenssy";    //编译期常量，必须要进行初始化，且不可更改
    private final String final_02;                //运行时常量，在实例化一个对象时进行初始化

    public FinalTestClass(){
//        final_02 = "ccc";
    }
    public FinalTestClass(String cc){
//        final_02 = "ccc";
    }

    {
        final_02 = "";
    }

}

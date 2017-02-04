package com.tung7.ex.repository.base.utils;

/**
 * TODO Complete The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/2/3.
 * @update
 */

public class OrderPropBean {
    /**
     * 堆内存对齐，应该是  four, eight, obj.
     */

    public long eight = 8L;
    public int four = getFour();
    public String obj = "4";

    public OrderPropBean(){

    }

    public int getFour() {
        eight = 18888L;
        return 4;
    }

    public static void main(String[] args) {
        /**
         * 加载，实例化。
         * 声明, 初始化 ||| 堆内存对齐
         *
         */
        OrderPropBean bean = new OrderPropBean();
        System.out.println(bean.eight); // 输出18888L, 所以说堆内存对齐应该发生在初始化之后

    }

}

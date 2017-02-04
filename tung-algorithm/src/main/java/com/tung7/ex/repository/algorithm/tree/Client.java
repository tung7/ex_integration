package com.tung7.ex.repository.algorithm.tree;

import com.tung7.ex.repository.algorithm.TKey;
import com.tung7.ex.repository.algorithm.TValue;

/**
 * TODO Complete The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/1/28.
 * @update
 */

public class Client {
    public static void BST(TKey key) {
        BST bst = new BST();
        bst.put(new TKey(25), new TValue("Lemon"));
        bst.put(new TKey(15), new TValue("Apple"));
        bst.put(new TKey(35), new TValue("Banana"));
        bst.put(new TKey(27), new TValue("Cat"));
        bst.put(new TKey(20), new TValue("Dog"));
        bst.put(new TKey(13), new TValue("Pig"));
        bst.put(new TKey(17), new TValue("Layer"));
        bst.put(new TKey(22), new TValue("Hunter"));
        bst.put(new TKey(18), new TValue("Lion"));
        bst.put(new TKey(21), new TValue("Orange"));
        bst.put(new TKey(23), new TValue("Nut"));
        bst.put(new TKey(30), new TValue("Puppy"));
        bst.put(new TKey(14), new TValue("Boomer"));

//        System.out.println(bst.get(key));

//        System.out.println("min: " + bst.getMin());
//        System.out.println("max: " + bst.getMax());

//        bst.delMin();
//        System.out.println("min: " + bst.getMin());
//        System.out.println(bst.get(new TKey(14)));

//        bst.delMax();
//        System.out.println("max: " + bst.getMax());
//        System.out.println(bst.get(new TKey(30)));

        bst.del(new TKey(20));
        System.out.println(bst.get(new TKey(18))); // 25 - 15 - 21 - 17 -18
        System.out.println(bst.get(new TKey(23)));  // 25 - 15 - 21 - 22 -23


    }

    public static void main(String[] args) {
        Client.BST(new TKey(21));

    }
}

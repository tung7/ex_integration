package com.tung7.ex.repository.algorithm.tree;

import com.tung7.ex.repository.algorithm.TKey;
import com.tung7.ex.repository.algorithm.TValue;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/1/28.
 * @update
 */

public class BST {
    private Node root;

    private class Node {
        public Node left;
        public Node right;
        /**
         * 子节点个数的值
         *  以该Node为root的树的节点数量。
         */
        public int number;
        /**
         * 排序的key
         */
        public TKey key;
        /**
         * 包含的value
         */
        public TValue value;

        public Node(TKey key, TValue value, int number) {
            this.key = key;
            this.value = value;
            this.number = number;
        }

        @Override
        public String toString() {
            return "[key: " + key + ", value: " + value + ", number: " + number + "]";
        }
    }

    /**
     * 查找
     * @param key
     * @return
     */
    public Node get(TKey key) {
        return get(root, key);
    }

    /**
     * 删除最小的节点
     */
    public void delMin() {
        root = delMin(root);
    }

    /**
     * 删除最大的节点
     */
    public void delMax() {
        root = delMax(root);
    }

    /**
     * 删除某个节点。
     * @param key
     */
    public void del(TKey key) {
        root = del(root, key);
    }

    /**
     * 删除某个树下的某个节点
     * @param cur 树的根节点
     * @param key 节点的key
     * @return 删除删除后的树的根节点
     */
    private Node del(Node cur, TKey key) {
        int cmp = key.compareTo(cur.key);
        if (cmp > 0) {
            cur.right = del(cur.right, key);
        } else if (cmp < 0) {
            cur.left = del(cur.left, key);
        } else { // ==0 当前节点要被删除
            if (cur.left == null) {
                cur = cur.right;
//                return cur.right;  //因为要删除节点的子树的number 不会改变，所以可以直接返回。
            } else if (cur.right == null) {
                cur = cur.left;
//                return cur.left
            } else {  // 当前要删除的节点有左右子树。
                /*
                 * 左子树不会受到影响.
                 * 右子树只需要 找到并删除最小节点，然后将这个最小节点替换当前这个被删除的节点。
                 * */
//                Node oldCur = cur;
//                // 找到右子树最小的节点, 并作为新的当前节点。
//                cur = getMinNode(cur.right);
//                // 处理新的右树. 踩了应用的坑。。。必需先处理右树，再处理左树。。。。
//                // 如果先处理左树的引用，那么在delMin时，右树的左子树已经改变了（也就是原右子树的左子树已经被改变成原左子树），肯定就错了。
//                cur.right = delMin(oldCur.right);
//                // 还原旧的左树。
//                cur.left = oldCur.left;


                /************** 另一种写法 , 没那么容易踩引用的坑 ****************/
                // 找到右子树最小的节点。
                Node min = getMinNode(cur.right);
                // 删除掉右子树最小的节点，返回的是删除操作后的树的根节点。也就是新的右树
                Node right = delMin(cur.right);
                // 替换当前节点
                min.left = cur.left;
                min.right = right;
                cur = min;
                /******************************/
            }
        }
        cur.number = size(cur.left) + size(cur.right) + 1;
        return cur;
    }


    /**
     * 获取树的最小节点
     * @param cur 树的根节点
     * @return 最小节点
     */
    private Node getMinNode(Node cur) {
        if (cur.left == null) {
            return cur;
        }
        return getMinNode(cur.left);
    }

    /**
     * 删除树下最大的节点
     * @param cur 树的根节点
     * @return 返回删除后的树的根节点
     */
    private Node delMax(Node cur) {
        /**
         * 如果当前节点的右树为null，说明该节点最大，要被删除，返回左树
         */
        if (cur.right == null) return cur.left;

        cur.right = delMax(cur.right);
        cur.number = size(cur.left) + size(cur.right) + 1;
        return cur;

    }

    /**
     * 删除树下最小的节点
     * @param cur 树的根节点
     * @return  返回删除后的树的根节点
     */
    private Node delMin(Node cur) {
        /**
         * 如果没有左子树，说明该节点要被删除。返回右子树
         */
        if (cur.left == null) return cur.right;

        /**
         * 如果有左子树，说明该节点不用被删除， 递归搜索后，更新number后，返回当前节点
         */
        cur.left = delMin(cur.left);
        cur.number = size(cur.left) + size(cur.right) + 1;
        return cur;
    }



    /**
     * 插入节点。相同key则更新。
     * @param key
     * @param value
     * @return 返回更新后的树
     */
    public void put(TKey key, TValue value) {
        root = put(root, key, value);
    }


    /**
     * 获取number， 获取树的所有节点数
     * @param node 树的根节点。
     * @return
     */
    private int size(Node node) {
        if (node == null) return 0;
        return  node.number;
    }

    public Node getMin() {
        return getMin(root);
    }

    public Node getMax() {
        return getMax(root);
    }


    private Node getMin(Node cur) {
        if (cur.left == null) return cur;
        return getMin(cur.left);
    }
    private Node getMax(Node cur) {
        if (cur.right == null) return cur;

        return getMax(cur.right);
    }

    /**
     * 往某树中添加节点
     * @param cur 树的根节点
     * @param key
     * @param value
     * @return
     */
    private Node put(Node cur, TKey key, TValue value){
        /* 如果当前节点为null，也就是搜索完毕后，没发现相同的key，则新建 */
        if (cur == null) return new Node(key, value, 1);

        int cmp = key.compareTo(cur.key);
        if (cmp > 0) {
            cur.right = put(cur.right, key, value);
        } else if (cmp < 0) {
            cur.left = put(cur.left, key, value);
        } else {
            /* key相等则更新当前节点 */
            cur.value = value;
        }
        /* 更新节点数量，左树节点 + 右树节点 + cur节点 */
        cur.number = size(cur.left) + size(cur.right) + 1;
        return cur;
    }
    /**
     * 获取值
     * @param cur
     * @param key
     * @return
     */
    private Node get(Node cur, TKey key){
        if (cur == null) return null;
        System.out.println("Searching: " + cur);

        int cmp = key.compareTo(cur.key);
        if (key.compareTo(cur.key) > 0) {
            return get(cur.right, key);
        } else if (cmp < 0){
            return get(cur.left, key);
        } else {
            return cur;
        }
    }

}

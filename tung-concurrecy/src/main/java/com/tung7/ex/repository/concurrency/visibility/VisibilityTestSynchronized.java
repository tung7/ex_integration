package com.tung7.ex.repository.concurrency.visibility;

/**
 * 体验可见性
 * <p>1. 使用volatile</p>
 *
 * <p>2. 使用synchronized，锁住某变量，这样在synchronized释放monitor的时候，就会把此变量同步回主内存中(执行store和write操作)</p>
 *
 * @author Tung
 * @version 1.0
 * @date 2017/5/29.
 * @update
 */

public class VisibilityTestSynchronized extends Thread {

    private  MyInt s = new MyInt();
    private int i;

    private class MyInt {
        private boolean f = false;

        public boolean isF() {
            return f;
        }

        public MyInt setF(boolean f) {
            this.f = f;
            return this;
        }
    }

    @Override
    public void run() {
            while (!s.isF()) {
                synchronized (s) {
                    i++;
                }  // 释放monitor的时候，就会把此变量同步回主内存中(执行store和write操作)
            }
            System.out.println("Finished: " + i);

    }

    public  void stopInc() {
        s.setF(true);
    }

    public static void main(String[] args) throws InterruptedException {
        VisibilityTestSynchronized t = new VisibilityTestSynchronized();
        t.start();

        // 主线程等到500ms, 让i自增执行一段时间。
        Thread.sleep(500);

        t.stopInc(); // 倘若run()中 没有synchronized(s)修饰，则不会停止。

    }

}

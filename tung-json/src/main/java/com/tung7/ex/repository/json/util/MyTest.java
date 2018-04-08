package com.tung7.ex.repository.json.util;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 3/14/2018.
 * @update
 */

class MyThread extends Thread{
    public void run(){
        System.out.println("MyThread: run()");
    }
    public void start(){
        System.out.println("MyThread: start()");
    }
}
class MyRunnable implements Runnable{
    public void run(){
        System.out.println("MyRunnable: run()");
    }
    public void start(){
        System.out.println("MyRunnable: start()");
    }
}
public class MyTest {
    public static void main(String args[]){
        MyThread myThread  =  new MyThread();
        MyRunnable myRunnable = new MyRunnable();
        Thread thread  =  new Thread(myRunnable);
        myThread.start();
        thread.start();
        int i=10;int n= i++%5;
        System.out.println(i);
        System.out.println(n);
    }
}

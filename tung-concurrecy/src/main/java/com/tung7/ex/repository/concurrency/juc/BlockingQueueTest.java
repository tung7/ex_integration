package com.tung7.ex.repository.concurrency.juc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/9/6.
 * @update
 */

public class BlockingQueueTest {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        Consumer<String> consumer1 = new Consumer<>(queue);
        Consumer<String> consumer2 = new Consumer<>(queue);

        Producer<String> producer = new Producer<String>(queue) {
            @Override
            String produce() {
                return String.valueOf(System.currentTimeMillis());
            }
        };

        new Thread(consumer1).start();
        new Thread(consumer2).start();
        new Thread(producer).start();
    }
}


class Consumer<T> implements Runnable {
    private BlockingQueue<T> queue;
    public Consumer(BlockingQueue<T> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            while (true) {
                consume(queue.take());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("消费者收到中断请求！");
        }
    }

    private void consume(T item) {
        System.out.println("Consume: " + item);
    }
}

abstract class Producer<T>  implements Runnable{
    private BlockingQueue<T> queue;
    public Producer(BlockingQueue<T> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Produce");
                queue.put(produce()); //为null的时候，会NPE
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("生产者收到中断请求！");
        }
    }

    abstract T produce();

}

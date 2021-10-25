package basic.cyclicbarrier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
CyclicBarrier可以周期性地创建出屏障，在屏障解除之前，碰到屏障的线程是无法继续前进的。
屏障的解除条件是到达屏障处的线程个数达到了构造函数指定的个数
CyclicBarrier用于使线程步调一致
 */
public class Main {

    private static final int TASKS = 5;

    public static void main(String[] args) {
        System.out.println("main BEGIN");
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(TASKS);


        CountDownLatch countDownLatch = new CountDownLatch(TASKS);
        // 屏障的解除条件是线程个数达到构造函数指定的个数
        // 第二个参数Runnable可以指定屏障解除时的操作
        CyclicBarrier cyclicBarrier = new CyclicBarrier(TASKS, () -> {
            System.out.println("CyclicBarrier Action");
        });
        for (int i = 0; i < TASKS; i++) {
            executorService.execute(new MyTask(cyclicBarrier,countDownLatch,i));
        }

        // 等待所有的线程完成
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
        System.out.println("main END");
    }
}

package basic.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
join方法可以等待执行线程执行完成,但是无法指定等待次数
使用countDownLatch可以实现指定等待次数的等待
 */
public class Main {

    private static final int TASKS = 10;

    public static void main(String[] args) {
        System.out.println("main BEGIN");
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        CountDownLatch countDownLatch = new CountDownLatch(TASKS);
        for (int i = 0; i < TASKS; i++) {
            executorService.execute(new MyTask(countDownLatch,i));
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

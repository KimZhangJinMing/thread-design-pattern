package thread_specific_storage.example3;

import java.util.concurrent.CountDownLatch;

/**
 * 多线程使用时，使用ThreadLocal
 */
public class Main {

    private final static int TASKS = 3;

    public static void main(String[] args) {
        System.out.println("main BEGIN");

        CountDownLatch countDownLatch = new CountDownLatch(TASKS);

        // 启动三个线程写日志
        for (int i = 0; i < TASKS; i++) {
            new ClientThread(countDownLatch).start();
        }

        try {
            // 等待所有的线程执行完成
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main END");
    }
}

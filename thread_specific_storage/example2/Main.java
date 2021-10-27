package thread_specific_storage.example2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多线程使用时，必须为每个线程创建一个Writer实例
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("main BEGIN");

        Runnable runnable = () -> {
            Log log = new Log(Thread.currentThread().getName() + "-log.txt");
            for (int i = 0; i < 10; i++) {
                log.println("main: i= " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.close();
        };

        // 启动三个线程写日志
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.execute(runnable);
        }

        try {
            // 等待所有的线程执行完成
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
        System.out.println("main END");
    }
}

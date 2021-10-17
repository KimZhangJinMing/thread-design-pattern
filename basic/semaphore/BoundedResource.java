package basic.semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;

// 表示数量有限的资源类
public class BoundedResource {
    private final int permits;
    private final Semaphore semaphore;
    private final Random random = new Random(314159);

    public BoundedResource(int permits) {
        if(permits <= 0) {
            throw new RuntimeException("permits can't be negative");
        }
        semaphore = new Semaphore(permits);
        this.permits = permits;
    }

    // 计算已经使用的信号量,可能存在一个线程还没计算完成，另外一个线程就改变了availablePermits的返回值
    // 导致计算结果不准确的问题
    public int getUsedCount() {
        return permits - semaphore.availablePermits();
    }


    // 使用资源
    public void use() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + "获取信号量，已经使用的信号量：" + this.getUsedCount());
            // 使用资源
            System.out.println(Thread.currentThread().getName() + "使用资源...");
            try {
                // 模拟使用资源耗费的时间
                Thread.sleep(random.nextInt(500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
            System.out.println(Thread.currentThread().getName() + "释放信号量，已经使用的信号量：" + this.getUsedCount());
        }

    }
}

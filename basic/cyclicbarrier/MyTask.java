package basic.cyclicbarrier;


import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MyTask implements Runnable{

    private CountDownLatch latch;
    private CyclicBarrier cyclicBarrier;
    private int content;
    private static final int PHASE = 5;
    private Random random = new Random();

    public MyTask(CyclicBarrier cyclicBarrier,CountDownLatch latch,int content) {
        this.cyclicBarrier = cyclicBarrier;
        this.latch = latch;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            for (int phase = 0; phase < PHASE; phase++) {
                doPhase(phase);
                // 等待所有线程一起执行
                cyclicBarrier.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } finally {
            // 减1
            latch.countDown();
        }
    }

    private void doPhase(int phase) {
        System.out.println(Thread.currentThread().getName() + " doPhase START: content = " + content + ", phase=" + phase);
        try {
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException e) {

        }
        System.out.println(Thread.currentThread().getName() + " doPhase END: content = " + content + ", phase=" + phase);

    }
}

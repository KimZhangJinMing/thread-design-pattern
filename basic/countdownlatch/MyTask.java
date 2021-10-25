package basic.countdownlatch;


import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class MyTask implements Runnable{

    private CountDownLatch latch;
    private int content;
    private Random random = new Random();

    public MyTask(CountDownLatch latch,int content) {
        this.latch = latch;
        this.content = content;
    }

    @Override
    public void run() {
        doTask();
        latch.countDown();
    }

    private void doTask() {
        System.out.println(Thread.currentThread().getName() + " doTask START: content = " + content);
        try {
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException e) {

        }
        System.out.println(Thread.currentThread().getName() + " doTask END: content = " + content);

    }
}

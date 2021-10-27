package thread_specific_storage.example3;


import java.util.concurrent.CountDownLatch;

/**
 * 虽然每个线程都是使用不同的Log实例，但是这里不用通过构造函数传参进来
 * 在线程内使用ThreadLocal,key为Thread.currentThread,value为不同的Log实例
 */
public class ClientThread extends Thread{

    private final ThreadLocal<Log> threadLocal = new ThreadLocal<>();
    private final CountDownLatch latch;

    public ClientThread(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            getLog().println("main: i= " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        getLog().close();
        latch.countDown();
    }

    // 调用getLog的线程不同，返回的Log也不同
    public Log getLog() {
        Log log = threadLocal.get();
        if(log == null) {
            System.out.println(Thread.currentThread().getName() + "getLog");
            log = new Log(Thread.currentThread().getName() + "-log.txt");
            threadLocal.set(log);
        }
        return log;

    }}

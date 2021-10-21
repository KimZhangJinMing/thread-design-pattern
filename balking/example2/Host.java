package balking.example2;


import java.util.concurrent.TimeoutException;

// 使用wait的超时功能来实现具有超时功能的Host类
public class Host {
    private final long timeout;
    private boolean ready = false;

    public Host(long timeout) {
        this.timeout = timeout;
    }

    public synchronized void ready() {
        this.ready = true;
        notifyAll();
    }

    public synchronized void execute() throws InterruptedException, TimeoutException {
        // 开始时间
        long start = System.currentTimeMillis();
        while (!ready) {
            // 当前时间
            long now = System.currentTimeMillis();
            // 超时的时间
            long rest = timeout - (now - start);
            if(rest < 0) {
                throw new TimeoutException("now - start = " + (now - start));
            }
            // wait超时后会自动唤醒，继续往下执行
            wait(rest);
            System.out.println(Thread.currentThread().getName() + " automatically notify。");
        }
        System.out.println(Thread.currentThread().getName() + " call execute method.");
    }
}

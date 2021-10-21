package guarded_suspension.example4;

import java.util.Random;

// 向队列中发送请求的线程，也就是往队列中putRequest
public class ClientThread extends Thread{
    private RequestQueue queue;
    private final Random random = new Random();

    public ClientThread(RequestQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        // 如果发生中断了，程序刚好结束，线程可以正常退出
        // 如果try..catch只捕捉了Thread.sleep发生中断异常时被捕捉后线程还会继续for循环,线程不会终止
        try {
            for (int i = 0; i < 10000; i++) {
                // 放入队列的Request对象是No0.No1....
                Request request = new Request("No" + i);
                System.out.println(Thread.currentThread().getName() + " putRequest: " + request.getName());
                queue.PutRequest(request);
                    // 错开发送请求(putRequest)的时间点
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "发生中断异常。");
        }
    }
}

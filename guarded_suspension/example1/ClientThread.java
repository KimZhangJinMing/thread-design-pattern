package guarded_suspension.example1;

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
        for (int i = 0; i < 10000; i++) {
            // 放入队列的Request对象是No0.No1....
            Request request = new Request("No" + i);
            System.out.println(Thread.currentThread().getName() + " putRequest: " + request.getName());
            queue.PutRequest(request);
            try {
                // 错开发送请求(putRequest)的时间点
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

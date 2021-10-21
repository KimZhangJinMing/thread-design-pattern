package guarded_suspension.example4;

import java.util.Random;

public class ServerThread extends Thread{

    private RequestQueue queue;
    private final Random random = new Random();

    public ServerThread(RequestQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for(int i=0; i< 10000; i++) {
                Request request = queue.getRequest();
                System.out.println(Thread.currentThread().getName() + " getRequest: " + request.getName());
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "发生中断异常。");
        }
    }
}

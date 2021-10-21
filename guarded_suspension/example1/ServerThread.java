package guarded_suspension.example1;

import java.util.Random;

public class ServerThread extends Thread{

    private RequestQueue queue;
    private final Random random = new Random();

    public ServerThread(RequestQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for(int i=0; i< 10000; i++) {
            Request request = queue.getRequest();
            System.out.println(Thread.currentThread().getName() + " getRequest: " + request.getName());
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

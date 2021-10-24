package worker_thread.example1;

import java.util.Random;

// 表示发出工作请求的线程的类
public class ClientThread extends Thread{
    private Channel channel;
    private Random random = new Random();

    public ClientThread(String name,Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            int i = 0;
            while(true) {
                Request request = new Request(getName(), i);
                channel.putRequest(request);
//                System.out.println(getName() + " put: " + request);
                i++;
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupt.");
        }
    }
}

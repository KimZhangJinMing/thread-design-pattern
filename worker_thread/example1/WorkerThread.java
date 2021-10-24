package worker_thread.example1;

public class WorkerThread extends Thread{

    private Channel channel;

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            while(true) {
                Request request = channel.takeRequest();
                request.execute();
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupt.");
        }
    }
}

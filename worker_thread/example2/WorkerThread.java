package worker_thread.example2;

public class WorkerThread extends Thread{

    private Channel channel;
    private boolean terminal = false;

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    public void stopThread() {
        // 即使是execute的sleep方法抛出了InterruptedException
        // terminal也设置成了true，run方法的while循环就不会继续运行了
        terminal = true;
        interrupt();
    }

    @Override
    public void run() {
        try {
            while(!terminal) {
                Request request = channel.takeRequest();
                request.execute();
            }
        } catch (InterruptedException e) {
            terminal = true;
            System.out.println(getName() + " interrupt.");
        }
    }
}

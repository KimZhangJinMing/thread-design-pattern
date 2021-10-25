package two_phase_termination.example1;


public class CountupThread extends Thread{

    private int counter;
    private boolean shutdownRequested = false;

    // 这里并不需要加锁，因为shutdownRequested这个状态只会由false变成true
    // 而不会由true变成false
    public void shutdownRequest() {
        shutdownRequested = true;
        // 确保线程在sleep或者wait的时候也会被终止
        // 如果shutdownRequested=true时，线程处于sleep/wait状态
        // 只修改标志是不会让线程停止sleep/wait的,这会降低程序的响应性
        // 如果使用interrupt方法,可以使线程从sleep中醒来/从等待队列中出来
        interrupt();
    }

    public boolean isShutdownRequested() {
        return shutdownRequested;
    }

    private void doWork() throws InterruptedException {
        counter++;
        System.out.println("doWork: counter= " + counter);
        Thread.sleep(500);
    }

    private void doShutdown() {
        System.out.println("doShutdown: counter= " + counter);
    }

    @Override
    public void run() {
        try {
            while(!isShutdownRequested()){
                doWork();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            doShutdown();
        }
    }
}

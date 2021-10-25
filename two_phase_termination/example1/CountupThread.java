package two_phase_termination.example1;


public class CountupThread extends Thread{

    private int counter;
    // 为什么需要shutdownRequested标志
    // 线程的中断要么抛出InterruptedException，要么线程变成中断状态
    // 只有我们捕获InterruptedException或使用isInterrupted方法判断线程是否中断不就可以可以了嘛？
    // 确实，但是就怕程序中会有忽略InterruptedException的时候，比如捕获了InterruptedException却什么都不处理
    // 这样子的话，线程的中断状态也不会改变,isInterrupted检测不到线程中断状态
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

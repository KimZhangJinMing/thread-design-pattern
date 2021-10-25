package two_phase_termination.example3;


public class CountupThread extends Thread{

    private int counter;

    public void shutdownRequest() {
        interrupt();
    }

    public boolean isShutdownRequested() {
        return isInterrupted();
    }

    private void doWork() {
        counter++;
        System.out.println("doWork: counter= " + counter);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
            // InterruptedException转换成线程中断状态
            // 这样isInterrupted方法就能检测到
            // 如果不加这句话，线程即使interrupt了，线程也会继续执行
            Thread.currentThread().interrupt();

            // 使用这个语句修改了中断状态，isInterrupted也检测不到，线程也会继续执行
//            Thread.interrupted();
        }
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
        }finally {
            doShutdown();
        }
    }
}

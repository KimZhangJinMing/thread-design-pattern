package basic.interrupt;

// Host类根据传递进来的count来执行繁重的任务
// 加上interrupt让外面可以取消方法的执行
public class Host {

    private int count = 0;

    public Host(int count) {
        this.count = count;
    }

    public void execute() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        for (int i = 0; i < count; i++) {
            // currentThread.isInterrupted() 只是判断是否是中断状态
            // Thread.interrupted()返回是否被中断,还会修改中断标志
            if(Thread.interrupted()) {
                System.out.println(currentThread.getName() + " interrupt");
                throw new InterruptedException("interrupt");
            }
            doHeavyJob();
        }
        System.out.println(currentThread.getName() + " finished execute.");
    }

    private void doHeavyJob() {
        System.out.println("doHeavyJob Begin");
        long start = System.currentTimeMillis();
        // 大约执行10s的操作
        while(start + 10000 > System.currentTimeMillis()) {

        }
        System.out.println("doHeavyJob End");
    }
}

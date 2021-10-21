package single_threaded_execution.example4;

/**
 * 演示count++为什么不是原子性的操作以及synchronized可以保证线程的可见性
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        SecurityGate securityGate = new SecurityGate();
        // 每个线程执行10次加，10次减
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                securityGate.enter();
//                System.out.println("enter, count:" + securityGate.getCount());
                securityGate.exit();
//                System.out.println("exit, count:" + securityGate.getCount());
            }
        };
        int runCount = 0;
        Thread[] threads = new Thread[10];
        while(true) {
            // 启动10个线程来执行
            for (int i = 0; i < 10; i++) {
                threads[i] = new Thread(runnable);
                threads[i].start();
            }

            // 等待所有的线程执行完成
            for (int i = 0; i < 10; i++) {
                threads[i].join();
            }

            if(securityGate.getCount() == 0) {
                System.out.println("运算正确。");
            }else {
                System.out.println("在第"+runCount+"次运算出错 count：" + securityGate.getCount());
                break;
            }
            runCount++;
        }

    }
}

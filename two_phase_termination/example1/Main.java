package two_phase_termination.example1;


// 演示线程终止
public class Main {

    public static void main(String[] args) {
        System.out.println("main Begin");

        // 开启线程
        CountupThread countupThread = new CountupThread();
        countupThread.start();
        // 运行10s
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 停止线程
        System.out.println("countupThread shutdown");
        countupThread.shutdownRequest();

        System.out.println("main Join");
        try {
            countupThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("main END");

    }
}

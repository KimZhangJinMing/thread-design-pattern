package basic.interrupt;


public class Main {
    public static void main(String[] args) {
        // 大约要耗时3 * 10 = 30s
        Host host = new Host(3);

        // interrupt必须在线程中使用才能中断
        Runnable runnable = () ->  {
            try {
                host.execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}

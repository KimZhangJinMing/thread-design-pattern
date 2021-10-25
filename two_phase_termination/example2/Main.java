package two_phase_termination.example2;

/**
 * 线程启动3s后发出异常，在线程中不捕获异常，利用未捕获的异常处理器和线程退出勾子来处理异常
 */
public class Main {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " BEGIN");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 抛出异常
            int a = 2 / 0;

            // 不会输出下面这句
            System.out.println(Thread.currentThread().getName() + " END");
        }).start();

        // 设置未捕获的异常
        Thread.setDefaultUncaughtExceptionHandler((thread,e) -> {
            System.out.println("UncaughtExceptionHandler BEGIN");
            System.out.println(thread.getName() + " occur exception");
            System.out.println("exception is: " + e);
            System.out.println("UncaughtExceptionHandler END");
        });


        // 设置退出钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutdownHook BEGIN");
            System.out.println("There are some threads occur exception.You can do something in this thread");
            System.out.println("shutdownHook END");
        }));
    }
}

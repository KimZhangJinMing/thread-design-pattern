package thread_per_message.example4;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main {
    public static void main(String[] args) {
        System.out.println(" main START");

        // 参数中的5表示无工作时也会一直持有的线程个数
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        Host host = new Host(scheduledExecutorService);
        try {
            host.request(10,'a');
            host.request(10,'b');
            host.request(10,'c');
        } finally {
            scheduledExecutorService.shutdown();
            System.out.println(" main END");
        }

    }
}

package thread_per_message.example4;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// 委托端
public class Host {

    // 委托端中持有执行端的对象
    private final static Helper helper = new Helper();
    private final ScheduledExecutorService scheduledExecutorService;

    public Host(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public void request(int count, char c) {
        System.out.println("  request(" + count + "," + c + ") START");
        // 可以延期执行的任务
        // schedule方法是ScheduleExecutorService接口的方法
        scheduledExecutorService.schedule(() -> {
            helper.handle(count, c);
        },3, TimeUnit.SECONDS);
        System.out.println("  request(" + count + "," + c + ") END");
    }

}

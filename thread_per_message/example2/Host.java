package thread_per_message.example2;

import java.util.concurrent.ThreadFactory;

// 委托端
public class Host {

    // 委托端中持有执行端的对象
    private final static Helper helper = new Helper();
    private ThreadFactory threadFactory;

    public Host(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public void request(int count, char c) {
        System.out.println("  request(" + count + "," + c + ") START");
        // ThreadFactory管理thread的创建
        // newThread的参数是一个Runnable，描述怎么执行thread
        threadFactory.newThread(() -> {
            helper.handle(count,c);
        }).start();
        System.out.println("  request(" + count + "," + c + ") END");
    }

}

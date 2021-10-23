package thread_per_message.example3;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

// 委托端
public class Host {

    // 委托端中持有执行端的对象
    private final static Helper helper = new Helper();
    private final Executor executor;

    public Host(Executor executor) {
        this.executor = executor;
    }

    public void request(int count, char c) {
        System.out.println("  request(" + count + "," + c + ") START");
        // Executor描述线程执行的操作是什么
        executor.execute( () -> {
            helper.handle(count, c);
        });
        System.out.println("  request(" + count + "," + c + ") END");
    }

}

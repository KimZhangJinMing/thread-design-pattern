package active_object.example2.active;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/*
ActiveObjectImpl代替了Proxy，SchedulerThread,Servant类
singleThreadExecutor代替了SchedulerThread的实例
Callable|Runnable代替了Servant的实现,
如果处理的逻辑比较复杂，也可以将Callable|Runnable封装成MakerStringRequest|DisplayStringRequest
 */
public class ActiveObjectImpl implements ActiveObject{

    // 只有一个线程在运行,对应着一个SchedulerThread实例
    private final ExecutorService threadPool = Executors.newSingleThreadExecutor();

    @Override
    public Future<String> makeString(int count, char fillchar) {
        // makeString有返回值,向线程池提交一个Callable
        // 返回一个Future对象,使用submit方法
        return threadPool.submit(() -> {
            char[] buffer = new char[count];
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = fillchar;
                Thread.sleep(100);
            }
            return new String(buffer);
        });
    }

    @Override
    public void displayString(String str) {
        // displayString没有返回值,向线程池提交一个Runnable
        // 执行execute方法
        threadPool.execute(() -> {
            System.out.println("displayString: " + str);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void shutdown() {
        threadPool.shutdown();
    }
}

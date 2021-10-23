package thread_per_message.example3;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        System.out.println(" main START");
        // 使用自己创建的Executor
        // 描述应该怎么执行
        /*Host host = new Host(new Executor() {
            @Override
            public void execute(Runnable command) {
                new Thread(command).start();
            }
        });*/


        // 使用ExecutorService
        // 当使用自己创建的Executor时，我们需要new Thread
        // ExecutorService会复用线程，线程一直在后台运行着，每次调用execute时，线程就会执行Runnable对象
        // ExecutorService接口是Executor的子接口，提供了shutdown来结束服务
        // 执行shutdown之后，新的请求不再执行
        Host host = new Host(Executors.newCachedThreadPool());
        host.request(10,'a');
        host.request(10,'b');
        host.request(10,'c');
        System.out.println(" main END");

    }
}

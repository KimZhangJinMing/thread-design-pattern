package thread_per_message.example2;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {
    public static void main(String[] args) {
        System.out.println(" main START");
        // 使用自己创建的ThreadFactory
        // 创建ThreadFactory时需要重写newThread的方法，描述怎么创建Thread
//        Host host = new Host(new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                return new Thread(r);
//            }
//        });

        // 使用Executors工具类创建的ThreadFactory
        Host host = new Host(Executors.defaultThreadFactory());
        host.request(10,'a');
        host.request(10,'b');
        host.request(10,'c');
        System.out.println(" main END");

    }
}

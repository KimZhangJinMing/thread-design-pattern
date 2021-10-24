package future.example3;

import java.util.concurrent.ExecutionException;

/*
捕获子线程的异常：
1.修改Host类，如果在创建RealData的实例时发生了异常，那么将异常设置到FutureData类中
2.在FutureData类中增加一个设置异常的setException方法
3.使用java.util.concurrent.ExecutionException来包装实际发生的异常
4.修改getContent方法为可以抛出ExecutionException异常
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("main BEGIN");
        Host host = new Host();
        // 子线程发生了异常，怎么处理？ 直接try..catch是捕获不到异常的
        Data future1 = host.request(-10, 'a');
        Data future2 = host.request(20, 'b');
        Data future3 = host.request(30, 'c');

        System.out.println("main otherJob BEGIN");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main otherJob END");

        try {
            System.out.println(future1.getContent());
            System.out.println(future2.getContent());
            System.out.println(future3.getContent());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("main END");
    }
}

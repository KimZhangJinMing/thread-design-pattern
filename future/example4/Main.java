package future.example4;

import java.util.concurrent.ExecutionException;

/*
使用FutureTask时怎么捕获线程发生的异常
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("main BEGIN");
        Host host = new Host();
        // 子线程发生异常，使用FutureTask时怎么捕获
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
            // FutureTask中发生异常会抛出ExecutionException
            System.out.println(future1.getContent());
            System.out.println(future2.getContent());
            System.out.println(future3.getContent());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("main END");
    }
}

package future.example2;

import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) {
        System.out.println("main BEGIN");
        Host host = new Host();
        Data future1 = host.request(10, 'a');
        Data future2 = host.request(20, 'b');
        Data future3 = host.request(30, 'c');

        System.out.println("main otherJob BEGIN");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main otherJob END");

        System.out.println(future1.getContent());
        System.out.println(future2.getContent());
        System.out.println(future3.getContent());

        System.out.println("main END");
    }
}

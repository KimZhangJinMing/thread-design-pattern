package balking.example2;


import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) {
        Host host = new Host(3000);
        System.out.println("===BEGIN===");
        try {
            // 这里并没有使用线程，而Host内部使用wait的超时来实现了具有超时功能的Host类
            host.execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}

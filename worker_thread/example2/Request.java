package worker_thread.example2;

import java.util.Random;

// 表示工作请求的类
public class Request {
    private String name;
    private int number;
    private final Random random = new Random();

    public Request(String name,int number) {
        this.name = name;
        this.number = number;
    }

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " execute Request(" + name +"," + number + ")");
        slowly();
    }


    public void slowly() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Request(" + name +"," + number + ")";
    }
}

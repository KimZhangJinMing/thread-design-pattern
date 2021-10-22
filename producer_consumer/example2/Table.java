package producer_consumer.example2;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// 桌子类，最多可以放置3个蛋糕
public class Table {

    // 使用ArrayBlockingQueue实现
    private BlockingQueue<String> cakes;
    private final int MAX_SIZE = 3;

    public Table(int size) {
        if(size > MAX_SIZE) {
            throw new RuntimeException("size exceed the max_size!");
        }
        cakes = new ArrayBlockingQueue<>(size);
    }

    // 使用BlockingQueue来实现,BlockingQueue内部已经实现了阻塞,也是线程安全的
    public String take() throws InterruptedException {
        String cake = cakes.take();
        System.out.println(Thread.currentThread().getName() + " take cake:" + cake);
        return cake;
    }


    public void put(String cake) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " put: " + cake);
        cakes.put(cake);
    }



}

package producer_consumer.example1;

import java.util.Random;

// 消费者
public class EaterThread extends Thread{

    private Table table;
    private final Random random = new Random();

    public EaterThread(String name, Table table) {
        super(name);
        this.table = table;
    }

    @Override
    public void run() {
        // 消费者一直消费
        try {
            while(true) {
                String cake = table.take();
                // 模拟吃蛋糕所花费的时间
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupted.");
        }
    }
}
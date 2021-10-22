package producer_consumer.example2;


import java.util.Random;

// 生产者
public class MakerThread extends Thread {

    private Table table;
    // 所有的生产者共享一个id，需要保证可见性
    private volatile static int id = 0;
    private final Random random = new Random();

    public MakerThread(String name, Table table) {
        super(name);
        this.table = table;
    }

    @Override
    public void run() {
        // 生产者一直生产cake
        try {
            while (true) {
                // 模拟制作蛋糕所花费的时间
                Thread.sleep(random.nextInt(1000));
                String cake = "[No:" + nextId() + "] by thread[" + getName() + "]";
                table.put(cake);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupted.");
        }
    }

    public int nextId() {
        return id++;
    }
}

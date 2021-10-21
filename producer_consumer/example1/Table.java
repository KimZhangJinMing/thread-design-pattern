package producer_consumer.example1;



// 桌子类，最多可以放置3个蛋糕
public class Table {

    // 使用数组类来实现队列。
    // 不使用ArrayList是因为ArrayList会自动扩容，不能限制List的长度为3
    // 不使用LinkedList也是因为LinkedList无法指定容量大小
    private String[] cakes;
    private final int MAX_SIZE = 3;
    // 记录当前蛋糕的个数
    private int count = 0;
    // 下次take的位置
    private int head = 0;
    // 下次put的位置
    private int tail = 0;

    public Table(int size) {
        if(size > MAX_SIZE) {
            throw new RuntimeException("size exceed the max_size!");
        }
        cakes = new String[size];
    }


    public synchronized String take() throws InterruptedException {
        // 必须等桌子上放满了蛋糕后才可以拿
        while(count <= 0) {
            wait();
        }
        // count > 0 时执行
        String cake = cakes[head];
        head = (head + 1) % cakes.length;
        count--;
        System.out.println(Thread.currentThread().getName() + " take cake:" + cake);
        notifyAll();
        return cake;
    }


    public synchronized void put(String cake) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " put: " + cake);
        // 当桌子上没有蛋糕时才可以放
        while (count >= cakes.length) {
            wait();
        }
        // count < cakes.length时执行
        cakes[tail] = cake;
        tail = (tail + 1) % cakes.length;
        count++;
        // 桌子上已经放满了蛋糕之后,通知生产者暂停生产
        notifyAll();
    }



}

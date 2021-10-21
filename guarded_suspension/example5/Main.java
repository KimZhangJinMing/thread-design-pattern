package guarded_suspension.example5;

// 演示了一个死锁的例子以及如何解开死锁
public class Main {
    public static void main(String[] args) {
        RequestQueue queue1 = new RequestQueue();
        RequestQueue queue2 = new RequestQueue();

        TalkThread thread1 = new TalkThread(queue1, queue2);
        thread1.setName("Alice");

        TalkThread thread2 = new TalkThread(queue2, queue1);
        thread2.setName("Bobby");

        // 两个线程都是先调用的getRequest方法，而此时两个队列中都没有元素
        // 这就导致发生了死锁
        // 通过在getRequest和putRequest中添加超时的处理来解开死锁
        thread1.start();
        thread2.start();
    }
}

package producer_consumer.example1;


public class Main {
    public static void main(String[] args) {
        // 桌子上最多可以放3个蛋糕
        Table table = new Table(3);

        // 多个Producer最多也只能生产3个
        new MakerThread("makerThread1",table).start();
        new MakerThread("makerThread2",table).start();
        new EaterThread("eaterThread1",table).start();
    }
}

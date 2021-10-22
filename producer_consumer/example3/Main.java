package producer_consumer.example3;


import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // 桌子上最多可以放3个蛋糕
        Table table = new Table(3);

        // 多个Producer最多也只能生产3个cakes
        MakerThread makerThread1 = new MakerThread("makerThread1", table);
        MakerThread makerThread2 = new MakerThread("makerThread2", table);
        MakerThread makerThread3 = new MakerThread("makerThread3", table);
        EaterThread eaterThread1 = new EaterThread("eaterThread1", table);

        System.out.println("===BEGIN===:" + new Date());
        makerThread1.start();
        makerThread2.start();
        makerThread3.start();
        eaterThread1.start();

        // 运行10s后，终止线程
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 清除桌子上的蛋糕
        table.clear();
        System.out.println("===END===:" + new Date());
    }
}

package single_threaded_execution.example3;

import java.util.concurrent.Semaphore;

public class Gate {
    // 通过门的人数
    private int count = 0;
    private String name = "NoBody";
    private String birthPlace = "NoWhere";
    // 最多只允许一个线程执行
    private Semaphore semaphore = new Semaphore(1);

    public void pass(String name, String birthPlace) {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + "取得信号量，开始执行..");
            this.name = name;
            // 加上睡眠进行调试，更加容易调试
//            Thread.sleep(300);
            this.birthPlace = birthPlace;
            count++;
            check();
        } catch (Exception e) {
            System.out.println("哎呀，"+ Thread.currentThread().getName() +"出错了...");
        }finally {
            // 即使是发生异常，也释放信号量，让其他线程执行
            semaphore.release();
            System.out.println(Thread.currentThread().getName() + "开始释放信号量啦");
            System.out.println("信号量可用的个数：" + semaphore.availablePermits());
        }
    }

    // 如果name和birthPlace的首字母不同,则记录的数据是异常的
    public void check() {
        if(name.charAt(0) != birthPlace.charAt(0)) {
            System.out.println("***Broken***: " + toString());
        }
    }

    @Override
    public String toString() {
        return "Gate{" +
                "count=" + count +
                ", name='" + name + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                '}';
    }
}

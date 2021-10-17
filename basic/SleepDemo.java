package basic;


public class SleepDemo {
    public static void main(String[] args) {
        SimpleThread simpleThread = new SimpleThread();
        simpleThread.start();
        try {
            // 实际上调用的是Thread.sleep方法，而sleep的方法只对当前的线程有用
            // 也就是说，虽然程序暂停了3s，但是是主线程暂停了3s，而不是simpleThread这个线程暂停了3s
            // 要让simpleThread暂停，可以在run方法中使用Thread.sleep方法
            simpleThread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程执行完成！");
    }
}

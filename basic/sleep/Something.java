package basic.sleep;


// 使用wait来模拟Sleep
public class Something {

    public static void sleep(long time) throws InterruptedException {
        if(time != 0) {
            // 使用局部变量当锁,可以防止外界获取到锁
            // 这样可以防止外部notify/notifyAll
            Object obj = new Object();
            synchronized (obj) {
                // 利用wait的超时来模拟sleep
                // 因为wait传参为0的时候,会永久阻塞
                // 所以if条件排除了time=0的情况
                obj.wait(time);
            }
        }
    }
}

package basic.simpe;

import basic.simpe.SimpleThread;

public class WaitDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new SimpleThread();
        //wait/notify/notifyAll都需要获取锁后才能调用，否则会抛出illegalMonitorStateException.
        t.wait();
    }

}

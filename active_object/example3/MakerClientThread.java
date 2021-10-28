package active_object.example3;

import active_object.example3.active.ActiveObject;
import active_object.example3.active.Result;

// 生产字符串的线程
// Client角色
/*
Client角色调用ActiveObject角色的方法来委托处理，它能够调用的
只有ActiveObject角色提供的方法。调用这些方法后，如果存放请求的队列
还没有满(队列满了会阻塞)，程序控制权会立即返回
虽然Client角色只知道ActiveObject角色，但它实际调用的是Proxy角色
 */
public class MakerClientThread extends Thread {

    private final ActiveObject activeObject;
    private final char fillchar;

    public MakerClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
        this.fillchar = name.charAt(0);
    }

    @Override
    public void run() {
        int count = 0;
        try {
            while(true) {
                Result<String> result = activeObject.makeString(count, fillchar);
                count++;
                Thread.sleep(10);
                String resultValue = result.getResultValue();
                System.out.println(Thread.currentThread().getName() + " :value = " + resultValue);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

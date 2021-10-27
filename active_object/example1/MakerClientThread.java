package active_object.example1;

import active_object.example1.active.ActiveObject;
import active_object.example1.active.Result;

// 生产字符串的线程
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

package active_object.example2;


import active_object.example2.active.ActiveObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

public class MakerClientThread extends Thread{

    private final ActiveObject activeObject;
    private final char fillchar;

    public MakerClientThread(String name,ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
        this.fillchar = name.charAt(0);
    }

    @Override
    public void run() {
        int i = 0;
        try {
            while (true) {
                Future<String> result = activeObject.makeString(i, fillchar);
                i++;
                Thread.sleep(10);
                System.out.println(getName() + " make :" + result.get());
            }
        }catch (RejectedExecutionException e) {
            System.out.println(getName() + " RejectedExecution because of the threadPool was shutdown." );
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

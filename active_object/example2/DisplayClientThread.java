package active_object.example2;

import active_object.example2.active.ActiveObject;

import java.util.concurrent.RejectedExecutionException;


public class DisplayClientThread extends Thread {

    private ActiveObject activeObject;

    public DisplayClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        int i = 0;
        try {
            while (true) {
                String str = Thread.currentThread().getName() + "  " + i;
                activeObject.displayString(str);
                i++;
                Thread.sleep(200);
            }
        }catch (RejectedExecutionException e) {
            System.out.println(getName() + " RejectedExecution because of the threadPool was shutdown." );
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

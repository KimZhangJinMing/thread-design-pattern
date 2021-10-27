package active_object.example1;


import active_object.example1.active.ActiveObject;


// 显示字符串的线程
public class DisplayClientThread extends Thread{

    private final ActiveObject activeObject;

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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

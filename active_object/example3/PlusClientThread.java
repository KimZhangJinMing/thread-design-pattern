package active_object.example3;

import active_object.example3.active.ActiveObject;
import active_object.example3.active.Result;


public class PlusClientThread extends Thread{

    private ActiveObject activeObject;

    public PlusClientThread(String name,ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (int i = 1; i <= 9; i++) {
                    for (int j = i; j <= 9; j++) {
                        Result<String> result = activeObject.add(i + "", j + "");
                        System.out.println(getName() +  " plus. i = " + i + ",j = " + j + ",result = " + result.getResultValue());
                        Thread.sleep(100);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

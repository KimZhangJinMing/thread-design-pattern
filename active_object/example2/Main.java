package active_object.example2;


import active_object.example2.active.ActiveObject;
import active_object.example2.active.ActiveObjectFactory;

public class Main {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakerClientThread("Alice",activeObject).start();
        new MakerClientThread("Bobby",activeObject).start();
        new DisplayClientThread("Chris",activeObject).start();

        // 运行5s后关闭
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("===shutdown===");
            activeObject.shutdown();
        }

    }
}

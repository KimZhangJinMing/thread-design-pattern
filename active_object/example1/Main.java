package active_object.example1;


import active_object.example1.active.ActiveObject;
import active_object.example1.active.ActiveObjectFactory;

public class Main {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakerClientThread("Alice",activeObject).start();
        new MakerClientThread("Bobby",activeObject).start();
        new DisplayClientThread("Chris",activeObject).start();
    }
}

package single_threaded_execution.example5;

/**
 * 演示一个死锁的例子，以及如何解开死锁的办法
 * 1. 以相同的顺序获取锁资源，死锁一般是由于交叉获取锁资源导致的
 */
public class Main {
    public static void main(String[] args) {
        Tool spoon = new Tool("Spoon");
        Tool fork = new Tool("Fork");
        new EaterThread("Alice",spoon,fork).start();
//        new EaterThread("Bobby",fork,spoon).start();
        new EaterThread("Bobby",spoon,fork).start();
    }
}

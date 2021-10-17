package singleThreadedExecution.example6;

/**
 * 演示一个死锁的例子，以及如何解开死锁的办法
 * 2.将造成死锁的临街资源组成一个对象，对新的对象加锁
 */
public class Main {
    public static void main(String[] args) {
        Tool spoon = new Tool("Spoon");
        Tool fork = new Tool("Fork");
        Pair alicePair = new Pair(spoon,fork);
        Pair bobbyPair = new Pair(fork,spoon);

        new EaterThread("Alice",alicePair).start();
        new EaterThread("Bobby",bobbyPair).start();
    }
}

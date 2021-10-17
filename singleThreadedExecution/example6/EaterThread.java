package singleThreadedExecution.example6;

import java.lang.reflect.Parameter;

public class EaterThread extends Thread{

    private String name;
    private Pair pair;

    public EaterThread(String name, Pair pair) {
        this.name = name;
        this.pair = pair;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (pair) {
                System.out.println(name + "拿起 " + pair.getLeft() + "(left).");
                System.out.println(name + "拿起 " + pair.getRight() + "(right).");
                System.out.println(name + "正在吃饭...");
                System.out.println(name + "放下 " + pair.getRight() + "(right).");
            }
            System.out.println(name + "放下 " + pair.getLeft() + "(left).");
        }
    }
}

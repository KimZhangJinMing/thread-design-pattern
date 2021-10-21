package single_threaded_execution.example5;

public class EaterThread extends Thread{

    private String name;
    private Tool left;
    private Tool right;

    public EaterThread(String name,Tool left, Tool right) {
        this.name = name;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (left) {
                System.out.println(name + "拿起 " + left + "(left).");
                synchronized (right) {
                    System.out.println(name + "拿起 " + right + "(right).");
                    System.out.println(name + "正在吃饭...");
                    System.out.println(name + "放下 " + right + "(right).");
                }
            }
            System.out.println(name + "放下 " + left + "(left).");
        }
    }
}

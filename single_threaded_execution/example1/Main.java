package single_threaded_execution.example1;

/**
 * 演示使用同一实例的共享变量出现线程安全的问题
 */
public class Main {
    public static void main(String[] args) {
        Gate gate = new Gate();

        Thread Alice = new UserThread(gate, "Alice", "Alaska");
        Thread Bobby = new UserThread(gate, "Bobby", "Brazil");
        Thread Chris = new UserThread(gate, "Chris", "Canada");

        Alice.start();
        Bobby.start();
        Chris.start();
    }
}

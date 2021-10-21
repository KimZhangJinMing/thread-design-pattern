package single_threaded_execution.example2;

/**
 * 使用synchronized解决同一时刻只能有一个线程运行的问题
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

package singleThreadedExecution.example3;

/**
 * 使用Semaphore解决同一时刻只能有一个线程运行的问题
 */
public class Main {
    public static void main(String[] args) {
        Gate gate = new Gate();

        Thread Alice = new UserThread(gate, "Alice", "Alaska");
        Alice.setName("Alice");
        Thread Bobby = new UserThread(gate, "Bobby", "Brazil");
        Bobby.setName("Bobby");
        Thread Chris = new UserThread(gate, "Chris", "Canada");
        Chris.setName("Chris");

        Alice.start();
        Bobby.start();
        Chris.start();
    }
}

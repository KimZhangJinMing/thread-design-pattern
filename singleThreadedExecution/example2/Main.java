package singleThreadedExecution.example2;

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

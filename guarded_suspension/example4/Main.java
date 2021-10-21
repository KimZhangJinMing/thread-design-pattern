package guarded_suspension.example4;

public class Main {
    public static void main(String[] args) {
        RequestQueue queue = new RequestQueue();
        Thread clientThread = new ClientThread(queue);
        clientThread.setName("clientThread");

        Thread serverThread = new ServerThread(queue);
        serverThread.setName("serverThread");

        clientThread.start();
        serverThread.start();

        // 睡眠10s后中断
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clientThread.interrupt();
        serverThread.interrupt();
    }
}

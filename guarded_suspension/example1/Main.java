package guarded_suspension.example1;

public class Main {
    public static void main(String[] args) {
        RequestQueue queue = new RequestQueue();
        Thread clientThread = new ClientThread(queue);
        clientThread.setName("clientThread");

        Thread serverThread = new ServerThread(queue);
        serverThread.setName("serverThread");

        clientThread.start();
        serverThread.start();
    }
}

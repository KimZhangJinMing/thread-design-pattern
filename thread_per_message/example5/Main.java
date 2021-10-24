package thread_per_message.example5;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        MiniServer server = new MiniServer(8080);
        try {
            server.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

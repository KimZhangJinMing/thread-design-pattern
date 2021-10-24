package thread_per_message.example5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// 模拟服务器
public class MiniServer {
    private final int port;

    public MiniServer(int port) {
        this.port = port;
    }

    public void execute() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Listening on " + serverSocket);
        try {
            while(true) {
                System.out.println("Accepting...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to " + clientSocket);
                // 每次接收到请求就开启一个新的线程去处理
                new Thread(() -> {
                    try {
                        Service.service(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
//                Service.service(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }
}

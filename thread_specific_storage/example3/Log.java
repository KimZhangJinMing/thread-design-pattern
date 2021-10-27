package thread_specific_storage.example3;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 记录线程特有的日志类
 */
public class Log {

    private PrintWriter writer;

    public Log(String filename) {
        System.out.println(Thread.currentThread().getName() + " create Log");
        try {
            this.writer = new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 写日志
    public void println(String s) {
        writer.println(s);
    }

    // 关闭日志
    public void close() {
        writer.println("===END OF LOG===");
        writer.close();
    }
}

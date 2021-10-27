package thread_specific_storage.example1;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Log {

    // 声明为static的,且实例化的时候指定的filename是一个文件名称
    // 说明这是一个为单线程使用的类,即使是多个线程调用也是同一个PrintWriter实例
    private static PrintWriter writer;

    // 因为要捕捉异常，所以放在静态代码块初始化
    static {
        try {
            writer = new PrintWriter(new FileWriter("log.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 写日志
    public static void println(String s) {
        writer.println(s);
    }

    // 关闭日志
    public static void close() {
        writer.println("===END OF LOG===");
        writer.close();
    }



}
